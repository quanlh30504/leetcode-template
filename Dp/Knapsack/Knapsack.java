package Dp.Knapsack;
public class Knapsack {

    public int knapsack2D(
        int[] weights,
        int[] values,
        int capacity
    ) {

        int n = weights.length;

        // dp[i][c] = max value for first i items with capacity c
        int[][] dp =
                new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {

            int weight = weights[i - 1];
            int value = values[i - 1];

            for (int c = 0; c <= capacity; c++) {

                // Skip
                dp[i][c] =
                        dp[i - 1][c];

                // Take
                if (weight <= c) {

                    dp[i][c] =
                            Math.max(
                                dp[i][c],
                                value
                                + dp[i - 1][c - weight]
                            );
                }
            }
        }

        return dp[n][capacity];
    } 


    public int knapsack1D(
        int[] weights,
        int[] values,
        int capacity
    ) {

        int[] dp =
                new int[capacity + 1];

        for (int i = 0; i < weights.length; i++) {

            int weight = weights[i];
            int value = values[i];

            for (
                    int c = capacity;
                    c >= weight;
                    c--
            ) {

                dp[c] =
                        Math.max(
                            dp[c],
                            value + dp[c - weight]
                        );
            }
        }

        return dp[capacity];
    }

}