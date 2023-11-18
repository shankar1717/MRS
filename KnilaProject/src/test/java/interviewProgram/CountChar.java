package interviewProgram;

public class CountChar {

	public static void main(String[] args) {

		String str= "Allegion  is my";
		int len= str.length();
		int count=0;
		
		for(int i=0; i<len; i++) {
			if(str.charAt(i)!= ' ') {
				count++;
				}
		}
        System.out.println("Total number of characters in a string: " + count);    

	}

}
