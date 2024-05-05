package n18_real_test;

import java.util.*;

public class DPTree {
    // 3.小美的树上染色
    //小美拿到了一棵树，每个节点有一个权值。初始每个节点都是白色。
    //小美有若干次操作，每次操作可以选择两个相邻的节点，如果它们都是白色且权值的乘积是完全平方数，小美就可以把这两个节点同时染红。
    //小美想知道，自己最多可以染红多少个节点？
    // 第一行输入一个正整数n，代表节点的数量。
    //第二行输入n个正整数a_i，代表每个节点的权值。
    //接下来的n-1行，每行输入两个正整数u,v，代表节点u和节点v有一条边连接
    // 输出描述：输出一个整数，表示最多可以染红的节点数量。
    // 输入例子：
    //3
    //3 3 12
    //1 2
    //2 3
    //输出例子：
    //2
    public static void main(String[] args) {
        final long MOD = (long) (1e9 + 7);
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        long[] value = new long[n + 1];
        List<Integer>[] next = new List[n + 1];
        //存放value
        for (int i = 1; i <= n; i++) {
            value[i] = scanner.nextInt();
            next[i] = new ArrayList<>();
        }
        //建树
        for (int i = 1; i < n; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            next[x].add(y);
            next[y].add(x);
        }
        int[] res = dpOnTheTree(1, -1, value, next);
        System.out.println(Math.max(res[0], res[1]));
    }

    //dp[0] 表示当前节点为根 当前节点不染色的最大染色数量  dp[1]则表示当前节点染色的最大染色数量
    static int[] dpOnTheTree(int cur, int pre, long[] value, List<Integer>[] next) {
        int[] dp = new int[2];
        //存放孩子节点的dp结果
        HashMap<Integer, int[]> res = new HashMap<>();

        //当前节点的dp结果分两步做
        //dp[0]
        for (int nxt : next[cur]) {
            if (nxt == pre) //pre为父节点 防止走回去咯~
                continue;
            int[] child = dpOnTheTree(nxt, cur, value, next);
            res.put(nxt, child);

            // 当前节点不染色 那就是所有孩子节点的最大值和
            dp[0] += Math.max(child[0], child[1]);
        }

        //dp[1]
        for (int nxt : next[cur]) {
            if (nxt == pre)
                continue;
            long mul = value[cur] * value[nxt];
            long sqrt = (long) Math.sqrt(mul);
            // 可以和孩子节点染色
            if (sqrt * sqrt == mul) {
                // dp[0] 存放的是所有孩子节点染色或不然染色的最大值和
                // Math.max(res.get(nxt)[0], res.get(nxt)[1]) 取需要染色的孩子节点nxt的dp[1], dp[0]的最大值
                // 以上两步计算结果 相减 就剩下了其他孩子的最大值和
                // 那么当前节点和孩子节点nxt染色 dp[1] = 其他孩子节点的dp最大值的和（也就是前两步相减的结果）+ nxt这个孩子节点的dp[0] + 2
                dp[1] = Math.max(dp[1], dp[0] - Math.max(res.get(nxt)[0], res.get(nxt)[1]) + res.get(nxt)[0] + 2);
            }
        }
        return dp;
    }

    // LC337. 打家劫舍 III
    // 小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为 root 。
    //除了 root 之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果 两个直接相连的房子在同一天晚上被打劫 ，房屋将自动报警。
    //给定二叉树的 root 。返回 在不触动警报的情况下 ，小偷能够盗取的最高金额 。
    // 输入: root = [3,4,5,1,3,null,1]
    //输出: 9
    //解释: 小偷一晚能够盗取的最高金额 4 + 5 = 9
    // 「无后效性」是指：动态规划在解决子问题的过程中，一旦某一个子问题的求解结果确定以后，就不会再被修改。求解的过程形成了一张「有向无环图」。
    //因此，子问题如何定义就很关键。常见的方法是：在设计状态的时候，维度定得细一点，通常表现为增加维度。这样一来，新的子问题就可以比较容易参考以前计算出来的子问题的结果，以避免复杂的分类讨论。

    // 树的后序遍历

    public int rob(TreeNode root) {
        int[] res = dfs(root);
        return Math.max(res[0], res[1]);
    }

    private int[] dfs(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }

        // 分类讨论的标准是：当前结点偷或者不偷
        // 由于需要后序遍历，所以先计算左右子结点，然后计算当前结点的状态值
        int[] left = dfs(node.left);
        int[] right = dfs(node.right);

