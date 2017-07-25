package beans;

import java.util.ArrayList;

public class PostBean {

		private String post;//search
		private ArrayList<ArrayList<String>> postResult;
		private boolean isValidPost;
		
		public PostBean(){
			post="";
			postResult = null;
			isValidPost = false;
		}
		
		public void setPost(String post){
			this.post=post;
			System.out.println("this.post: " + this.post);
			
		}
		
		public String getPost(){
			return post;
			
		}
		
		public void setpostResult(ArrayList<ArrayList<String>> postResult) {
			this.postResult = postResult;
			System.out.println("this.setpostresult: " + this.postResult);
		}
		
		public ArrayList<ArrayList<String>> getpostResult() {
			return postResult;
		}
		
		public void setValidation(boolean isValidPost)
		{
			this.isValidPost = isValidPost;
		}
		public boolean isValidPost()
		{
			return isValidPost;
		}
	}

