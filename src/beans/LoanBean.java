package beans;

public class LoanBean {

	private String isbn;
	private String dateIn;
	private String cardId;
	private String availability;
	private String dateDue;
	private String dateOut;

	public LoanBean() {

		cardId = "";
		isbn = "";
		dateDue = "";
		dateOut = "";
		dateIn = "";
	}

	// setters

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public void setDateDue(String dateDue) {
		this.dateDue = dateDue;
	}

	public void setDateOut(String dateOut) {
		this.dateOut = dateOut;
	}

	public String getCardId() {
		return cardId;
	}

	public String getIsbn() {
		return isbn;
	}
	public String getAvailability() {
		return availability;
	}

	public String getDateDue() {
		return dateDue;
	}

	public String getDateOut() {
		return dateOut;
	}
	public String getDateIn() {
		return dateIn;
	}

	public void setDateIn(String dateIn) {
		this.dateIn=dateIn;
		
	}

}
