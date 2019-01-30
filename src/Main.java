import java.util.Random;

public class Main {
	public static void main(String[] args) {
		Random r = new Random();
		int[][] arr = new int[9][9];
		arr[2][3]=5;
		System.out.println(Board.isSolvable(arr));
	}
}
