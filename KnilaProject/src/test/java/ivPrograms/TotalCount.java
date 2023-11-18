package ivPrograms;

public class TotalCount {

	public static void main(String[] args) {

		String name = "Allegion is my company";
		int len = name.length();
		int count = 0;
		for (int i = 0; i < len; i++) {
			if (name.charAt(i) != ' ')
				count++;
		}
		System.out.println("Total Char:" + count);
	}

}
