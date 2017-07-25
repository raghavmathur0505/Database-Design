package beans;

import java.util.ArrayList;
import java.util.Date;

public class UserBean {
	
	private String name;
	private String password;
	private String firstName;
	private String lastName;
	private String gender;
	private String address;
	private String state;
	private String zipCode;
	private String phone;
	private String email;
	private String lastLogin;
	private int loginAttempts;
	private String lastLoginLocation;
	private boolean isValidUser;
	private boolean isLoggedIn;
	//private CartBean cart;
	private String address2;
	private String city;
	private String country;
	private String dateofbirth;
	private String cardId;
	private String ssn;
	
	public UserBean(){
		name="";
		password="";
		firstName="";
		lastName="";
		address="";
		gender="";
		state ="";
		zipCode = "";
		phone="";
		email="";
		lastLogin = "";
		loginAttempts =0;
		lastLoginLocation="";
		//cart = new CartBean();
		isLoggedIn = false;
		isValidUser = false;
		address2="";
		city="";
		state="";
		country="";
		dateofbirth="";
		ssn="";
	}
	
	//setters
	public void setGender(String gender)
	{
		this.gender=gender;
	}
	public void setCountry(String country)
	{
		this.country=country;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	
	public void setValidation(boolean isValidUser)
	{
		this.isValidUser = isValidUser;
	}
	
	public void setState(String state)
	{
		this.state = state;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
		
	public void setZipCode(String zipCode)
	{
		this.zipCode=zipCode;
	}
	
	public void setLastLogin(String lastLogin)
	{
		this.lastLogin = lastLogin;
	}
	
	public void setLoginAttempts(int loginAttempts)
	{
		this.loginAttempts = loginAttempts;
	}
	
	public void setPassword(String password)
	{
		this.password=password;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName=firstName;
	}
	
	public String getLastLoginLocation() {
		return lastLoginLocation;
	}

	public void setLastLoginLocation(String lastLoginLocation) {
		this.lastLoginLocation = lastLoginLocation;
	}

	public void setLastName(String lastName)
	{
		this.lastName=lastName;
	}
	
	public void setLoggedIn(boolean loggedIn)
	{
		this.isLoggedIn = loggedIn;
	}
	
	public void setAddress(String address)
	{
		this.address=address;
	}
	
	public void setPhone(String phone)
	{
		this.phone=phone;
	}
	
	public void setEmail(String email)
	{
		this.email=email;
	}
	
	public void setAddress2(String address2)
	{
		this.address2=address2;
	}
	public void setDateOfBirth(String dateofbirth)
	{
		this.dateofbirth=dateofbirth;
	}
	
	// end setters
	//begin getters
	public String getCardId()
	{
		return cardId;
	}
	public String getSSN()
	{
		return ssn;
	}
	public String getCity()
	{
		return city;
	}
	public String getGender()
	{
		return gender;
	}
	
	public String getCountry()
	{
		return country;
	}
	public String getAddress2()
	{
		return address2;
	}
	public String getDateOfBirth()
	{
		return dateofbirth;
	}
	public String getState()
	{
		return state;
	}
	public String getName()
	{
		return name;
	}
	
	public boolean isValidUser()
	{
		return isValidUser;
	}
	
	public String getZipCode()
	{
		return zipCode;
	}
	
	public String getLastLogin()
	{
		return lastLogin;
	}
	
	public int getLoginAttempts()
	{
		return loginAttempts;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public String getPhone()
	{
		return phone;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public boolean isLoggedIn()
	{
		return isLoggedIn;
	}

	public void setCardId(String cardId) {
		
		this.cardId=cardId;
	}

	public void setSSN(String Ssn) {
		// TODO Auto-generated method stub
		this.ssn=Ssn;
	}
	
	/*public void deleteCartItem(String strItemIndex)
	{
		cart.deleteCartItem(strItemIndex);
	}
	
	public void updateCartItem(String strItemIndex, String strQuantity)
	{
		System.out.println("inside user update cart");
		cart.updateCartItem(strItemIndex, strQuantity);
	}
	
	 public void addCartItem(String author, String title, String strUnitCost, String strQuantity, String isbn) 
	 {
		 cart.addCartItem(author, title, strUnitCost, strQuantity, isbn);
	 }
	 
	 public void addCartItem(CartItemBean cartItem) 
	 {
		 cart.addCartItem(cartItem);
	 }
	 
	 public CartItemBean getCartItem(int iItemIndex) 
	 {
		return cart.getCartItem(iItemIndex);
	 }
	 
	 public ArrayList getCartItems()
	 {
		 return cart.getCartItems();
	 }
	 
	 public void setCartItems(ArrayList alCartItems)
	 {
		 cart.setCartItems(alCartItems);
	 }
	 
	 public double getOrderTotal()
	 {
		 return cart.getOrderTotal();
	 }
	 
	 public int getLineItemCount()
	 {
		 return cart.getLineItemCount();
	 }
	 
	 public void setOrderTotal(double dblOrderTotal)
	 {
		 cart.setOrderTotal(dblOrderTotal);
	 }
	 
	 protected void calculateOrderTotal()
	 {
		 cart.calculateOrderTotal();
	 }
	 
	 protected void initCart()
	 {
		 
	 }*/
}