        // dp[0]：以当前 node 为根结点的子树能够偷取的最大价值，规定 node 结点不偷
        // dp[1]：以当前 node 为根结点的子树能够偷取的最大价值，规定 node 结点偷
        int[] dp = new int[2];

        dp[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        dp[1] = node.val + left[0] + right[0];
        return dp;
    }

    // LC124. 二叉树中的最大路径和
    // 二叉树中的 路径 被定义为一条节点序列，序列中每对相邻节点之间都存在一条边。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。
    //路径和 是路径中各节点值的总和。
    //给你一个二叉树的根节点 root ，返回其 最大路径和 。
    // 输入：root = [1,2,3]
    //输出：6
    //解释：最优路径是 2 -> 1 -> 3 ，路径和为 2 + 1 + 3 = 6
    int ans = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        dfs1(root);
        return ans;
    }
    int dfs1(TreeNode root) {
        if (root == null) return 0;
        int left = dfs1(root.left), right = dfs1(root.right);
        int t = root.val;
        if (left >= 0) t += left;
        if (right >= 0) t += right;
        ans = Math.max(ans, t);
        return Math.max(root.val, Math.max(left, right) + root.val);
    }

}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class TraverseTree {

    static TreeNode buildTree(int[] arr, int i){
        if(i >= arr.length) return null;
        TreeNode root = new TreeNode(arr[i]);
        root.left = buildTree(arr, 2*i+1);
        root.right = buildTree(arr, 2*i+2);
        return root;
    }

    static void printTree(TreeNode root) {
        if(root == null) return;
        printTree(root.left);
        System.out.print(root.val + " ");
        printTree(root.right);
    }

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        TreeNode root = buildTree(arr,0);
        printTree(root);
    }
}

class RedTree {
    // 26.小红的小红树小红在刷小红书的时候看到了一颗挂着小红薯的小红树，所以小红也想种一颗小红树挂一些小红薯发小红书。
    // 小红有一颗树，每个结点有一个权值，初始时每个节点都是白色。
    // 小红每次操作可以选择两个相邻的结点，如果它们都是白色且权值的和是质数，小红就可以选择其中一个节点染红。
    // 小红想知道最多可以染红多少个节点？
    // 输入描述：第一行输入一个正整数n，代表节点的数量。第二行输入n个正整数ai，代表每个节点的权值。
    // 接下来的n一1行，每行输入两个正整数u,v，代表节点u和节点v有一条边连接。
    // 输出描述：输出一个整数表示答案。

    // 两数和为质数 只能染其中一个，那染父节点还是儿子节点呢？
    // 我们自底向上的考虑，只需要贪心的只染儿子节点，因为儿子节点只有一个父节点，染了儿子节点也不会和其他节点产生冲突。
    // 质数的判断用的欧拉筛
    static boolean[] isNotPrime; // true 表示为合数
    static int[] value;
    static List<Integer>[] next;
    static int res;
    public static void main(String[] args) {
        final long MOD = (long) (1e9 + 7);
        // 题目数据 a 最大为1e5 两数相加最大2e5 所以我们只需要筛出2e5之前的素数即可
        final int MAXN = (int) (2e5 + 10);

        // 欧拉筛
        isNotPrime = new boolean[MAXN];
        List<Integer> primes = new ArrayList<>();

        for(int i = 2; i < MAXN; i++){
            if(!isNotPrime[i])
                primes.add(i);
            for(int prime: primes){
                if(i * prime >= MAXN)
                    break;
                isNotPrime[i * prime] = true;

                if(i % prime == 0)
                    break;
            }
        }

        Scanner scanner = new Scanner(System.in);
        int n  = scanner.nextInt();

        value = new int[n + 1];
        next = new List[n + 1];

        //保存节点值
        for(int i = 1; i <= n; i++) {
            value[i] = scanner.nextInt();
            next[i] = new ArrayList<>();
        }
        //建树
        for(int i = 1; i < n; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            next[x].add(y);
            next[y].add(x);
        }

        // 把1看作根节点
        dfs(1, -1);
        System.out.println(res);
    }

    private static void dfs(int cur, int pre){
        for(int nxt: next[cur]){
            if(nxt == pre) // pre为父节点 防止反走
                continue;
            dfs(nxt, cur);

            int check = value[cur] + value[nxt];
            //能染就染 反正染的是儿子节点 不关父节点的事
            if(!isNotPrime[check]){
                res++;
            }
        }
    }
}