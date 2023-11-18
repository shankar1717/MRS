package interviewProgram;

public class PalindromeString {

	public static void main(String[] args) {

		String str="ADDA";
		String rev="";
		String Org_str=str;
		int len=str.length();
		for(int i=len-1; i>=0; i--) {
			rev= rev + str.charAt(i);
		}
		System.out.println("Reversed string is: " + rev);
		
		if(Org_str.equals(rev)) {
			System.out.println("string is Palindrome: " + rev);
		}else {
			System.out.println("string is Not Palindrome: " + rev);

		}
	}

}
