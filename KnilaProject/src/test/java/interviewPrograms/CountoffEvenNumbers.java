package interviewPrograms;

public class CountoffEvenNumbers {

	public static void main(String[] args) {

		int count=0;
		for(int i=1; i<=10; i++) {
			if(i%2==1) {
				count= count+1;
			}
		}
		System.out.println(count);
	}

}
