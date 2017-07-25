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

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import beans.ProductsBean;
import beans.UserBean;

/**
 * Servlet implementation class SignUpControllerServlet
 */
@WebServlet("/SignUpControllerServlet")
public class SignUpControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.setContentType("text/html");
		response.setContentType("text/json");
		//PrintWriter out=response.getWriter();
		
		String fname=request.getParameter("fname");
		String lname=request.getParameter("lname");
		String ssn=request.getParameter("ssn");
		String phone=request.getParameter("phone");
		String email=request.getParameter("email");
		String city=request.getParameter("city");
		String state=request.getParameter("state");
		String address=request.getParameter("address");
		
		
		ProductsBean products= new ProductsBean();
		UserBean userBean = new UserBean();
		//map values to bean 
		userBean.setName(fname);
		userBean.setSSN(ssn);
		
		userBean.setLastName(lname);
		userBean.setAddress(address);
		userBean.setEmail(email);
		userBean.setPhone(phone);
		
		userBean.setState(state);
		userBean.setCity(city);
		
		
		
		request.setAttribute("bean",userBean);
		//debug********************************//
		//System.out.println(username);
		//System.out.println(email);
		
		Boolean status = false;
		try {
			 
			Client client = Client.create();
			WebResource webResource = client.resource("http://localhost:9443/LibraryManagement/rest/signupservices/newuser");
			
			Gson userJson = new Gson();
			String data = userJson.toJson(userBean);
			//Gson gson = new Gson();
			//UserBean user = gson.fromJson(data, UserBean.class);
			/* user.setName(fname);
			user.setSSN(ssn);
			
			user.setLastName(lname);
			user.setAddress(address);
			user.setEmail(email);
			user.setPhone(phone);
			
			user.setState(state);
			user.setCity(city); */
			/*
			MultivaluedMap formData = new MultivaluedMapImpl();
			formData.add("username", username);
			formData.add("password", password);
			formData.add("firstName", firstName);
			formData.add("lastName", lastName);
			formData.add("address1", address1);
			formData.add("email", email);
			formData.add("phone", phone);
			formData.add("address2",address2);
			formData.add("city", city);
			formData.add("state", state);
			formData.add("country", country);
			formData.add("dateofbirth", dateofbirth);
			formData.add("gender", gender);
			*/
			//ClientResponse restResponse = webResource
			  //  .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
			   // .post(ClientResponse.class, formData);
			ClientResponse restResponse = webResource
			    .type(MediaType.APPLICATION_JSON)
			  .post(ClientResponse.class, data);
			
			if (restResponse.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus());
			}

			Gson gson = new Gson();
			UserBean searchResult = gson.fromJson(restResponse.getEntity(String.class), UserBean.class);
			userBean=searchResult;
			
			
			//String statusString = restResponse.getEntity(String.class);
			//status = Boolean.parseBoolean(statusString);
			status= userBean.isValidUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(status){
			//UserBean ub =new UserBean();
			HttpSession session = request.getSession();
			session.setAttribute("USER", userBean);
			//UserBean userBean2=(UserBean)session.getAttribute("USER");
			//HttpSession session2 = request.getSession();
			session.setAttribute("PRODUCTS", products);
			System.out.println("session data: " + userBean.getCardId());
			RequestDispatcher rd=request.getRequestDispatcher("BorrowBooks.jsp");
			rd.forward(request, response);
		}
		else{
			RequestDispatcher rd=request.getRequestDispatcher("LoginRegisterFail.jsp");
			rd.forward(request, response);
		}
	
	}

}
