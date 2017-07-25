package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import beans.SearchBean;
import beans.UserBean;
import beans.ProductBean;
import beans.ProductsBean;
import java.util.ArrayList;

/**
 * Servlet implementation class ViewPostsServlet
 */
@WebServlet("/CheckUnreturedBookByCardIdServlet")
public class CheckUnreturedBookByCardIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()54
	 */
	public CheckUnreturedBookByCardIdServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPOST(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// String user="";
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/json");
		//ServletRequest session = null;
		// @SuppressWarnings("null")
		//HttpSession session = request.getSession();
		//UserBean userBean=(UserBean)session.getAttribute("USER");
		//String user=userBean.getName();
		String cardId = request.getParameter("cardId");
		String loanId = request.getParameter("loanId");
		String fine = request.getParameter("fine");
		String Fname = request.getParameter("Fname");
		String dateIn = request.getParameter("dateIn");
		String paid = request.getParameter("paid");
	
		//String isbn = request.getParameter("isbn");
		//String name = request.getParameter("name");
		//String cardId = userBean.getCardId();
		//System.out.println("session in servlet: "+user+" "+cardId);
		
		//cardId = "%" + cardId + "%";
		//isbn = "%" + isbn + "%";
		//name = "%" + name + "%";

		ProductsBean products = new ProductsBean();
		SearchBean search = new SearchBean();
		//System.out.println("searchtext:" + searchTitle);
		search.setSearch(cardId);
		//search.setSearch2(isbn);
		//search.setSearch3(name);
		products.setCardId(cardId);
		products.setLoanId(loanId);
		products.setFine(fine);
		products.setFirstName(Fname);
		products.setDateIn(dateIn);
		products.setPaid(paid);
		Boolean status = false;
		String cardStatus="";
		try {

			Client client = Client.create();
			WebResource webResource = client
					.resource("http://localhost:9443/LibraryManagement/rest/checkunreturnedbook/view");

			Gson userJson = new Gson();
			String data = userJson.toJson(search);

			ClientResponse restResponse = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, data);

			if (restResponse.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus());
			}

			Gson gson = new Gson();
			SearchBean searchResult = gson.fromJson(restResponse.getEntity(String.class), SearchBean.class);

			System.out.println("servlet printing now: ");
			// searchResult.getBooks();

			search = searchResult;

			//String statusString = restResponse.getEntity(String.class);
			//status = Boolean.parseBoolean(statusString);
			status = searchResult.isValidSearch();
			cardStatus= searchResult.getCard();
			System.out.println("servlet status: " + status);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (cardStatus.equalsIgnoreCase("true")) {
			//UserBean ub=new UserBean();
			//ub.setCardId(cardId);
			System.out.println("status is good!");
			HttpSession session2 = request.getSession();
			//session1.setAttribute("PRODUCTS1", products);
			session2.setAttribute("CARDID", search);
			session2.setAttribute("PRODUCTS1", products);
			RequestDispatcher rd = request.getRequestDispatcher("PayFinePage.jsp");
			rd.forward(request, response);
		} else {
			HttpSession session1 = request.getSession();
			//session1.setAttribute("PRODUCTS1", products);
			session1.setAttribute("CARDID", search);
			RequestDispatcher rd = request.getRequestDispatcher("ViewAllFines2.jsp");
			rd.forward(request, response);
		}

		// now send request to service

	}

}
