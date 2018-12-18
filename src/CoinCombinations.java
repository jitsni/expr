/*
Given many coins of 3 different face values, print the combination sums of the coins up to 1000. Must be printed in order. 

eg: coins(10, 15, 55) 
print: 
10 
15 
20 
25 
30 
. 
. 
. 
1000
*/

import java.util.*;

public class CoinCombinations {
    public static void main(String ... args) {
        Set<Integer> sums = new TreeSet<>();
        dfs(10, 15, 55, 0, sums);
        System.out.println(sums);

        printSums(10, 15, 55);
    }

    static void printSums(int c1, int c2, int c3) {

        Set<Integer> sums = new HashSet<>();
        sums.add(0);

        for(int sum = 1; sum <= 1000; sum++) {

            if(sums.contains(sum - c1) || sums.contains(sum - c2) || sums.contains(sum - c3)) {
                System.out.println(sum);
                sums.add(sum);
            }
        }
    }

    static void dfs(int c1, int c2, int c3, int sum, Set<Integer> sums) {
        if (sum > 1000) return;    
        if (sums.contains(sum)) return;

        if (sum != 0) {
            sums.add(sum);
        }

        dfs(c1, c2, c3, sum + c1, sums);
        dfs(c1, c2, c3, sum + c2, sums);
        dfs(c1, c2, c3, sum + c3, sums);
    }
}
