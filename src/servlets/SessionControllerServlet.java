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

import beans.ProductsBean;
import beans.UserBean;
/**
 * Servlet implementation class SessionControllerServlet
 */
@WebServlet("/SessionControllerServlet")
public class SessionControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		PrintWriter out=response.getWriter();
		
		String cardId=request.getParameter("card_id");
		String name=request.getParameter("name");
		//String location = request.getParameter("location");
		UserBean bean=new UserBean();
		ProductsBean products = new ProductsBean();
		bean.setName(name);
		bean.setCardId(cardId);
		//bean.setLastLoginLocation(location);
		request.setAttribute("bean",bean);
		
		Boolean status = false;
		try {			 
			Client client = Client.create();
			WebResource webResource = client.resource("http://localhost:9443/LibraryManagement/rest/loginservices/checkuservalidity");
			//MultivaluedMap formData = new MultivaluedMapImpl();
			
			//System.out.println();
			Gson userJson = new Gson();
			String data = userJson.toJson(bean);
			//System.out.println("here is the data" + data);
			
			
			//formData.add("username", name);
			//formData.add("password", password);
			ClientResponse restResponse = webResource
			    .type(MediaType.APPLICATION_JSON)
			    .post(ClientResponse.class, data);
			
			if (restResponse.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus());
			}
			Gson gson = new Gson();
			UserBean user = gson.fromJson(restResponse.getEntity(String.class), UserBean.class);
			bean = user;
 
			//String statusString = restResponse.getEntity(String.class);
			status = user.isValidUser();
			//status = Boolean.parseBoolean(statusString);//parse model here
			
			//String statusString = restResponse.getEntity(String.class);
			//status = Boolean.parseBoolean(statusString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(status){
			HttpSession session = request.getSession();
			session.setAttribute("USER", bean);
			UserBean userBean=(UserBean)session.getAttribute("USER");
			System.out.println("session data: " + userBean.getCardId());
			HttpSession session2 = request.getSession();
			session2.setAttribute("PRODUCTS", products);
			RequestDispatcher rd=request.getRequestDispatcher("BorrowBooks.jsp");
			rd.forward(request, response);
			
		
		}
		else{
			RequestDispatcher rd=request.getRequestDispatcher("LoginRegisterFail2.jsp");
			rd.forward(request, response);
		}
	}

}
