package ivPrograms;

public class sample {
	public static void main(String args[]) {
		String name = "I am shankar";
		String rev = "";
		int len = name.length();

		for (int i = len - 1; i >= 0; i--) {

			rev = rev + name.charAt(i);
		}
		System.out.println(rev);
	}
}
