package ivPrograms;

public class PalindromeNumber {

	public static void main(String[] args) {
		
		int num=1221;
		int rev=0;
		int Org_num = num;
		
		while(num!=0) {
			rev= rev*10 + num%10;
			num=num/10;
		}
		System.out.println("Reversed Number is:"+  rev);
		
		if(Org_num==rev) {
			System.out.println("Number is Palindrome Number:" + rev);
		}else {
			System.out.println("Number is Not Palindrome Number:" + rev);

		}
	}

}
