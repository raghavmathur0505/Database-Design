package services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import DAO.DBOperation;
import beans.LoanBean;
import beans.ProductsBean;
//import beans.RegisterBidBean;

@Path("/returnloan")
public class ReturnLoan {
	
	@Path("/newreturn")
	@POST
	@Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
	public Response addNewUser(String data) {
		boolean response = false;
		boolean isAddNewUserSuccessful = false; //should be set to false
		Gson gson = new Gson();
		//ProductsBean products = gson.fromJson(data, ProductsBean.class);
		LoanBean bean = gson.fromJson(data, LoanBean.class);
		
		
		//
		
		//initialize prod_id and u_id here
		
		//String bidID = user.getBidID();
	
		//String availability= bean.getAvailability();
		String cardId= bean.getCardId();
		String dateDue= bean.getDateDue();
		String dateOut =bean.getDateOut();
		String isbn =bean.getIsbn();
		String dateIn= bean.getDateIn();
		
		
		
		
		
		System.out.println("tdate in in service:" + dateIn);
		//DBOperation dao = new DBOperation();
		isAddNewUserSuccessful = DBOperation.ReturnBook(cardId,isbn,dateOut,dateDue,dateIn);
		System.out.println(isAddNewUserSuccessful);
		
		//sql code to add userInformation to database goes here
		
		
		if(isAddNewUserSuccessful){
			response = true;
			System.out.println("value of string in service is: " + String.valueOf(response));
			
		
		}
		else{
			response = false;
		}
		//System.out.println("value of string is: " + String.valueOf(response));
		return Response.ok().entity(String.valueOf(response)).build();
	}
	
	@Path("/availableusername/{username}")
	@GET
	public String availableUsername(@PathParam("username") String username) {
		//code here to see if userName exists		
		return username + "001";
	}

}
