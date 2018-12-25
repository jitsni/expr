/*
Consider an array A[] of integers and following two types of queries.

update(l, r, x) : Adds x to all values from A[l] to A[r] (both inclusive).
printArray() : Prints the current modified array.
Examples :

Input : A [] { 10, 5, 20, 40 }
        update(0, 1, 10)
        printArray()
        update(1, 3, 20)
        update(2, 2, 30)
        printArray()
Output : 20 15 20 40
         20 35 70 60
Explanation : The query update(0, 1, 10) 
adds 10 to A[0] and A[1]. After update,
A[] becomes {20, 15, 20, 40}       
Query update(1, 3, 20) adds 20 to A[1],
A[2] and A[3]. After update, A[] becomes
{20, 35, 40, 60}.
Query update(2, 2, 30) adds 30 to A[2]. 
After update, A[] becomes {20, 35, 70, 60}.
*/

import java.util.*;

public class RangeUpdate {

	static int[] a = { 10, 5, 20, 40 };
	static int[] diff;

	public static void main(String... args) {
		diff = new int[a.length + 1];

		update(0, 1, 10);
        printArray();
        update(1, 3, 20);
        update(2, 2, 30);
        printArray();
	}

	static void update(int l, int r, int update) {
		diff[l] += update;
		diff[r+1] -= update;
	}

	static void printArray() {
		int[] result = new int[a.length];	
		int totalDiff  = diff[0];
		result[0] = totalDiff + a[0];
		for(int i=1; i < a.length; i++) {
			totalDiff += diff[i];
			result[i] = totalDiff + a[i];
		}
		System.out.println(Arrays.toString(result));
	}
}
