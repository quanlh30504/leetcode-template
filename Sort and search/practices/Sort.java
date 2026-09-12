

class Sort {


    public static void bubbleSort(int[] arr) {

        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j+1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
            }   
        }

    }

    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minId = i;

            for (int j = i; j < n; j++) {
                if (arr[j] < arr[minId]) {
                    minId = j;
                }
            }

            if (minId != i) {

                int tmp = arr[i];
                arr[i] = arr[minId];
                arr[minId] = tmp;
            }
        }
    }

    public static void insertionSort (int[] arr) {
        int n = arr.length;

        for (int i = 1; i < n; i++) {
            int value = arr[i];
            int j = i -1;
            while (j >= 0) {
                if (arr[j] > value) {
                    arr[j+1] = arr[j];
                } else if (arr[j] <= value) {
                    arr[j+1] = value;
                    break;
                }
                j--;
            }
            
        }
    }
    
    public static void merge(int[] arr, int l, int mid, int r) {
        int[] tmp = new int[l-r+1];

        int i = l;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= r) {
            if (arr[i] < arr[j]) {
                tmp[k++] = arr[i++];
            }else {
                tmp[k++] = arr[j++];
            }
        }
         
    }

    public static void mergeSort(int[] arr, int l , int r) {
        if (l > r) return;
        int mid = l + (r-l)/2;

        mergeSort(arr, l, mid);
        mergeSort(arr, mid + 1, r);

        merge(arr, l, mid, r);
    }



    public static void main(String[] args) {
        int[] arr = {1,3,2,6,4,5};
        // bubbleSort(arr);
        // selectionSort(arr);
        insertionSort(arr);

        for (int x : arr) {
            System.out.println(x);
        }
    }

}