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

@Path("/searchcheckoutbooks")
public class searchCheckOutBooks {
	
	@Path("/search")
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
        SearchBean search = gson.fromJson(data, SearchBean.class);
		
		//System.out.println("this is the value of search: " + search);
		
		//sql code to add userInformation to database goes here
		String cardId =search.getSearch();
		String isbn =search.getSearch2();
		String name =search.getSearch3();
		//System.out.println("The title is in servicesis : " + title);
		ArrayList<ArrayList<String>> postResult = DBOperation.getCheckOutBooks(cardId,isbn,name);
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
				
				product.setIsbn(postResult.get(index).get(0));
				product.setCardId(postResult.get(index).get(1));
				product.setFirstName(postResult.get(index).get(2));
				product.setLastName(postResult.get(index).get(3));
				product.setDateOut(postResult.get(index).get(4));
				product.setDueDate(postResult.get(index).get(5));
				/*product.setItemDesc(postResult.get(index).get(4));
				product.setItemCategory(postResult.get(index).get(5));
				product.setItemQuality(postResult.get(index).get(6));
				product.setAdd1(postResult.get(index).get(7));
				product.setAdd2(postResult.get(index).get(8));
				product.setCountry(postResult.get(index).get(9));
				product.setState(postResult.get(index).get(10));
				product.setCity(postResult.get(index).get(11));
				product.setEmailId(postResult.get(index).get(12));
				//product.setImage(postResult.get(index).get(12));
				//System.out.println(product.getUserName());
			*/
				products.addProducts(product);
				//System.out.println(products.getUSERNAME(index));
				//System.out.println("book isbn is: " + book.getIsbn());
				
				//System.out.println(searchResult.get(index).get(0));
		
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
