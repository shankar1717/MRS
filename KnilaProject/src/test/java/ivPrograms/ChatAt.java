package ivPrograms;

public class ChatAt {

	public static void main(String[] args) {
		String str= "ABCD";
		char a[]=str.toCharArray();
		String rev ="";
		int len=a.length;
		for(int i=len-1; i>=0; i--) {
			rev= rev+a[i];
			System.out.println(a[i]);
		}
		System.out.println("Reversed String Is:" + rev);
		
	
	}

}
