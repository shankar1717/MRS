package interviewProgram;

public class PrintChar {

	public static void main(String[] args) {

		String str="ALLEGION COMPANY";
		char a[]=str.toCharArray();
		int num=0;
		for(int i=0 ; i<a.length; i++) {
			num=i+1;
			System.out.println(num +"="+ a[i] );

		}
	}

}
