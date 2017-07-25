package services;

import javax.mail.Session;
import javax.servlet.http.HttpSession;
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
import beans.ProductBean;
import beans.UserBean;

@Path("/signupservices")
public class SignUpServices {
	
	@Path("/newuser")
	@POST
	@Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
	public Response addNewUser(String data) {
		boolean response = false;
		String isAddNewUserSuccessful = null; //should be set to false
		Gson gson = new Gson();
		UserBean user = gson.fromJson(data, UserBean.class);
		
		String fname = user.getName();
		String ssn = user.getSSN();
		String lname = user.getLastName();
		
		String email = user.getEmail();
		String phone = user.getPhone();
		String address = user.getAddress();
		String city = user.getCity();
		String state = user.getState();
		
		System.out.println("this is the email address entered " + email);
		//DBOperation dao = new DBOperation();
		isAddNewUserSuccessful = DBOperation.userSignUp(ssn, fname, lname,address,city,state,email, phone);
		System.out.println("isAddNewUserSuccessful: " + isAddNewUserSuccessful);
		
		//sql code to add userInformation to database goes here
		
		//UserBean product = new UserBean();
		if(!(isAddNewUserSuccessful == null)){
			response = true;
			System.out.println("value of string is: " + String.valueOf(response));

	
			
		    user.setCardId(isAddNewUserSuccessful);
		    user.setValidation(response);
		    //Session session = new Session();
			//session.setAttribute("USER", userBean);
			System.out.println("in services: card id= " + user.getCardId());
			//EmailService email = new EmailService();
			//email.setEmailTo(emailAddress);
			//email.setEmailFrom("mastraghav@gmail.com");
			//email.setHost("smtp.gmail.com");
			//email.setProperties();
			//email.setSession();
			// debug code -> System.out.println(emailAddress);
			
			//default message for now
			//String subject = "Online Bidding successful registration";
			//String msg = "Congratulations " + firstName + " you've successfully created an account " +
			//			"\nyour username is " + username +
			//			"\n\nEnjoy our service!!!";
			
			//email.sendEmail(subject, msg); 
		}
		else{
			response = false;
		}
		//System.out.println("value of string is: " + String.valueOf(response));

		Gson searchResultJson = new Gson();
		String responseData = searchResultJson.toJson(user);
		//System.out.println("value of string is: " + responseData);
		//return Response.ok().entity(responseData).build();
		return Response.ok().entity(String.valueOf(responseData)).build();
	}
	
	@Path("/availableusername/{username}")
	@GET
	public String availableUsername(@PathParam("username") String username) {
		//code here to see if userName exists		
		return username + "001";
	}

}
