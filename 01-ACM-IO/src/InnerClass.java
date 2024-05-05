import java.util.Arrays;

public class InnerClass {
    // 想使用内部类，main方法需要先new本类，还受静态类限制。所以还是先套一层成员方法，再在main里面调用
    public void test(){
        UnionFind unionFind = new UnionFind(10);
        System.out.println(unionFind.find(1));
    }
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[] arr = new int[m+n];
        int i=0, j=0;
        while(i<m&&j<n){
            if(nums1[i]<nums2[j]){
                arr[i+j] = nums1[i];
                i++;
            } else {
                arr[i+j] = nums2[j];
                j++;
            }
        }
        if(i<m) System.arraycopy(nums1, i, arr, i+j, m-i);
        if(j<n) System.arraycopy(nums2, j, arr, i+j, n-j);
        System.out.println(Arrays.toString(arr));
        int mid = (m+n)/2;
        if((m+n)%2==1) return (double)arr[mid];
        return (double)(arr[mid]+arr[mid-1])/2;


    }

    public static void main(String[] args) {
        InnerClass innerClass = new InnerClass();
        innerClass.test();
        UnionFind unionFind = innerClass.new UnionFind(10);
        int[] arr = {2,3};
        int[] arr2 = {1,4};
        innerClass.findMedianSortedArrays(arr, arr2);
    }

    class UnionFind{
        private int[] parent;
        public UnionFind(int n){
            parent = new int[n];
            for(int i = 0; i < n; i++){
                parent[i] = i;
            }
        }

        public boolean connected(int x, int y){ return find(x) == find(y); }

        public int find(int x){
            while(parent[x] != x){
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return x;
        }

        public void union(int x, int y){
            int rootX = find(x);
            int rootY = find(y);
            if(rootX == rootY) return;
            parent[rootX] = rootY;
        }
    }


}
