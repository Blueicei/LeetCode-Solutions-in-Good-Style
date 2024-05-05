package n10_union_find;

public class NumberOfIslands {
    public int findCircleNum(int[][] isConnected) {
        int len = isConnected.length;
        int[] parent = new int[len];
        for(int i=0; i<len; i++) parent[i] = i;
        for(int i=0; i<len; i++){
            for(int j=0; j<len; j++){
                if(isConnected[i][j] == 1) union(parent, i, j);
            }
        }
        int rs = 0;
        for(int i=0; i<len; i++){
            if(parent[i] == i) rs++;
        }
        return rs;
    }

    private int find(int[] parent, int x){
        while(parent[x] != x){
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    private void union(int[] parent, int x, int y){
        int rootX = find(parent, x);
        int rootY = find(parent, y);
        parent[rootY] = rootX;
    }
}
