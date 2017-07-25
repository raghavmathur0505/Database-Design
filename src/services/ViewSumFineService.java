package services;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import com.google.gson.Gson;

import DAO.DBOperation;
import beans.ProductBean;
//import beans.Books;
//import beans.PostBean;
import beans.ProductsBean;
import beans.SearchBean;
//import beans.UserBean;

@Path("/viewsumfineservice")
public class ViewSumFineService {
	
	@Path("/view")
	@POST
	@Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
	public Response addNewUser(String data) 
	{
		
		boolean response = false;
		Gson gson = new Gson();
		//PostBean post = gson.fromJson(data, PostBean.class);
		ProductsBean products = new ProductsBean();
        //SearchBean search=new SearchBean();
		ArrayList<ArrayList<String>> postResult= null;
        SearchBean search = gson.fromJson(data, SearchBean.class);
		boolean resultUnpaid= search.getUnpaidFine();
		boolean resultPaid= search.getPaidFine();
		
		if(resultUnpaid)
			postResult = DBOperation.searchSumFines();
		else if(resultPaid)
			postResult =DBOperation.searchPaidSumFines();
		else
			postResult =DBOperation.searchSumFines();
		
		System.out.println("The search result is: " + postResult);
		search.setsearchResult(postResult);
		//System.out.println("index 0 is: " + searchResult.get(0).get(0));
		
		if(postResult != null){
			response = true;
			//post.setpostResult(postResult);
			search.setsearchResult(postResult);
			
			products.setValidationSearch(response);
			System.out.println("post result size " + postResult.size());
			
			for(int index=0;index < postResult.size();index++)
			{
				ProductBean product = new ProductBean();
				
				product.setCardId(postResult.get(index).get(0));
				product.setFine(postResult.get(index).get(1));
				//product.setPaid(postResult.get(index).get(2));
				//product.setCardId(postResult.get(index).get(3));
				//product.setDateIn(postResult.get(index).get(4));
				product.setFirstName(postResult.get(index).get(2));
				
				products.addProducts(product);
				
		
			}
			
			//books.getBooks();
		}
		else
		{
			response = false;
			search.setValidation(response);
			
		}

		Gson searchResultJson = new Gson();
		String responseData = searchResultJson.toJson(products);
		//System.out.println("value of string is: " + responseData);
		return Response.ok().entity(responseData).build();
	}
	
	@Path("/availableusername/{username}")
	@GET
	public String availableUsername(@PathParam("username") String username) {
		//code here to see if userName exists		
		return username + "001";
	}

}
