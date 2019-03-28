import java.util.*;

public class SpiralTraversal {
	private final int[][] arr;
	private int rows;
	private int cols;
	private int i;
	private int j;

	public static void main(String ... args) {
		int[][] arr = new int[][] {
			{ 1, 14, 13, 12 },
			{ 2, 15, 20, 11 },
			{ 3, 16, 19, 10 },
			{ 4, 17, 18,  9 },
			{ 5,  6,  7,  8 }
		};
		SpiralTraversal acw = new SpiralTraversal(arr);
		List<Integer> order = acw.antiClockWise();
		System.out.println(order);

		arr = new int[][] {
			{  1,  2,  3,  4 },
			{ 14, 15, 16,  5 },
			{ 13, 20, 17,  6 },
			{ 12, 19, 18,  7 },
			{ 11, 10,  9,  8 }
		};
		acw = new SpiralTraversal(arr);
		order = acw.clockWise();
		System.out.println(order);


		arr = new int[][] {
			{ 1, 2, 3, 4 }
		};
		acw = new SpiralTraversal(arr);
		order = acw.clockWise();
		System.out.println(order);
		acw = new SpiralTraversal(arr);
		order = acw.antiClockWise();
		System.out.println(order);

		arr = new int[][] {
			{ 1 },
            { 2 },
            { 3 },
            { 4 }
		};
		acw = new SpiralTraversal(arr);
		order = acw.clockWise();
		System.out.println(order);
		acw = new SpiralTraversal(arr);
		order = acw.antiClockWise();
		System.out.println(order);
	}

	private SpiralTraversal(int[][] arr) {
		this.arr = arr;
		this.rows = arr.length;
		this.cols = arr[0].length;
	}

	private List<Integer> antiClockWise() {
		List<Integer> order = new ArrayList<>();
		while(rows > 0 && cols > 0) {
			if (rows > 0 && cols > 0) {
				down(order);
				j++;
			}
			if (rows > 0 && cols > 0) {
				right(order);
				i--;
			}
			if (rows > 0 && cols > 0) {
				up(order);
				j--;
			}
			if (rows > 0 && cols > 0) {
				left(order);
				i++;
			}
		}
		return order;
	}

	private List<Integer> clockWise() {
		List<Integer> order = new ArrayList<>();
		while(rows > 0 && cols > 0) {
			if (rows > 0 && cols > 0) {
				right(order);
				i++;
			}
			if (rows > 0 && cols > 0) {
				down(order);
				j--;
			}
			if (rows > 0 && cols > 0) {
				left(order);
				i--;
			}
			if (rows > 0 && cols > 0) {
				up(order);
				j++;
			}
		}
		return order;
	}

	private void down(List<Integer> order) {
		// System.out.printf("down [%d %d] rows=%d cols=%d order=%s\n", i, j, rows, cols, order);
		for(int k=0; k < rows; k++, i++) {
			order.add(arr[i][j]);
		}
		i--;
		cols--;
	}

	private void right(List<Integer> order) {
		// System.out.printf("right [%d %d] rows=%d cols=%d order=%s\n", i, j, rows, cols, order);
		for(int k=0; k < cols; k++, j++) {
			order.add(arr[i][j]);
		}
		j--;
		rows--;
	}

	private void up(List<Integer> order) {
		// System.out.printf("up [%d %d] rows=%d cols=%d order=%s\n", i, j, rows, cols, order);
		for(int k=0; k < rows; k++, i--) {
			order.add(arr[i][j]);
		}
		i++;
		cols--;
	}

	private void left(List<Integer> order) {
		// System.out.printf("left [%d %d] rows=%d cols=%d order=%s\n", i, j, rows, cols, order);
		for(int k=0; k < cols; k++, j--) {
			order.add(arr[i][j]);
		}
		j++;
		rows--;
	}
}
