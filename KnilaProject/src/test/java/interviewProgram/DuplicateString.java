package interviewProgram;

public class DuplicateString {
	public static void main(String[] args) {

		String arr[]= {"a", "b", "c", "a", "d"};
		
		for(int i=0; i<arr.length; i++){
			for(int j=i+1; j<arr.length; j++) {
				if(arr[i]== arr[j]) {
					System.out.println("duplicate String is: " + arr[i] );
				}
			}
		}
	}


}
