
public class main {
    
    
    public static int solve(int[] arr) {
        // value in [1 n] -> update index (value) = - element in index

        int n =  arr.length;

        //set
        for (int i = 0; i < n; i++) {
            if (arr[i] <= 0 || arr[i] > n) {
                arr[i] = n + 1;
            }
        }
        
        // mark
        for (int x : arr) {
            x = Math.abs(x);
            int index = x - 1;

            if (x >= 1 && x <= n) {
                if (arr[index] > 0) {
                    arr[index] = -arr[index];
                }
            }
        }


        for (int i = 0; i < n; i++) {
            if (arr[i] > 0) return i + 1;
        }
        return n + 1;
    }
}
