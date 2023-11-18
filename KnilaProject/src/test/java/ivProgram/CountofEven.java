package ivProgram;

public class CountofEven {

	public static void main(String[] args) {

		int count = 0;
		for (int i = 1; i <= 10; i++) {
			if (i % 2 == 0) {// for even and for odd 'if(i%2==1)'
				count = count + 1;
			}
		}
		System.out.println(count);
	}

}
