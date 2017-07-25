package beans;

import java.util.ArrayList;

public class ProductBean {

	private String isbn;
	private String cardId;
	private String author;
	private String title;
	private String availability;
	private String fname;
	private String lname;
	private String due_date;
	private String date_out;
	private String loanId;
	private String fine;
	private String paid;
	private String dateIn;
	
	ArrayList<String> product1 = new ArrayList<String>();

	public ProductBean() {

		availability = "";
		isbn = "";
		author = "";
		title = "";
		cardId = "";
		fname = "";
		lname = "";
		due_date = "";
		date_out = "";
		loanId="";
		fine="";
		paid="";
		dateIn ="";
	}
	public void addCardId(String cardId)
	{
		product1.add(cardId);
	}
	// setters
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public void setFine(String fine) {
		this.fine = fine;
	}
	public void setPaid(String paid) {
		this.paid = paid;
	}
	public void setDateIn(String dateIn) {
		this.dateIn = dateIn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public void setAuthor(String author) {
		this.author = author;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public void setFirstName(String fname) {
		this.fname = fname;
	}

	public void setLastName(String lname) {
		this.lname = lname;
	}

	public void setDueDate(String due_date) {
		this.due_date = due_date;
	}

	public void setDateOut(String date_out) {
		this.date_out = date_out;
	}

	public String getCardId() {
		return cardId;
	}
	public String getLoanId() {
		return loanId;
	}
	public String getFine() {
		return fine;
	}
	public String getPaid() {
		return paid;
	}
	public String getDateIn() {
		return dateIn;
	}
	
	public String getFirstName() {
		return fname;
	}

	public String getLastName() {
		return lname;
	}

	public String getDueDate() {
		return due_date;
	}

	public String getDateOut() {
		return date_out;
	}

	public String getTitle() {
		return title;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getAvailability() {
		return availability;
	}

	public String getAuthor() {
		return author;
	}

}
