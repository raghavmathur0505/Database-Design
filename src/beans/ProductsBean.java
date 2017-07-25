package beans;
import java.util.ArrayList;
import java.util.List;

public class ProductsBean {

	private ProductBean productBean;
	private boolean isValidPost;
	private boolean isValidSearch;
	
	public boolean isValidSearch() {
		return isValidSearch;
	}
	public void setValidSearch(boolean isValidSearch) {
		this.isValidSearch = isValidSearch;
	}
	ArrayList<ProductBean> products = new ArrayList<ProductBean>();
	
	public void setIsbn(String isbn)
	{
		productBean.setIsbn(isbn);
	}
	public void setLoanId(String loanId)
	{
		productBean.setLoanId(loanId);
	}
	public void setFine(String fine)
	{
		productBean.setFine(fine);
	}
	public void setPaid(String paid)
	{
		productBean.setPaid(paid);
	}
	public void setDateIn(String dateIn)
	{
		productBean.setDateIn(dateIn);
	}
	public void setTitle(String title)
	{
		productBean.setTitle(title);
	}
	public void setAuthor(String author)
	{
		productBean.setAuthor(author);
	}
	public void setAvailability(String availability)
	{
		productBean.setAvailability(availability);
	}

	public void setValidation(boolean isValid)
	{
		this.isValidPost=isValid;
	}
	public void setValidationSearch(boolean isValidSearch){
		this.isValidSearch = isValidSearch;
	}
	public void setCardId(String cardId) {
		productBean.setCardId(cardId);
	}

	public void setFirstName(String fname) {
		productBean.setFirstName(fname);
	}

	public void setLastName(String lname) {
		productBean.setLastName(lname);
	}

	public void setDueDate(String due_date) {
		productBean.setDueDate(due_date);
	}

	public void setDateOut(String date_out) {
		productBean.setDateOut(date_out);
	}
	
	//setters end
	
	//getters begin
	
	public String getCardId() {
		return productBean.getCardId();
	}
	public String getLoanId() {
		return productBean.getLoanId();
	}
	public String getFine() {
		return productBean.getFine();
	}
	public String getPaid() {
		return productBean.getPaid();
	}
	public String getDateIn() {
		return productBean.getDateIn();
	}
	public String getFirstName() {
		return productBean.getFirstName();
	}

	public String getLastName() {
		return productBean.getLastName();
	}

	public String getDueDate() {
		return productBean.getDueDate();
	}

	public String getDateOut() {
		return productBean.getDateOut();
	}
	public String getIsbn()
	{
		return productBean.getIsbn();
	}
	public String getTitle()
	{
		return productBean.getTitle();
	}
	public String getAuthor()
	{
		return productBean.getAuthor();
	}
	public String getAvailability()
	{
		return productBean.getAvailability();
	}

	public boolean isValidPost() {
		
		return isValidPost;
	}
	
	public void addProducts(ProductBean product)
	{
		products.add(product);
	}
	public int getListSize()
	{
		return products.size();
	}
	public String getLOANID(int index)
	{
		return products.get(index).getLoanId();
	}
	public String getFINE(int index)
	{
		return products.get(index).getFine();
	}
	public String getPAID(int index)
	{
		return products.get(index).getPaid();
	}
	public String getDATEIN(int index)
	{
		return products.get(index).getDateIn();
	}
	public String getISBN(int index)
	{
		return products.get(index).getIsbn();
	}
	public String getCARDID(int index)
	{
		return products.get(index).getCardId();
	}
	public String getFIRSTNAME(int index)
	{
		return products.get(index).getFirstName();
	}
	public String getLASTNAME(int index)
	{
		return products.get(index).getLastName();
	}
	public String getDATEOUT(int index)
	{
		return products.get(index).getDateOut();
	}
	public String getDUEDATE(int index)
	{
		return products.get(index).getDueDate();
	}
	public String getTITLE(int index)
	{
		return products.get(index).getTitle();
	}
	public String getAUTHOR(int index)
	{
		return products.get(index).getAuthor();
	}
	public String getAVAILABILITY(int index)
	{
		return products.get(index).getAvailability();
	}
	public void getProducts()
	{
		System.out.println("size of books stored is: " + products.size() );
		for(int index = 0; index < products.size(); index++)
		{
			/*System.out.println("books isbn printed out: " + books.get(index).getIsbn());
			System.out.println("books title printed out: " + books.get(index).getTitle());
			System.out.println("books price printed out: " + books.get(index).getPrice());
			System.out.println("books quantity printed out: " + books.get(index).getInventory());
			System.out.println("books author printed out: " + books.get(index).getAuthor());
			System.out.println("----BOOK DIVIDER------------" );*/
			
			//System.out.println(book.getTitle());
		}	
	}

}
	
	




