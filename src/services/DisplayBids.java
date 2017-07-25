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
import beans.ProductsBean;
//import beans.Books;
//import beans.PostBean;

//import beans.UserBean;

@Path("/displaybids")
public class DisplayBids {
	
	@Path("/display")
	@POST
	@Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
	public Response addNewUser(String data) 
	{
		
		boolean response = false;
		Gson gson = new Gson();
		//PostBean post = gson.fromJson(data, PostBean.class);
		ProductsBean products = new ProductsBean();
		//PostBean post=new PostBean();
		//search.getSearch();
		
		//System.out.println("this is the value of search: " + search);
		
		//sql code to add userInformation to database goes here
		String title =" ";
		//System.out.println("The title is: " + title);
		ArrayList<ArrayList<String>> postResult = DBOperation.viewBooks(title);
		System.out.println("The search result is: " + postResult);
		//post.setpostResult(postResult);
		//System.out.println("index 0 is: " + searchResult.get(0).get(0));
		
		if(postResult != null){
			response = true;
			//post.setpostResult(postResult);
			products.setValidation(response);
			System.out.println("post result size " + postResult.size());
			
			for(int index=0;index < postResult.size();index++)
			{
				ProductBean product = new ProductBean();
				
				product.setIsbn(postResult.get(index).get(0));
				product.setTitle(postResult.get(index).get(1));
				product.setAuthor(postResult.get(index).get(2));
				product.setAvailability(postResult.get(index).get(3));
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
			products.setValidation(response);
			
		}

		/*
		if(isSearchSuccessful){
			response = true;
			
			List<Book> books = new ArrayList<Book>();
			//books.add(book);
			Book book = new Book();
			book.setAuthor("J.K. Rowling");
			book.setInventory(2);
			book.setIsbn("12345");
			book.setPrice(12.99);
			book.setTitle("Harry Potter and the Philosopher's Stone");
			//books.add();
			
		}
		else
		{
			response = false;
		}
		*/
		
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
