public class InsertSort {
    // 插入不同与冒泡和选择的是，他不需要找到局部最大或最小的的数，只需要维护前n个局部有序就行，第n+1个用temp保存空出来一位，平移数组，放到合适的位置
    // 局部最好情况就是不需要移动就退出了循环，O(n)
    // 他比冒泡好在平移>swap，而且还能优化成ShellSort，O(n^1.3)介于nlog_n和n^2。插入和冒泡都是稳定的，选择不稳定。快排和堆排不稳定，归并稳定。三个不比较排序都稳定
    public void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j;
            // 从 i 开始，就看前面的，正因为看前面的，边界条件才是 j > 0
            for (j = i; j > 0 && arr[j - 1] > temp; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = temp;
        }
    }

    public void shell_sort(int[] arr) {
        int n = arr.length;
        int h = 1;
        while (h < n / 3) {
            h = 3 * h + 1;
        }
        while (h >= 1) {
            // insertion sort
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && arr[j] < arr[j - h]; j -= h) {
                    swap(arr, j, j - h);
                }
            }
            h = h / 3;
        }
    }

    private void swap(int[] arr, int index1, int index2) {
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}
