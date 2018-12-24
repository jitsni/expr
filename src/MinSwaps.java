import java.util.*;

/*
You are given an unordered array consisting of consecutive integers [1, 2, 3, ..., n] without any duplicates. You are allowed to swap any two elements. You need to find the minimum number of swaps required to sort the array in ascending order.

For example, given the array  we perform the following steps:

i   arr                         swap (indices)
0   [7, 1, 3, 2, 4, 5, 6]   swap (0,3)
1   [2, 1, 3, 7, 4, 5, 6]   swap (0,1)
2   [1, 2, 3, 7, 4, 5, 6]   swap (3,4)
3   [1, 2, 3, 4, 7, 5, 6]   swap (4,5)
4   [1, 2, 3, 4, 5, 7, 6]   swap (5,6)
5   [1, 2, 3, 4, 5, 6, 7]

It took  5 swaps to sort the array.
*/

public class MinSwaps {

    public static void main(String ... args) {
        int[] a = { 4, 3, 2, 1 };
        System.out.printf("array = %s swaps = %d\n", Arrays.toString(a), minimumSwaps(a));

        a = new int[] { 2, 3, 4, 1, 5 };
        System.out.printf("array = %s swaps = %d\n", Arrays.toString(a), minimumSwaps(a));

        a = new int[] { 1, 3, 5, 2, 4, 6, 7 };
        System.out.printf("array = %s swaps = %d\n", Arrays.toString(a), minimumSwaps(a));

        a = new int[] { 2, 1 };
        System.out.printf("array = %s swaps = %d\n", Arrays.toString(a), minimumSwaps(a));

        a = new int[] { 1, 2 };
        System.out.printf("array = %s swaps = %d\n", Arrays.toString(a), minimumSwaps(a));
    }

    // finds cycles. add all (cycle length - 1)
    static int minimumSwaps(int[] arr) {
        boolean[] visited = new boolean[arr.length];
        int swaps = 0;
        for(int i=0; i < arr.length; i++) {
            if (!visited[i]) {
                swaps += cycle(arr, visited, i, arr[i], 0);
            }
        }
        return swaps;
    }

    static int cycle(int[] arr, boolean[] visited, int i, int start, int count) {
        visited[i] = true;

        if (arr[arr[i] - 1] == start) {
            return count;
        } else {
            return cycle(arr, visited, arr[i] - 1, start, count + 1);
        }
    }

}
