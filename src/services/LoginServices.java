package services;


import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;

import DAO.DBOperation;
import beans.UserBean;

import javax.ws.rs.PathParam;
 
@Path("/loginservices")
public class LoginServices {
	@Path("/checkuservalidity")
	@POST
	@Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
	public Response isValidUser(String data) {
		System.out.println("inside the login services");
		boolean response = false;
		//System.out.println("web service data " + data);
		Gson gson = new Gson();
		UserBean user = gson.fromJson(data, UserBean.class);
		System.out.println("the user name is NOW: " + user.getName());
		//System.out.println("the location is NOW: " + user.getLastLoginLocation());
		String name = user.getName();
		String cardId = user.getCardId();
		//String location = user.getLastLoginLocation();
		
		DBOperation dao = new DBOperation();
		boolean isValidUser = dao.userLogin(name, cardId);
		System.out.println("is valid user in login services?:  " + String.valueOf(isValidUser));
		
		
		//JSONObject resultJSON = dao.getProfile(cardId);//get profile data for "u.sername"
		//JSONArray rows = (JSONArray) resultJSON.get("result");
		
		
		
		//System.out.println("the username is " + formParam.getFirst("username"));
		//System.out.println("the username is " + formParam.getFirst("password"));
		
		if(isValidUser)
		{
			response = true;
			/*
			user.setFirstName(firstName);
			user.setLastName(lastName);			
			user.setAddress(dbAddress);
			user.setLastLogin(lastLogin);
			user.setPhone(phone);
			user.setEmail(email);
			user.setLoginAttempts(loginAttempts);
			user.setValidation(response); 
			for (int i = 0; i < rows.size(); i++) {//for each row
				user.setCardId(((JSONObject)rows.get(i)).get("Card_Id").toString());
				user.setSSN(((JSONObject)rows.get(i)).get("Ssn").toString());
				user.setName(((JSONObject)rows.get(i)).get("B_Fname").toString());
				user.setLastName(((JSONObject)rows.get(i)).get("B_Lname").toString());
				user.setAddress(((JSONObject)rows.get(i)).get("Address").toString());
				//user.setAddress2(((JSONObject)rows.get(i)).get("Address_Line2").toString());
				user.setCity(((JSONObject)rows.get(i)).get("City").toString());
				user.setState(((JSONObject)rows.get(i)).get("State").toString());
				//user.setCountry(((JSONObject)rows.get(i)).get("Country").toString());
				//user.setGender(((JSONObject)rows.get(i)).get("Gender").toString());
				//user.setDateOfBirth(((JSONObject)rows.get(i)).get("Birth_date").toString());
				//user.setLastLogin(((JSONObject)rows.get(i)).get("Last_login").toString());
				user.setEmail(((JSONObject)rows.get(i)).get("Email").toString());
				user.setPhone(((JSONObject)rows.get(i)).get("Phone").toString());
				//user.setLoginAttempts(Integer.parseInt(((JSONObject)rows.get(i)).get("No_failed_login").toString()));
				System.out.println("entering the loop ");
			}
			*/
			user.setLoggedIn(true);
			//user.setLoginAttempts(3);
			user.setValidation(response);	
			
		}
		else{
			response = false;
			//user.setLoginAttempts((user.getLoginAttempts()+1));
			user.setValidation(response);
		}
		
		Gson userJson = new Gson();
		String responseData = userJson.toJson(user);
		//return Response.ok().entity(String.valueOf(response)).build();
		return Response.ok().entity(responseData).build();
	}
	
	@Path("/availableusername/{username}")
	@GET
	public String availableUsername(@PathParam("username") String username) {
		
		return username + "001";
	}
}