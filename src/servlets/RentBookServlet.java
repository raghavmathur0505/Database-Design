package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import beans.LoanBean;
//import beans.RegisterBidBean;

/**
 * Servlet implementation class AddToCartServlet
 */
@WebServlet("/RentBookServlet")
public class RentBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RentBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("do post is running!!");
		response.setContentType("text/json");
		PrintWriter out=response.getWriter();
		
		
		//write code for Prod_ID and U_ID here, everything else is taken care of
		
		String cardId = request.getParameter("cardId");    
		String isbn = request.getParameter("isbn"); 
		String availability = request.getParameter("availability"); 
		String dateOut = request.getParameter("dateOut");
		String dateDue = request.getParameter("dateDue"); 
		//String dateDue = request.getParameter("actDesc"); 
		
		//System.out.println("in addtocartservlet: " + itemID+ bidderId + postUserID+expDesc + actDesc+expQuality+  actQuality+ expPrice+ actPrice+ itemName+ postUserEmail + bidUserEmail);
		//System.out.println("RegisterBidServlet-postuserid : "+ postUserID );
		
		LoanBean bean=new LoanBean();
		
		//write set functions for Prod_ID and U_ID
		
		
		//bean.setBidID(bidID);
		bean.setCardId(cardId);
		bean.setIsbn(isbn);
		bean.setDateDue(dateDue);
		bean.setDateOut(dateOut);
		bean.setAvailability(availability);
		
		request.setAttribute("bean",bean);
		
		Boolean status = false;
		try {
			 
			Client client = Client.create();
			WebResource webResource = client.resource("http://localhost:9443/LibraryManagement/rest/giveloan/newloan");
			
			Gson userJson = new Gson();
			String data = userJson.toJson(bean);
			
			
			/*MultivaluedMap formData = new MultivaluedMapImpl();
			formData.add("username", username);
			formData.add("password", password);
			formData.add("firstName", firstName);
			formData.add("lastName", lastName);
			formData.add("address", address);
			formData.add("email", email);
			formData.add("phone", phone);*/
			
			
			
			//ClientResponse restResponse = webResource
			//    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
			//    .post(ClientResponse.class, formData);
			ClientResponse restResponse = webResource
				    .type(MediaType.APPLICATION_JSON)
				    .post(ClientResponse.class, data);
			
			if (restResponse.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus());
			}
 
			String statusString = restResponse.getEntity(String.class);
			status = Boolean.parseBoolean(statusString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if(status){
			//HttpSession session = request.getSession();
			//session.setAttribute("USER",user);
			System.out.println("session at the end with status= "+ String.valueOf(status) );
			RequestDispatcher rd=request.getRequestDispatcher("MainPageLoanApproved.jsp");
			rd.forward(request, response);
		}
		else{
			if(bean.getAvailability().equals("Yes"))
			{
				RequestDispatcher rd=request.getRequestDispatcher("MainPageLoanDisapproved.jsp");
				rd.forward(request, response);
			}
			else{
				RequestDispatcher rd=request.getRequestDispatcher("MainPageLoanUnavailable.jsp");
				rd.forward(request, response);
			}
				
		
		}
	
    }
}
 