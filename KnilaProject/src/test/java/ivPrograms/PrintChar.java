package ivPrograms;

public class PrintChar {

	public static void main(String[] args) {

		String name="Allegion";
		char a[]=name.toCharArray();
		int len=a.length;
		int count=0;
		for(int i=0 ;i<len; i++) {
				count=i+1;
				System.out.println(a[i] + ":" + count);

			}
			
	}
}

