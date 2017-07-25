package servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.lang.Process;
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
@WebServlet("/UpdateFinesBatchServlet")
public class UpdateFinesBatchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()54
	 */
	public UpdateFinesBatchServlet() {
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
		// response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String cmdarr[] = new String[5];
		cmdarr[0] = "cmd.exe";
		cmdarr[1] = "/c";
		cmdarr[2] = "start";
		cmdarr[3] = "/min";
		cmdarr[4] = "C:/Program Files/MySQL/MySQL Server 5.7/bin/UpdateFinesBatch.bat";
		Runtime run = Runtime.getRuntime();
		try {
			System.out.println("Start Running the batch file\n");
			Process p = run.exec("cmd.exe /c start /min UpdateFinesBatch.bat", null,new File("C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin"));
			p.waitFor();
			System.out.println("Completed:");

			//Runtime load = Runtime.getRuntime();
			//Process process = load.exec("cmd.exetart copyFiles.bat", null,
				//	new File("C:\\Tomcat 5.0\\webapps\\myApp\\files"));

		} catch (Exception e) {
			System.out.println("Exception" + e);
		}

		ProductsBean products = new ProductsBean();
		SearchBean search = new SearchBean();
		// System.out.println("searchtext:" + searchTitle);
		// search.setSearch(cardId);
		// search.setSearch2(isbn);
		// search.setSearch3(name);

		Boolean status = false;
		try {

			Client client = Client.create();
			WebResource webResource = client
					.resource("http://localhost:9443/LibraryManagement/rest/viewfineservice/view");

			Gson userJson = new Gson();
			String data = userJson.toJson(search);

			// ClientResponse restResponse = webResource
			// .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
			// .post(ClientResponse.class, formData);
			ClientResponse restResponse = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, data);

			if (restResponse.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + restResponse.getStatus());
			}

			Gson gson = new Gson();
			ProductsBean searchResult = gson.fromJson(restResponse.getEntity(String.class), ProductsBean.class);

			System.out.println("servlet printing now: ");
			// searchResult.getBooks();

			products = searchResult;

			// String statusString = restResponse.getEntity(String.class);
			// status = Boolean.parseBoolean(statusString);
			status = products.isValidSearch();
			System.out.println("servlet status: " + status);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (status) {
			// UserBean ub=new UserBean();
			// ub.setCardId(cardId);
			System.out.println("status is good!");
			HttpSession session1 = request.getSession();
			session1.setAttribute("PRODUCTS1", products);
			// session1.setAttribute("USER", userBean);
			RequestDispatcher rd = request.getRequestDispatcher("ViewAllFines.jsp");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("MainPage.jsp");
			rd.forward(request, response);
		}

		// now send request to service

	}

}
