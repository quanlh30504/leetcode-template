package Dp.Knapsack;

public class Practice {
    

    public int knapsack(
        int[] weights,
        int[] values,
        int capacity
    ) {
        int n = weights.length;

        // dp[i][c] : this is the best value with first i item and capacity c
        // transition
        /*
            case 1: skip item i -> dp[i][c] = dp[i-1][c]
            case 2: if weight of item i is not greater than capacity -> dp[i][c] = values[i] + dp[i-1][c-weights[i]]
        */
        int[][] dp = new int[n +_1][capacity + 1];
        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            int weight = weights[i-1];
            int value = values[i-1];
            
            for (int c = 0; c <= capacity; c++) {
                //skip
                dp[i][c] = dp[i-1][c];

                // take
                if (c >= weight) {
                    dp[i][c] = Math.max(dp[i][c], value + dp[i-1][c - weight]);
                }
            }
        }

    
        return dp[n][capacity];

        
    }


    public int knapsack1D (
        int[] weights,
        int[] values,
        int capacity
    ) {

        // dp[i] the best value with capacity c
        int[] dp = new int[capacity + 1];


        // 2 action 
        /*
            skip : dp[c] = dp[c] (because we iterate through each item)
            take: dp[c] = values[i] + dp[c - weights[i]];

            -> dp[c] = max of take action and skip action
        */

        for (int i = 0; i < weights.length; i++) {
            int value = values[i];
            int weight = weights[i];


            for (int c = capacity; c >= weight; c--) {
                // at item i = 2 -> value 2 and weight = 2 
                // we go through 2 capacity like c = 2 and c = 4
                // dp[2] = value + dp[0] = 2
                // dp[4] = value + dp[2], dp[2] have value of item i
                dp[c] = Math.max(dp[c], value + dp[c - weight]);

            }
        }




        return dp[capacity];
    }
}
