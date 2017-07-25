package beans;

import java.util.ArrayList;

public class SearchBean {
	private String search;
	private String search2;
	private String search3;
	private ArrayList<ArrayList<String>> searchResult;
	private String searchCard;
	private boolean isValidSearch;
	private ArrayList<ArrayList<String>> searchResult2;
	private String[] searchArray;
	
	private boolean isPaidFine;
	private boolean isUnpaidFine;
	public SearchBean(){
		search="";
		search2="";
		search3="";
		searchResult = null;
		searchResult2= null;
		isValidSearch = false;
		searchCard="";
		isPaidFine=false;
		isUnpaidFine=false;
		searchArray=null;
	}

	public void setSearch(String search){
		this.search=search;
		System.out.println("this.search: " + this.search);
	}
	public void setSearch2(String search2){
		this.search2=search2;
	}
	public void setCard(String search2){
		this.searchCard=search2;
	}
	public void setPaidFine(boolean isPaidFine){
		this.isPaidFine=isPaidFine;
	}
	public void setUnpaidFine(boolean isUnpaidFine){
		this.isUnpaidFine=isUnpaidFine;
	}
	public boolean getPaidFine(){
		return isPaidFine;
	}
	public boolean getUnpaidFine(){
		return isUnpaidFine;
	}
	public void setSearch3(String search3) {
		this.search3 = search3;
	}
	public String getSearch(){
		return search;
	}
	
	public String getSearch2(){
		return search2;
	}
	public String getCard(){
		return searchCard;
	}
	
	public String getSearch3() {
		return search3;
	}
	public String[] getSearchArray(){
		return searchArray;
	}
	
	public void setSearchArray(String[] searchArray){
		this.searchArray=searchArray;
	}
	
	public void setsearchResult(ArrayList<ArrayList<String>> searchResult) {
		this.searchResult = searchResult;
	}
	public void setsearchResult2(ArrayList<ArrayList<String>> searchResult2) {
		this.searchResult2 = searchResult2;
	}
	public ArrayList<ArrayList<String>> getsearchResult() {
		return searchResult;
	}
	public ArrayList<ArrayList<String>> getsearchResult2() {
		return searchResult2;
	}
	public void setValidation(boolean isValidSearch)
	{
		this.isValidSearch = isValidSearch;
	}
	public boolean isValidSearch()
	{
		return isValidSearch;
	}
}
