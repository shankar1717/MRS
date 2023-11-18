package interviewProgram;

public class StringReverse {

	public static void main(String[] args) {

		String str="ABCDE";
		String rev="";
		int len= str.length();
		for(int i=len-1 ; i>=0; i--){
			rev= rev + str.charAt(i);
		}
		System.out.println("Reversed String Is:"  + rev);
		
		StringBuffer sb = new StringBuffer(str);
		System.out.println(sb.reverse());
		
		StringBuilder sbd = new StringBuilder(str);
		System.out.println(sbd.reverse());

	}
	

}
