package interviewPrograms;

public class FactorialNumber {

	public static void main(String[] args) {

		int factorial=1;
		int num=4;
		for(int i=1; i<=num; i++) {
			factorial= factorial*i;
		}
		System.out.println("factorial number: "+ num + " is = " + factorial);
	}

}
