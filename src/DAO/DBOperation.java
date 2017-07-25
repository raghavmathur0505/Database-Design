package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import beans.SearchBean;

public class DBOperation {
	private static final String mysqlurl = "jdbc:mysql://localhost:3306/library";
	private static final String mysqluser = "root";
	private static final String mysqlpassword = "Raghav.0505";

	private static final String sqlcmd0 = "USE library;"; // use the database
															// statement
	
	public static boolean PaySingleFine(String cardId, String loanId, String dateIn, String paid) {//dateIn==yes, paid==yes
		Connection conn = null;
		boolean result = true;
		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();
			stmt1.executeQuery(sqlcmd0);
			String sqlcmd3 = "SELECT * FROM  book_loans WHERE Card_id= '" + cardId + "' AND Loan_id= '"+loanId+"' AND Date_in is null;";
			ResultSet result2 = stmt1.executeQuery(sqlcmd3);
			String sqlcmd4 = "UPDATE fines SET Paid='1' where Loan_id IN (SELECT Loan_id FROM book_loans where Card_id = '" + cardId + "');";			
			
			if(result2.next()) {
				result = false;
			}
			else{
				stmt1.executeUpdate(sqlcmd4);
			}	
			result2.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}
		return result;
	}

	public static boolean checkUnreturnedBook(String cardId) {
		Connection conn = null;
		boolean result = true;
		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();
			stmt1.executeQuery(sqlcmd0);
			String sqlcmd3 = "SELECT * FROM  book_loans WHERE Card_id= '" + cardId + "' AND Date_in is null;";
			ResultSet result2 = stmt1.executeQuery(sqlcmd3);
			String sqlcmd4 = "UPDATE fines SET Paid='1' where Loan_id IN (SELECT Loan_id FROM book_loans where Card_id = '" + cardId + "');";			
			
			if(result2.next()) {
				result = false;
			}
			else{
				stmt1.executeUpdate(sqlcmd4);
			}	
			result2.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}
		return result;
	}

	public static ArrayList<ArrayList<String>> searchSumFines() {
		ArrayList<ArrayList<String>> searchResult = null;

		Connection conn = null;

		String sqlcmd1 = "SELECT book_loans.Card_id AS CARD_ID, SUM(fines.Fine_amt) as TOTAL_FINE, borrower.B_Fname "
				+ " AS NAME FROM fines INNER JOIN BOOK_LOANS ON fines.Loan_id = book_loans.Loan_id INNER JOIN borrower "
				+ " ON book_loans.Card_id = borrower.Card_id WHERE fines.paid = '0' "
				+ " GROUP BY book_loans.Card_id order by book_loans.Card_id;";

		String sqlcmd2 = "SELECT book_loans.Card_id AS CARD_ID, SUM(fines.Fine_amt) as TOTAL_FINE, borrower.B_Fname "
				+ " AS NAME FROM fines INNER JOIN BOOK_LOANS ON fines.Loan_id = book_loans.Loan_id INNER JOIN borrower "
				+ " ON book_loans.Card_id = borrower.Card_id WHERE fines.paid = '1' "
				+ " GROUP BY book_loans.Card_id order by book_loans.Card_id;";
		// String sqlcmd2= "SELECT * FROM book_loans WHERE
		System.out.println("The sql statement is " + sqlcmd1);
		// System.out.println(title);
		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println("The sql statement is " + sqlcmd1);

			searchResult = new ArrayList<ArrayList<String>>();
			// ArrayList<String> cardIds = new ArrayList<String>();
			while (result1.next()) {
				ArrayList<String> currProduct = new ArrayList<String>();

				// currProduct.add(result1.getString("Loan_id"));
				currProduct.add(result1.getString("CARD_ID"));

				currProduct.add(result1.getString("TOTAL_FINE"));
				// currProduct.add(result1.getString("Paid"));
				currProduct.add(result1.getString("NAME"));
				// currProduct.add((result1.getString("Paid") == "true") ? "1" :
				// "No");

				// currProduct.add(result1.getString("Date_in"));
				// String output =result1.getString("Date_in");
				// currProduct.add((result1.getString("Date_in") != null) ?
				// "Yes" : "No");

				// String output =
				// String.valueOf(result1.getBoolean("Availability"));
				// currProduct.add((output == "true") ? "Yes" : "No");
				searchResult.add(currProduct);

				System.out.println("currProduct: " + currProduct);

			}
			System.out.println("The search product result is got successfully.");
			// HttpSession session1 = new HttpSession();
			// session1.setAttribute("PRODUCTS1", products);
			// session1.setAttribute("cardIds", cardIds.getCARDID(index));
			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return searchResult;
	}

	public static ArrayList<ArrayList<String>> searchPaidSumFines() {
		ArrayList<ArrayList<String>> searchResult = null;

		Connection conn = null;

		/*
		 * String sqlcmd2 =
		 * "SELECT book_loans.Card_id AS CARD_ID, SUM(fines.Fine_amt) as TOTAL_FINE, borrower.B_Fname "
		 * +
		 * " AS NAME FROM fines INNER JOIN BOOK_LOANS ON fines.Loan_id = book_loans.Loan_id INNER JOIN borrower "
		 * + " ON book_loans.Card_id = borrower.Card_id WHERE fines.paid = '0' "
		 * + " GROUP BY book_loans.Card_id order by book_loans.Card_id;";
		 */

		String sqlcmd1 = "SELECT book_loans.Card_id AS CARD_ID, SUM(fines.Fine_amt) as TOTAL_FINE, borrower.B_Fname "
				+ " AS NAME FROM fines INNER JOIN BOOK_LOANS ON fines.Loan_id = book_loans.Loan_id INNER JOIN borrower "
				+ " ON book_loans.Card_id = borrower.Card_id WHERE fines.paid = '1' "
				+ " GROUP BY book_loans.Card_id order by book_loans.Card_id;";
		// String sqlcmd2= "SELECT * FROM book_loans WHERE
		System.out.println("The sql statement is " + sqlcmd1);
		// System.out.println(title);
		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println("The sql statement is " + sqlcmd1);

			searchResult = new ArrayList<ArrayList<String>>();
			// ArrayList<String> cardIds = new ArrayList<String>();
			while (result1.next()) {
				ArrayList<String> currProduct = new ArrayList<String>();

				// currProduct.add(result1.getString("Loan_id"));
				currProduct.add(result1.getString("CARD_ID"));

				currProduct.add(result1.getString("TOTAL_FINE"));
				// currProduct.add(result1.getString("Paid"));
				currProduct.add(result1.getString("NAME"));
				// currProduct.add((result1.getString("Paid") == "true") ? "1" :
				// "No");

				// currProduct.add(result1.getString("Date_in"));
				// String output =result1.getString("Date_in");
				// currProduct.add((result1.getString("Date_in") != null) ?
				// "Yes" : "No");

				// String output =
				// String.valueOf(result1.getBoolean("Availability"));
				// currProduct.add((output == "true") ? "Yes" : "No");
				searchResult.add(currProduct);

				System.out.println("currProduct: " + currProduct);

			}
			System.out.println("The search product result is got successfully.");
			// HttpSession session1 = new HttpSession();
			// session1.setAttribute("PRODUCTS1", products);
			// session1.setAttribute("cardIds", cardIds.getCARDID(index));
			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return searchResult;
	}

	public static ArrayList<ArrayList<String>> searchFines() {
		ArrayList<ArrayList<String>> searchResult = null;

		Connection conn = null;

		String sqlcmd1 = "SELECT fines.Loan_id , fines.Fine_amt , fines.Paid , "
				+ " book_loans.Card_id , book_loans.Date_in , borrower.B_Fname, book_loans.Isbn "
				+ " FROM fines INNER JOIN book_loans ON fines.Loan_id = book_loans.Loan_id INNER JOIN borrower ON book_loans.Card_id = borrower.Card_id;";

		System.out.println("The sql statement is " + sqlcmd1);
		// System.out.println(title);
		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println("The sql statement is " + sqlcmd1);

			searchResult = new ArrayList<ArrayList<String>>();
			while (result1.next()) {
				ArrayList<String> currProduct = new ArrayList<String>();

				currProduct.add(result1.getString("Loan_id"));
				currProduct.add(result1.getString("Fine_amt"));
				// currProduct.add(result1.getString("Paid"));
				currProduct.add((result1.getString("Paid").equalsIgnoreCase("true") || result1.getString("Paid").equalsIgnoreCase("1")) ? "Yes" : "No");
				currProduct.add(result1.getString("Card_id"));
				// currProduct.add(result1.getString("Date_in"));
				// String output =result1.getString("Date_in");
				currProduct.add((result1.getString("Date_in") != null) ? "Yes" : "No");
				//System.out.println("datein:  " + currProduct);
				currProduct.add(result1.getString("B_Fname"));
				currProduct.add(result1.getString("Isbn"));
				// String output =
				// String.valueOf(result1.getBoolean("Availability"));
				// currProduct.add((output == "true") ? "Yes" : "No");
				searchResult.add(currProduct);

				System.out.println("currProduct: " + currProduct);

			}
			System.out.println("The search product result is got successfully.");

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return searchResult;
	}

	public static ArrayList<ArrayList<String>> searchFineByCardId(String cardId) {
		ArrayList<ArrayList<String>> searchResult = null;

		Connection conn = null;

		String sqlcmd1 = "SELECT fines.Loan_id , fines.Fine_amt , fines.Paid , "
				+ " book_loans.Card_id , book_loans.Date_in , borrower.B_Fname, book_loans.Isbn  "
				+ " FROM fines INNER JOIN BOOK_LOANS ON fines.Loan_id = book_loans.Loan_id INNER JOIN borrower ON book_loans.Card_id = borrower.Card_id"
				+ " WHERE book_loans.Card_id = '" + cardId + "';";

		System.out.println("The sql statement is " + sqlcmd1);
		// System.out.println(title);
		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println("The sql statement is " + sqlcmd1);

			searchResult = new ArrayList<ArrayList<String>>();
			while (result1.next()) {
				ArrayList<String> currProduct = new ArrayList<String>();

				currProduct.add(result1.getString("Loan_id"));
				currProduct.add(result1.getString("Fine_amt"));
				// currProduct.add(result1.getString("Paid"));
				currProduct
						.add((result1.getString("Paid") == "true" || result1.getString("Paid") == "1") ? "Yes" : "No");
				currProduct.add(result1.getString("Card_id"));
				// currProduct.add(result1.getString("Date_in"));
				// String output =result1.getString("Date_in");
				currProduct.add((result1.getString("Date_in") != null) ? "Yes" : "No");
				currProduct.add(result1.getString("B_Fname"));
				currProduct.add(result1.getString("Isbn"));
				// String output =
				// String.valueOf(result1.getBoolean("Availability"));
				// currProduct.add((output == "true") ? "Yes" : "No");
				searchResult.add(currProduct);

				System.out.println("currProduct: " + currProduct);

			}
			System.out.println("The search product result is got successfully.");

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return searchResult;
	}

	public static boolean ReturnBook(String cardId, String isbn, String dateOut, String dateDue, String dateIn) {
		boolean result = false;
		Connection conn = null;
		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			String sqlcmd = "Select Loan_id " + " FROM library.book_loans " + " WHERE Isbn ='" + isbn
					+ "' AND Card_id ='" + cardId + "' AND Date_out = '" + dateOut + "' AND Due_date= '" + dateDue
					+ "' AND Date_in IS null ;";

			ResultSet output = stmt1.executeQuery(sqlcmd);
			if (output.next()) {
				int loanID = (output.getInt("Loan_id"));
				String sqlcmd1 = "UPDATE library.book_loans SET Date_in ='" + dateIn + "' WHERE Loan_id = '" + loanID
						+ "';";
				String sqlcmd2 = "UPDATE library.book SET Availability = '1' WHERE Isbn = '" + isbn + "';";
				stmt1.executeUpdate(sqlcmd1);
				stmt1.executeUpdate(sqlcmd2);// get the result
				/*
				 * SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				 * 
				 * Date date1 = format.parse(dateDue); Date date2 =
				 * format.parse(dateIn);
				 * 
				 * if (date1.compareTo(date2) < 0) { // String sqlcmd2 = "Select
				 * Loan_id FROM library.book_loans // " + " where Isbn='
				 * " + isbn + "' AND Card_id='" // + cardId +
				 * "' AND Date_out= '" + dateOut + "' and // Due_date= '
				 * " + dateDue + "'; "; // ResultSet result2 =
				 * stmt1.executeQuery(sqlcmd2); // int loanId =
				 * (result2.getInt("Loan_id"));
				 * 
				 * // SimpleDateFormat formatter = new //
				 * SimpleDateFormat("yyyy-MM-dd");
				 * 
				 * long diff = 0; long noOfDays = 0; diff = date2.getTime() -
				 * date1.getTime(); noOfDays = TimeUnit.DAYS.convert(diff,
				 * TimeUnit.MILLISECONDS); long a =
				 * TimeUnit.DAYS.toDays(noOfDays);
				 * 
				 * double fineAmt = a * (0.25); String sqlcmd3 =
				 * "INSERT INTO library.fines VALUES (" + loanID + ",'" +
				 * fineAmt + "'," + null + ");"; stmt1.executeUpdate(sqlcmd3);
				 * // System.out.println("earlier"); // result2.close(); }
				 */
				result = true;

				output.close();
				conn.close();
			}
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return result;
	}

	public static ArrayList<ArrayList<String>> getCheckOutBooks(String cardId, String isbn, String name) {
		ArrayList<ArrayList<String>> searchResult = null;

		Connection conn = null;

		String sqlcmd1 = "SELECT book_loans.Isbn, book_loans.Card_id, borrower.B_Fname, borrower.B_Lname, book_loans.Date_out, book_loans.Due_date "
				+ "FROM book_loans JOIN borrower ON book_loans.Card_id = borrower.Card_id "
				+ "WHERE Book_loans.Date_in is null AND ( book_loans.Isbn= '" + isbn + "' OR book_loans.Card_id = '"
				+ cardId + "' OR borrower.B_Fname= '" + name + "' OR borrower.B_Lname= '" + name + "') ; ";
		// String sqlcmd2 = "SELECT Book.Isbn, Book.Title,
		// GROUP_CONCAT(Authors.Full_Name ORDER BY Authors.Full_Name) Authors,
		// Book.Availability "
		// + "FROM Book INNER JOIN book_authors ON Book.Isbn = book_authors.Isbn
		// INNER JOIN Authors ON book_authors.Author_id = Authors.Author_id ";
		/*
		 * if (!title.equals("%%") || !name.equals("%%") || !isbn.equals("%%"))
		 * {
		 * 
		 * sqlcmd1 += " WHERE "; boolean existFlag = false; if
		 * (!title.equals("%%")) { sqlcmd1 += "Book.Title LIKE  '" + title +
		 * "' "; existFlag = true; } if (!name.equals("%%")) { if (existFlag) {
		 * sqlcmd1 += "AND "; } sqlcmd1 += "Authors.Full_Name LIKE  '" + name +
		 * "' "; existFlag = true; } if (!isbn.equals("%%")) { if (existFlag) {
		 * sqlcmd1 += "AND "; } sqlcmd1 += "Book.Isbn LIKE '" + isbn + "' "; } }
		 * sqlcmd1 += "GROUP BY Book.Isbn, Book.Title;";
		 */
		System.out.println("The sql statement is " + sqlcmd1);
		// System.out.println(title);
		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println("The sql statement is " + sqlcmd1);

			searchResult = new ArrayList<ArrayList<String>>();
			while (result1.next()) {
				ArrayList<String> currProduct = new ArrayList<String>();

				currProduct.add(result1.getString("Isbn"));
				currProduct.add(result1.getString("Card_id"));
				currProduct.add(result1.getString("B_Fname"));
				currProduct.add(result1.getString("B_Lname"));
				currProduct.add(result1.getString("Date_out"));
				currProduct.add(result1.getString("Due_date"));
				// String output =
				// String.valueOf(result1.getBoolean("Availability"));
				// currProduct.add((output == "true") ? "Yes" : "No");
				searchResult.add(currProduct);

				// System.out.println("currProduct: " + currProduct);

			}
			System.out.println("The search product result is got successfully.");

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return searchResult;
	}

	public static boolean CheckBookAvailability(String cardId, String isbn, String dateOut, String dateDue,
			String availability) {
		boolean result = false;
		Connection conn = null;

		// query statement
		String sqlcmd1 = "SELECT availability FROM library.book WHERE isbn='" + isbn + "';";

		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result

			if (result1.next()) {
				String output = String.valueOf(result1.getBoolean("Availability"));
				if (output.equalsIgnoreCase("true") || output.equalsIgnoreCase("1")) {
					String sqlcmd2 = "SELECT count(*) AS Num_Loans FROM library.book_loans WHERE Card_id='" + cardId
							+ "' AND Date_in is null;";
					ResultSet result2 = stmt1.executeQuery(sqlcmd2);
					while (result2.next()) {
						int output2 = (result2.getInt("Num_Loans"));
						if (output2 <= 2) {
							String sqlcmd3 = "INSERT INTO library.book_loans VALUES (" + null + ",'" + isbn + "','"
									+ cardId + "','" + dateOut + "','" + dateDue + "'," + null + ");";
							stmt1.executeUpdate(sqlcmd3);
							String sqlcmd4 = "UPDATE library.book SET Availability ='0' WHERE isbn ='" + isbn + "';";
							stmt1.executeUpdate(sqlcmd4);
							result = true;
						} else
							result = false;
					}
				} else
					result = false;

			} else {
				result = false;
				System.out.println("The username does not exists.");
			}

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return result;
	}

	public static String userSignUp(String ssn, String fname, String lname, String address, String city, String state,
			String email, String phone) {
		boolean result = false;
		Connection conn = null;
		String resultNew = null;
		// query statement
		String sqlcmd1 = "SELECT * FROM library.borrower WHERE Ssn='" + ssn + "';";

		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println(
					"The sql statement is " + sqlcmd1 + ssn + fname + lname + address + city + state + email + phone);
			if (result1.next()) { // the user already exists
				result = false;
				System.out.println("Sign up failed. The SSN already exists.");
			} else { // the user doesn't exist
				result = true;
				// int noFailedLogin = 0;
				String sqlcmd2 = "INSERT INTO borrower VALUES (" + null + ",'" + ssn + "','" + fname + "','" + lname
						+ "','" + address + "','" + city + "','" + state + "','" + email + "','" + phone + "');";
				stmt1.executeUpdate(sqlcmd2);
				System.out.println("The sql statement is " + sqlcmd2);
				System.out.println("The user signed up successfully.");
				String sqlcmd3 = "SELECT Card_id FROM borrower WHERE Ssn = '" + ssn + "'; ";
				ResultSet result2 = stmt1.executeQuery(sqlcmd3);
				System.out.println("The card id in dao is:  " + sqlcmd3);
				while (result2.next()) {
					resultNew = String.valueOf(result2.getString("Card_id"));

					System.out.println("session cardid: " + resultNew);

				}
				System.out.println("The search product result is got successfully.");
				result2.close();
				// result1.close();
			}

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return resultNew;
	}

	@SuppressWarnings("unchecked")
	/*
	public static ArrayList<ArrayList<String>> searchBooks(String title, String isbn, String name) {
		// public static ArrayList<ArrayList<String>> searchBooks(String
		// string1) {
		// JSONObject resultJSON = new JSONObject();
		ArrayList<ArrayList<String>> searchResult = null;

		Connection conn = null;
		// query book information statement
		// String sqlcmd1 = "SELECT * FROM product WHERE Post_User_Id=B.Isbn,
		// B.Title, B.Price, B.No_of_copies, GROUP_CONCAT(DISTINCT A.Author_name
		// SEPARATOR ', ') AS Author_name ";
		// sqlcmd1 += "FROM BOOK AS B, AUTHORS AS A ";
		// sqlcmd1 += "WHERE B.Isbn=A.Isbn AND B.Title LIKE '" + title + "' AND
		// B.No_of_copies>=0 GROUP BY A.Isbn ORDER BY B.Price;";
		// String sqlcmd2 = "SELECT Post_User_Id, Prod_Id, Prod_Name, P_Price,
		// P_Description, P_Category, P_Quality, P_Address_Line1,
		// P_Address_Line2, P_City, P_State, P_Country FROM
		// user_data.product,users;";
		// String sqlcmd3 = "SELECT Email_Id,Post_User_Id, Prod_Id, Prod_Name,
		// P_Price, P_Description, P_Category, P_Quality, P_Address_Line1,
		// P_Address_Line2, P_City, P_State, P_Country FROM
		// user_data.product,user_data.users WHERE Post_User_Id=Username;";
		/*
		 * String sqlcmd2 =
		 * "SELECT Book.Isbn, Book.Title, Authors.Full_Name, Book.Availability FROM Authors, Book_Authors, Book "
		 * +
		 * " WHERE Authors.Author_id= Book_Authors.Author_id AND Book_Authors.Isbn= Book.Isbn "
		 * + "AND ( Book.Isbn LIKE '" + title + "' OR Book.Title LIKE '" + title
		 * + "' OR Authors.Full_Name LIKE '" + title + "');"; String sqlcmd3 =
		 * "SELECT Book.Isbn, Book.Title, Authors.Full_Name, Book.Availability FROM Authors, Book_Authors, Book "
		 * +
		 * " WHERE Authors.Author_id= Book_Authors.Author_id AND Book_Authors.Isbn= Book.Isbn "
		 * + "AND (Book.Isbn = '" + string1 + "' OR Book.Isbn = '" + string2 +
		 * "' OR Book.Isbn = '" + string3 + "' )" + "AND (Book.Title LIKE '" +
		 * string1 + "' OR Book.Title LIKE '" + string2 +
		 * "' OR Book.Title LIKE '" + string3 + "')" +
		 * "AND (Authors.Full_Name LIKE '" + string1 +
		 * "' OR Authors.Full_Name LIKE '" + string2 +
		 * "' OR Authors.Full_Name LIKE '" + string3 + "');";
		 */
	/*
		String sqlcmd1 = "SELECT  Book.Isbn, Book.Title, GROUP_CONCAT(Authors.Full_Name ORDER BY Authors.Full_Name) Authors, Book.Availability "
				+ "FROM Book INNER JOIN book_authors ON Book.Isbn = book_authors.Isbn INNER JOIN Authors ON book_authors.Author_id = Authors.Author_id ";

		if (!title.equals("%%") || !name.equals("%%") || !isbn.equals("%%")) {

			sqlcmd1 += " WHERE ";
			boolean existFlag = false;
			if (!title.equals("%%")) {
				sqlcmd1 += " Book.Title LIKE  '" + title + "' ";
				existFlag = true;
			}
			if (!name.equals("%%")) {
				if (existFlag) {
					sqlcmd1 += "AND ";
				}
				sqlcmd1 += " Authors.Full_Name LIKE  '" + name + "' ";
				existFlag = true;
			}
			if (!isbn.equals("%%")) {
				if (existFlag) {
					sqlcmd1 += "AND ";
				}
				sqlcmd1 += " Book.Isbn LIKE '" + isbn + "' ";
			}
		}
		sqlcmd1 += " GROUP BY Book.Isbn, Book.Title;";
		*/
	/*
	System.out.println("The sql statement is " + sqlcmd1);
	// System.out.println(title);
	try {
		// connect to database
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
		Statement stmt1 = conn.createStatement();

		stmt1.executeQuery(sqlcmd0); // use the database
		ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
		System.out.println("The sql statement is " + sqlcmd1);

		searchResult = new ArrayList<ArrayList<String>>();
		while (result1.next()) {
			ArrayList<String> currProduct = new ArrayList<String>();

			currProduct.add(result1.getString("Isbn"));
			currProduct.add(result1.getString("Title"));
			currProduct.add(result1.getString("Authors"));
			String output = String.valueOf(result1.getBoolean("Availability"));
			currProduct.add((output.equalsIgnoreCase("true")) ? "Yes" : "No");
			searchResult.add(currProduct);

			// System.out.println("currProduct: " + currProduct);

		}
		System.out.println("The search product result is got successfully.");

		result1.close();
		conn.close();
	} catch (Exception e) {
		System.out.println("Error occurred during communicating with database.");
		e.printStackTrace();
	}

	return searchResult;
}
*/
	 public static ArrayList<ArrayList<String>> searchBooks(String[] searchArray) {
		
		ArrayList<ArrayList<String>> searchResult = null;

		Connection conn = null;
		  String sqlcmd1 = "SELECT  Book.Isbn, Book.Title, GROUP_CONCAT(Authors.Full_Name ORDER BY Authors.Full_Name) Authors, Book.Availability "
				+ "FROM Book INNER JOIN book_authors ON Book.Isbn = book_authors.Isbn INNER JOIN Authors ON book_authors.Author_id = Authors.Author_id ";

		if (searchArray.length>0) {
			
			 sqlcmd1 += " WHERE ";
			 //boolean existFlag = false;
			int count = 0;
			for(String i: searchArray)
			{
				if(count>0){
					sqlcmd1 += " OR ";
				    sqlcmd1+= " Book.Title LIKE '" + i + "' OR Authors.Full_Name LIKE '" + i + "' OR Book.Isbn LIKE '" + i + "' ";
				}
				else
					sqlcmd1+= " Book.Title LIKE '" + i + "' OR Authors.Full_Name LIKE '" + i + "' OR Book.Isbn LIKE '" + i + "' ";
				count++;
			}
			
		}
		sqlcmd1 += " GROUP BY Book.Isbn, Book.Title;";
		
		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println("The sql statement is " + sqlcmd1);

			searchResult = new ArrayList<ArrayList<String>>();
			while (result1.next()) {
				ArrayList<String> currProduct = new ArrayList<String>();

				currProduct.add(result1.getString("Isbn"));
				currProduct.add(result1.getString("Title"));
				currProduct.add(result1.getString("Authors"));
				String output = String.valueOf(result1.getBoolean("Availability"));
				currProduct.add((output.equalsIgnoreCase("true")) ? "Yes" : "No");
				searchResult.add(currProduct);

				// System.out.println("currProduct: " + currProduct);

			}
			System.out.println("The search product result is got successfully.");

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return searchResult;
		} 
     
	public static ArrayList<ArrayList<String>> viewBooks(String title) {
		// JSONObject resultJSON = new JSONObject();
		ArrayList<ArrayList<String>> searchResult = null;

		Connection conn = null;

		String sqlcmd1 = "SELECT Book.Isbn, Book.Title, Authors.Full_Name, Book.Availability FROM Authors, Book_Authors, Book WHERE Authors.Author_id = Book_Authors.Author_id AND Book_Authors.Isbn = Book.Isbn LIMIT 0;";
		System.out.println("dbo");
		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println("The sql statement is " + sqlcmd1);

			searchResult = new ArrayList<ArrayList<String>>();
			while (result1.next()) {
				ArrayList<String> currProduct = new ArrayList<String>();

				currProduct.add(result1.getString("Isbn"));
				currProduct.add(result1.getString("Title"));
				currProduct.add(result1.getString("Full_Name"));
				// currProduct.add(String.valueOf(result1.getBoolean("Availability")));
				String output = String.valueOf(result1.getBoolean("Availability"));
				currProduct.add((output == "true") ? "Yes" : "No");
				searchResult.add(currProduct);

				// System.out.println("currProduct: " + currProduct);

			}
			System.out.println("The search product result is got successfully.");

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return searchResult;
	}

	public boolean userLogin(String name, String cardId) {
		boolean result = false;
		Connection conn = null;

		// query statement
		String sqlcmd1 = "SELECT * FROM library.borrower WHERE B_Fname='" + name + "' AND Card_id = '" + cardId + "';";

		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println("The sql statement is " + sqlcmd1);
			if (result1.next()) { // the user exists
				if (result1.getString("Card_id").equals(cardId)) { // valid
																	// login
					result = true;
					// update Last_login
					// SimpleDateFormat dateFormat = new
					// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					// String Last_login = dateFormat.format(new Date());
					// System.out.println(location);
					// String sqlcmd2 = "UPDATE borrowers SET Last_login='" +
					// Last_login + "',Last_login_location='" + location + "'
					// WHERE Username ='" + username + "';";
					// stmt1.executeUpdate(sqlcmd2);
					// System.out.println("The sql statement is " + sqlcmd2);
					System.out.println("This is a valid login.");
				} else { // failed login: wrong password
					result = false;
					// int failedLoginNum =
					// Integer.parseInt(result1.getString("No_failed_login"));
					// failedLoginNum++;
					// String sqlcmd3 = "UPDATE users SET No_failed_login='" +
					// failedLoginNum + "' WHERE Username ='" + username + "';";
					// stmt1.executeUpdate(sqlcmd3);
					// System.out.println("The sql statement is " + sqlcmd3);
					System.out.println("Login failed. Wrong password.");
				}
			} else { // the user doesn't exist
				result = false;
				System.out.println("The user doesn't exist.");
			}

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getProfile(String cardId) {
		JSONObject resultJSON = new JSONObject();

		Connection conn = null;
		// query user information statement
		String sqlcmd1 = "SELECT * FROM library.borrower WHERE Card_id='" + cardId + "';";

		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result

			System.out.println("getting user information...");
			System.out.println("The sql statement is " + sqlcmd1);

			// convert the ResultSet to JSON
			JSONArray rows = new JSONArray();
			int colNum = result1.getMetaData().getColumnCount();
			while (result1.next()) {// for all rows
				JSONObject currRow = new JSONObject();
				for (int i = 1; i <= colNum; i++) { // for 1 row
					currRow.put(result1.getMetaData().getColumnLabel(i), result1.getString(i));
				}
				rows.add(currRow);
			}
			resultJSON.put("result", rows);// result has all rows

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return resultJSON;
	}

	/**
	 * 
	 * @param actPrice2
	 * @param actQuality2
	 * @param actDesc2
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param address
	 * @param phone
	 * @return
	 */
	public static boolean AddBidToCart(String bidID, String itemName, String postUserEmail, String bidUserEmail,
			String itemID, String bidderId, String postUserID, String expDesc, String expQuality, String expPrice,
			String actDesc, String actQuality, String actPrice) {
		boolean result = false;
		Connection conn = null;

		// query statement
		String sqlcmd1 = "INSERT INTO shoppingcart VALUES (" + null + ",'" + bidID + "','" + itemID + "','" + itemName
				+ "','" + bidderId + "','" + postUserID + "','" + expDesc + "','" + expQuality + "','" + expPrice
				+ "','" + actDesc + "','" + actQuality + "','" + actPrice + "','" + 1 + "','" + bidUserEmail + "','"
				+ postUserEmail + "');";

		System.out.println("sql cmd: " + sqlcmd1);
		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			stmt1.executeUpdate(sqlcmd1);
			result = true;
			System.out.println("The sql statement is " + sqlcmd1);
			System.out.println("The new bid is inserted successfully.");

			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return result;
	}

	public static boolean prodBid(String itemName, String postUserEmail, String bidUserEmail, String itemID,
			String bidderId, String postUserID, String expDesc, String expQuality, String expPrice, String actDesc,
			String actQuality, String actPrice) {

		boolean result = false;
		Connection conn = null;

		// query statement
		String sqlcmd1 = "INSERT INTO bid VALUES (" + null + ",'" + itemID + "','" + bidderId + "','" + postUserID
				+ "','" + expDesc + "','" + expQuality + "','" + expPrice + "','" + actDesc + "','" + actQuality + "','"
				+ actPrice + "','" + itemName + "','" + postUserEmail + "','" + bidUserEmail + "');";

		System.out.println("sql cmd: " + sqlcmd1);
		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			stmt1.executeUpdate(sqlcmd1);
			result = true;
			System.out.println("The sql statement is " + sqlcmd1);
			System.out.println("The new bid is inserted successfully.");

			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return result;
	}

	public static boolean updateUser(String username, String password, String firstName, String lastName, String email,
			String address1, String address2, String city, String state, String country, String dateofbirth,
			String phone, String gender) {
		boolean result = false;
		Connection conn = null;

		// query statement
		String sqlcmd1 = "SELECT * FROM user_data.users WHERE Username='" + username + "';";

		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println("The sql statement is " + sqlcmd1 + password + firstName + lastName + email + address1
					+ address2 + city + state + country + dateofbirth + phone + gender);
			if (result1.next()) { // the user already exists
				result = true;
				// int noFailedLogin = 0;
				String sqlcmd2 = "UPDATE users SET U_First_Name='" + firstName + "',Address_Line1='" + address1
						+ "',Address_Line1='" + address2 + "',U_Last_Name='" + lastName + "',Pass='" + password
						+ "',Email_Id='" + email + "',Birth_Date='" + dateofbirth + "',Gender='" + gender + "',City='"
						+ city + "',State='" + state + "',Country='" + country + "',Ph_No='" + phone
						+ "' WHERE Username ='" + username + "';";
				// String sqlcmd2 = "INSERT INTO users VALUES (" + null + ",'" +
				// username + "','" + firstName + "','" + lastName + "','" +
				// password + "','" + email + "','" + dateofbirth + "','" +
				// gender + "','" + city + "','" + state + "','" + country +
				// "','" + phone + "','" + address1 + "','" + address2 + "'," +
				// null + "," + null + ",'" + noFailedLogin + "');";
				stmt1.executeUpdate(sqlcmd2);
				System.out.println("The sql statement is " + sqlcmd2);
				System.out.println("The user updated information successfully.");

			} else { // the user doesn't exist
				result = true;
				System.out.println("The username does not exists.");
			}

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return result;
	}

	public static boolean prodPost(String username, String itemName, String itemPrice, String itemDesc,
			String itemCategory, String itemQuality, String add1, String add2, String country, String state,
			String city) {

		boolean result = false;
		Connection conn = null;

		// query statement
		String sqlcmd1 = "INSERT INTO product VALUES (" + null + ",'" + username + "','" + itemName + "','" + itemPrice
				+ "','" + itemDesc + "','" + itemCategory + "','" + itemQuality + "','" + add1 + "','" + add2 + "','"
				+ country + "','" + state + "','" + city + "');";

		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			stmt1.executeUpdate(sqlcmd1);
			result = true;
			System.out.println("The sql statement is " + sqlcmd1);
			System.out.println("The new product is inserted successfully.");

			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return result;
	}

	public static ArrayList<ArrayList<String>> searchPostBidsByTitle(String title) {
		// JSONObject resultJSON = new JSONObject();
		ArrayList<ArrayList<String>> searchResult = null;

		Connection conn = null;
		// query book information statement
		// String sqlcmd1 = "SELECT * FROM product WHERE Post_User_Id=B.Isbn,
		// B.Title, B.Price, B.No_of_copies, GROUP_CONCAT(DISTINCT A.Author_name
		// SEPARATOR ', ') AS Author_name ";
		// sqlcmd1 += "FROM BOOK AS B, AUTHORS AS A ";
		// sqlcmd1 += "WHERE B.Isbn=A.Isbn AND B.Title LIKE '" + title + "' AND
		// B.No_of_copies>=0 GROUP BY A.Isbn ORDER BY B.Price;";
		String sqlcmd1 = "SELECT * FROM user_data.bid WHERE Prod_Id ='" + title + "';";
		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println("The sql statement is " + sqlcmd1);

			/*
			 * // convert the ResultSet to JSON JSONArray rows = new
			 * JSONArray(); int colNum = result1.getMetaData().getColumnCount();
			 * while (result1.next()) { JSONObject currRow = new JSONObject();
			 * for (int i = 1; i <= colNum; i++) {
			 * currRow.put(result1.getMetaData().getColumnLabel(i),
			 * result1.getObject(i)); } rows.add(currRow); }
			 * resultJSON.put("result", rows);
			 */
			searchResult = new ArrayList<ArrayList<String>>();
			while (result1.next()) {
				ArrayList<String> currProduct = new ArrayList<String>();
				currProduct.add(result1.getString("Bid_Id"));
				currProduct.add(result1.getString("Prod_Id"));
				currProduct.add(result1.getString("Bidder_Id"));
				currProduct.add(result1.getString("Post_User_Id"));
				currProduct.add(result1.getString("Exp_Description"));
				currProduct.add(result1.getString("Exp_Quality"));
				currProduct.add(result1.getString("Exp_Price"));
				currProduct.add(result1.getString("Act_Description"));
				currProduct.add(result1.getString("Act_Quality"));
				currProduct.add(result1.getString("Act_Price"));
				currProduct.add(result1.getString("Prod_Name"));
				currProduct.add(result1.getString("Post_Email"));
				currProduct.add(result1.getString("Bidder_Email"));
				// currProduct.add(result1.getString("P_Country"));
				// currProduct.add(result1.getString("P_Image"));
				searchResult.add(currProduct);

				// System.out.println("currProduct: " + currProduct);

			}
			System.out.println("The search product result is got successfully.");

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return searchResult;
	}

	/**
	 * get the search book result
	 * 
	 * @param title
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<ArrayList<String>> viewProductByTitle(String title) {
		// JSONObject resultJSON = new JSONObject();
		ArrayList<ArrayList<String>> viewResult = null;

		Connection conn = null;
		// query book information statement
		// String sqlcmd1 = "SELECT * FROM product WHERE Post_User_Id=B.Isbn,
		// B.Title, B.Price, B.No_of_copies, GROUP_CONCAT(DISTINCT A.Author_name
		// SEPARATOR ', ') AS Author_name ";
		// sqlcmd1 += "FROM BOOK AS B, AUTHORS AS A ";
		// sqlcmd1 += "WHERE B.Isbn=A.Isbn AND B.Title LIKE '" + title + "' AND
		// B.No_of_copies>=0 GROUP BY A.Isbn ORDER BY B.Price;";
		String sqlcmd1 = "SELECT Prod_Id, Prod_Name, P_Price, P_Description, P_Category, P_Quality, P_Address_Line1, P_Address_Line2, P_City, P_State, P_Country,Email_Id  FROM user_data.product,users WHERE Post_User_Id ='"
				+ title + "' AND Post_User_Id= Username;";
		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println("The sql statement is " + sqlcmd1);

			/*
			 * // convert the ResultSet to JSON JSONArray rows = new
			 * JSONArray(); int colNum = result1.getMetaData().getColumnCount();
			 * while (result1.next()) { JSONObject currRow = new JSONObject();
			 * for (int i = 1; i <= colNum; i++) {
			 * currRow.put(result1.getMetaData().getColumnLabel(i),
			 * result1.getObject(i)); } rows.add(currRow); }
			 * resultJSON.put("result", rows);
			 */
			viewResult = new ArrayList<ArrayList<String>>();
			while (result1.next()) {
				ArrayList<String> currProduct = new ArrayList<String>();
				currProduct.add(result1.getString("Prod_Id"));
				currProduct.add(result1.getString("Prod_Name"));
				currProduct.add(result1.getString("P_Price"));
				currProduct.add(result1.getString("P_Description"));
				currProduct.add(result1.getString("P_Category"));
				currProduct.add(result1.getString("P_Quality"));
				currProduct.add(result1.getString("P_Address_Line1"));
				currProduct.add(result1.getString("P_Address_Line2"));
				currProduct.add(result1.getString("P_City"));
				currProduct.add(result1.getString("P_State"));
				currProduct.add(result1.getString("P_Country"));
				currProduct.add(result1.getString("Email_Id"));

				// currProduct.add(result1.getString("P_Image"));
				viewResult.add(currProduct);

				// System.out.println("currProduct: " + currProduct);

			}
			System.out.println("The search product result is got successfully.");

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return viewResult;
	}

	public static ArrayList<ArrayList<String>> searchBiddersByTitle(String title) {
		// JSONObject resultJSON = new JSONObject();
		ArrayList<ArrayList<String>> searchResult = null;

		Connection conn = null;
		// query book information statement
		String sqlcmd1 = "SELECT * FROM user_data.bid WHERE Bidder_Id LIKE'" + title + "';";

		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println("The sql statement is " + sqlcmd1);

			/*
			 * // convert the ResultSet to JSON JSONArray rows = new
			 * JSONArray(); int colNum = result1.getMetaData().getColumnCount();
			 * while (result1.next()) { JSONObject currRow = new JSONObject();
			 * for (int i = 1; i <= colNum; i++) {
			 * currRow.put(result1.getMetaData().getColumnLabel(i),
			 * result1.getObject(i)); } rows.add(currRow); }
			 * resultJSON.put("result", rows);
			 */
			searchResult = new ArrayList<ArrayList<String>>();
			while (result1.next()) {
				ArrayList<String> currProduct = new ArrayList<String>();
				currProduct.add(result1.getString("Bid_Id"));
				currProduct.add(result1.getString("Prod_Id"));
				currProduct.add(result1.getString("Bidder_Id"));
				currProduct.add(result1.getString("Post_User_Id"));
				currProduct.add(result1.getString("Exp_Description"));
				currProduct.add(result1.getString("Exp_Quality"));
				currProduct.add(result1.getString("Exp_Price"));
				currProduct.add(result1.getString("Act_Description"));
				currProduct.add(result1.getString("Act_Quality"));
				currProduct.add(result1.getString("Act_Price"));
				searchResult.add(currProduct);
			}
			System.out.println("The search result is got successfully.");

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return searchResult;
	}

	public static ArrayList<ArrayList<String>> deleteItemInCart(String title, String username) {
		// JSONObject resultJSON = new JSONObject();
		ArrayList<ArrayList<String>> searchResult = null;

		Connection conn = null;
		// query book information statement
		String sqlcmd1 = "DELETE FROM user_data.shoppingcart WHERE Prod_Name='" + title + "' AND Post_User_Id='"
				+ username + "';";
		String sqlcmd2 = "SELECT * FROM user_data.shoppingcart WHERE Post_User_Id='" + username + "';";

		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0);
			stmt1.executeUpdate(sqlcmd1);// use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd2); // get the result
			System.out.println("The sql statement 1 is " + sqlcmd1);
			System.out.println("The sql statement 2 is " + sqlcmd2);

			/*
			 * // convert the ResultSet to JSON JSONArray rows = new
			 * JSONArray(); int colNum = result1.getMetaData().getColumnCount();
			 * while (result1.next()) { JSONObject currRow = new JSONObject();
			 * for (int i = 1; i <= colNum; i++) {
			 * currRow.put(result1.getMetaData().getColumnLabel(i),
			 * result1.getObject(i)); } rows.add(currRow); }
			 * resultJSON.put("result", rows);
			 */
			searchResult = new ArrayList<ArrayList<String>>();
			while (result1.next()) {
				ArrayList<String> currProduct = new ArrayList<String>();
				currProduct.add(result1.getString("Prod_Name"));
				currProduct.add(result1.getString("Act_Price"));
				currProduct.add(result1.getString("Bidder_Email"));
				currProduct.add(result1.getString("Post_Email"));
				currProduct.add(result1.getString("Item_Count"));

				// currProduct.add(result1.getString("P_Image"));
				searchResult.add(currProduct);
			}
			System.out.println("The search result is got successfully.");

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return searchResult;
	}

	public static ArrayList<ArrayList<String>> viewCart(String title) {
		// JSONObject resultJSON = new JSONObject();
		ArrayList<ArrayList<String>> searchResult = null;

		Connection conn = null;
		// query book information statement
		String sqlcmd1 = "SELECT * FROM user_data.shoppingcart WHERE Post_User_Id='" + title + "';";

		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println("The sql statement is " + sqlcmd1);

			/*
			 * // convert the ResultSet to JSON JSONArray rows = new
			 * JSONArray(); int colNum = result1.getMetaData().getColumnCount();
			 * while (result1.next()) { JSONObject currRow = new JSONObject();
			 * for (int i = 1; i <= colNum; i++) {
			 * currRow.put(result1.getMetaData().getColumnLabel(i),
			 * result1.getObject(i)); } rows.add(currRow); }
			 * resultJSON.put("result", rows);
			 */
			searchResult = new ArrayList<ArrayList<String>>();
			while (result1.next()) {
				ArrayList<String> currProduct = new ArrayList<String>();
				currProduct.add(result1.getString("Prod_Name"));
				currProduct.add(result1.getString("Act_Price"));
				currProduct.add(result1.getString("Bidder_Email"));
				currProduct.add(result1.getString("Post_Email"));
				currProduct.add(result1.getString("Item_Count"));
				currProduct.add(result1.getString("Prod_Id"));
				currProduct.add(result1.getString("Bidder_Id"));
				// currProduct.add(result1.getString("P_Image"));
				searchResult.add(currProduct);
			}
			System.out.println("The search result is got successfully.");

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return searchResult;
	}

	public static ArrayList<ArrayList<String>> searchProductsByTitle(String title) {
		// JSONObject resultJSON = new JSONObject();
		ArrayList<ArrayList<String>> searchResult = null;

		Connection conn = null;
		// query book information statement
		String sqlcmd1 = "SELECT * FROM user_data.product,users WHERE Prod_Name LIKE '" + title
				+ "' AND Post_User_Id = Username;";

		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println("The sql statement is " + sqlcmd1);

			/*
			 * // convert the ResultSet to JSON JSONArray rows = new
			 * JSONArray(); int colNum = result1.getMetaData().getColumnCount();
			 * while (result1.next()) { JSONObject currRow = new JSONObject();
			 * for (int i = 1; i <= colNum; i++) {
			 * currRow.put(result1.getMetaData().getColumnLabel(i),
			 * result1.getObject(i)); } rows.add(currRow); }
			 * resultJSON.put("result", rows);
			 */
			searchResult = new ArrayList<ArrayList<String>>();
			while (result1.next()) {
				ArrayList<String> currProduct = new ArrayList<String>();
				currProduct.add(result1.getString("Post_User_Id"));
				currProduct.add(result1.getString("Prod_Id"));
				currProduct.add(result1.getString("Prod_Name"));
				currProduct.add(result1.getString("P_Price"));
				currProduct.add(result1.getString("P_Description"));
				currProduct.add(result1.getString("P_Category"));
				currProduct.add(result1.getString("P_Quality"));
				currProduct.add(result1.getString("P_Address_Line1"));
				currProduct.add(result1.getString("P_Address_Line2"));
				currProduct.add(result1.getString("P_City"));
				currProduct.add(result1.getString("P_State"));
				currProduct.add(result1.getString("P_Country"));
				currProduct.add(result1.getString("Email_Id"));
				// currProduct.add(result1.getString("P_Image"));
				searchResult.add(currProduct);
			}
			System.out.println("The search result is got successfully.");

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return searchResult;
	}

	public static ArrayList<ArrayList<String>> updateItemCount(String item, String username, String itemCount) {
		ArrayList<ArrayList<String>> searchResult = null;
		// boolean result = false;
		Connection conn = null;

		// query statement
		String sqlcmd1 = "SELECT * FROM user_data.shoppingcart WHERE Post_User_Id='" + username + "';";

		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println("The sql statement is " + sqlcmd1 + item + username + itemCount);
			if (result1.next()) { // the user already exists
				// result = true;
				// int noFailedLogin = 0;
				String sqlcmd2 = "UPDATE shoppingcart SET Item_Count ='" + itemCount + "' where Prod_Name ='" + item
						+ "' AND Post_User_Id='" + username + "';";
				String sqlcmd3 = "SELECT * FROM user_data.shoppingcart WHERE Post_User_Id='" + username + "';";

				// String sqlcmd2 = "INSERT INTO users VALUES (" + null + ",'" +
				// username + "','" + firstName + "','" + lastName + "','" +
				// password + "','" + email + "','" + dateofbirth + "','" +
				// gender + "','" + city + "','" + state + "','" + country +
				// "','" + phone + "','" + address1 + "','" + address2 + "'," +
				// null + "," + null + ",'" + noFailedLogin + "');";
				stmt1.executeUpdate(sqlcmd2);
				System.out.println("The sql statement is " + sqlcmd2);
				System.out.println("The item count information successfully.");
				System.out.println("For Update ITEM COUNT \n The sql statement is " + sqlcmd1);
				ResultSet result2 = stmt1.executeQuery(sqlcmd3);
				searchResult = new ArrayList<ArrayList<String>>();
				while (result2.next()) {
					ArrayList<String> currProduct = new ArrayList<String>();
					currProduct.add(result2.getString("Prod_Name"));
					currProduct.add(result2.getString("Act_Price"));
					currProduct.add(result2.getString("Bidder_Email"));
					currProduct.add(result2.getString("Post_Email"));
					currProduct.add(result2.getString("Item_Count"));
					currProduct.add(result2.getString("Prod_Id"));
					currProduct.add(result2.getString("Bidder_Id"));

					// currProduct.add(result1.getString("P_Image"));
					searchResult.add(currProduct);
				}
				System.out.println("Item Count DB Updated");
			} else { // the user doesn't exist
				// result = true;
				System.out.println("The product does not exists.");
			}

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return searchResult;
	}

	public static ArrayList<ArrayList<String>> emailCart(String itemId, String itemName, String itemPrice,
			String bidderId, String postUserId, String postUserEmail, String bidUserEmail, String itemCount) {

		ArrayList<ArrayList<String>> searchResult = null;
		Connection conn = null;

		// query statement
		String sqlcmd1 = "INSERT INTO user_data.orders VALUES (" + null + ",'" + itemId + "','" + itemName + "','"
				+ bidderId + "','" + postUserId + "','" + itemPrice + "','" + itemCount + "','" + bidUserEmail + "','"
				+ postUserEmail + "');";
		String sqlcmd2 = "DELETE FROM user_data.shoppingcart WHERE Prod_Name='" + itemName + "' AND Post_User_Id='"
				+ postUserId + "';";
		String sqlcmd3 = "SELECT * FROM user_data.shoppingcart WHERE Post_User_Id='" + postUserId + "';";
		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0);
			stmt1.executeUpdate(sqlcmd1);// use the database
			stmt1.executeUpdate(sqlcmd2);// use the database

			ResultSet result1 = stmt1.executeQuery(sqlcmd3); // get the result
			System.out.println("The sql statement 1 is " + sqlcmd1);
			System.out.println("The sql statement 2 is " + sqlcmd2);
			System.out.println("The sql statement 3 is " + sqlcmd3);

			searchResult = new ArrayList<ArrayList<String>>();
			while (result1.next()) {
				ArrayList<String> currProduct = new ArrayList<String>();
				currProduct.add(result1.getString("Prod_Name"));
				currProduct.add(result1.getString("Act_Price"));
				currProduct.add(result1.getString("Bidder_Email"));
				currProduct.add(result1.getString("Post_Email"));
				currProduct.add(result1.getString("Item_Count"));
				currProduct.add(result1.getString("Prod_Id"));
				currProduct.add(result1.getString("Bidder_Id"));

				// currProduct.add(result1.getString("P_Image"));
				searchResult.add(currProduct);
			}
			System.out.println("The search result is got successfully.");

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return searchResult;
	}

	@SuppressWarnings("unchecked")
	public static JSONObject getBookInfo(String Isbn) {
		JSONObject resultJSON = new JSONObject();

		Connection conn = null;
		// query book information statement
		String sqlcmd1 = "SELECT B.Isbn, B.Title, B.Price, B.No_of_copies, GROUP_CONCAT(DISTINCT A.Author_name SEPARATOR ', ') AS Author_name ";
		sqlcmd1 += "FROM BOOK AS B, AUTHORS AS A ";
		sqlcmd1 += "WHERE B.Isbn=A.Isbn AND B.Isbn='" + Isbn + "';";

		String sqlcmd2 = "SELECT OI.Review FROM ORDERED_ITEMS AS OI WHERE OI.Isbn='" + Isbn + "';";

		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();
			Statement stmt2 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println("The sql statement is " + sqlcmd1);

			// convert the ResultSet to JSON
			result1.next();
			resultJSON.put("Isbn", result1.getString("Isbn"));
			resultJSON.put("Title", result1.getString("Title"));
			resultJSON.put("Price", result1.getString("Price"));
			resultJSON.put("No_of_copies", result1.getString("No_of_copies"));
			resultJSON.put("Author_name", result1.getString("Author_name"));

			ResultSet result2 = stmt2.executeQuery(sqlcmd2); // get the result
			System.out.println("The sql statement is " + sqlcmd2);

			JSONArray reviews = new JSONArray();
			while (result2.next()) {
				JSONObject currReview = new JSONObject();
				currReview.put("Review", result2.getString("Review"));
				reviews.add(currReview);
			}
			resultJSON.put("Reviews", reviews);
			System.out.println("The book information is got successfully.");

			result1.close();
			result2.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return resultJSON;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<ArrayList<String>> getOrderHistory(String username) {
		// JSONObject resultJSON = new JSONObject();
		ArrayList<ArrayList<String>> orderHistory = null;

		Connection conn = null;
		// query order history information statement
		String sqlcmd1 = "SELECT OH.Order_id, OH.Order_time, OH.Total_price, OI.Isbn, B.Title, OI.Quantity, OI.Unit_price, OI.Review ";
		sqlcmd1 += "FROM ORDER_HISTORY AS OH, ORDERED_ITEMS AS OI, BOOK AS B ";
		sqlcmd1 += "WHERE OH.Username='" + username
				+ "' AND OH.Order_id=OI.Order_id AND OI.Isbn=B.Isbn ORDER BY OH.Order_id DESC;";

		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println("The sql statement is " + sqlcmd1);

			/*
			 * // convert the ResultSet to JSON JSONArray rows = new
			 * JSONArray(); int colNum = result1.getMetaData().getColumnCount();
			 * while (result1.next()) { JSONObject currRow = new JSONObject();
			 * for (int i = 1; i <= colNum; i++) {
			 * currRow.put(result1.getMetaData().getColumnLabel(i),
			 * result1.getString(i)); } rows.add(currRow); }
			 * resultJSON.put("result", rows); System.out.println(
			 * "The order history is got successfully.");
			 */

			orderHistory = new ArrayList<ArrayList<String>>();
			while (result1.next()) {
				ArrayList<String> currBook = new ArrayList<String>();
				currBook.add(result1.getString("Order_id"));
				currBook.add(result1.getString("Order_time"));
				currBook.add(result1.getString("Total_price"));
				currBook.add(result1.getString("Isbn"));
				currBook.add(result1.getString("Title"));
				currBook.add(result1.getString("Quantity"));
				currBook.add(result1.getString("Unit_price"));
				currBook.add(result1.getString("Review"));
				orderHistory.add(currBook);
			}
			System.out.println("The order history is got successfully.");

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return orderHistory;
	}

	public static boolean addReview(String orderId, String isbn, String review) {
		boolean result = false;
		Connection conn = null;

		// query statement
		String sqlcmd1 = "UPDATE ORDERED_ITEMS SET Review='" + review + "' WHERE Order_id='" + orderId + "' AND Isbn='"
				+ isbn + "';";

		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			stmt1.executeUpdate(sqlcmd1); // get the result
			result = true;
			System.out.println("The sql statement is " + sqlcmd1);
			System.out.println("The review is added successfully.");

			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return result;
	}

	public static boolean submitOrder(String username, String totalPrice, JSONArray items) {
		boolean result = false;
		Connection conn = null;

		// query statement
		String sqlcmd1 = "SELECT * FROM BOOKSTORE_INFO;";

		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			ResultSet result1 = stmt1.executeQuery(sqlcmd1); // get the result
			System.out.println("The sql statement is " + sqlcmd1);
			result1.next();
			int orderId = result1.getInt("Max_order_no") + 1;

			// insert a row into order_history table
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String orderTime = dateFormat.format(new Date());
			String sqlcmd2 = "INSERT INTO ORDER_HISTORY VALUES ('" + orderId + "','" + username + "','" + orderTime
					+ "','" + totalPrice + "');";
			stmt1.executeUpdate(sqlcmd2);
			System.out.println("The sql statement is " + sqlcmd2);

			// insert multiple rows into the ordered_items table
			for (int i = 0; i < items.size(); i++) {
				String isbn = ((JSONObject) items.get(i)).get("Isbn").toString();
				String quantity = ((JSONObject) items.get(i)).get("Quantity").toString();
				String unitPrice = ((JSONObject) items.get(i)).get("Unit_price").toString();
				String sqlcmd3 = "INSERT INTO ORDERED_ITEMS VALUES ('" + orderId + "','" + isbn + "','" + quantity
						+ "','" + unitPrice + "'," + null + ");";
				stmt1.executeUpdate(sqlcmd3);
				System.out.println("The sql statement is " + sqlcmd3);
			}

			// update the BOOKSTORE_INFO table
			String sqlcmd4 = "UPDATE BOOKSTORE_INFO SET Max_order_no='" + orderId + "';";
			stmt1.executeUpdate(sqlcmd4);
			System.out.println("The sql statement is " + sqlcmd4);

			result = true;
			System.out.println("Submit order successfully.");

			result1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return result;
	}

	public static boolean updateUserAddress(String username, String newAddress) {
		boolean result = false;
		Connection conn = null;

		// query statement
		String sqlcmd1 = "UPDATE USERS SET Address='" + newAddress + "' WHERE Username='" + username + "';";

		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			stmt1.executeUpdate(sqlcmd1);
			result = true;
			System.out.println("The sql statement is " + sqlcmd1);
			System.out.println("The address is updated successfully.");

			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return result;
	}

	public static boolean updateUserPhone(String username, String newPhone) {
		boolean result = false;
		Connection conn = null;

		// query statement
		String sqlcmd1 = "UPDATE USERS SET Phone='" + newPhone + "' WHERE Username='" + username + "';";

		try {
			// connect to database
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, mysqluser, mysqlpassword);
			Statement stmt1 = conn.createStatement();

			stmt1.executeQuery(sqlcmd0); // use the database
			stmt1.executeUpdate(sqlcmd1);
			result = true;
			System.out.println("The sql statement is " + sqlcmd1);
			System.out.println("The phone is updated successfully.");

			conn.close();
		} catch (Exception e) {
			System.out.println("Error occurred during communicating with database.");
			e.printStackTrace();
		}

		return result;
	}

	
}
