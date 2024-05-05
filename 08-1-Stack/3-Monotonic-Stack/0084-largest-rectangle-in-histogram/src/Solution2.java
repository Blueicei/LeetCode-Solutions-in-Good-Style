import java.util.Stack;

public class Solution2 {

    // 不使用哨兵的写法

    public int largestRectangleArea(int[] heights) {
        int len = heights.length;
        // 特判
        if (len == 0) {
            return 0;
        }

        Stack<Integer> stack = new Stack<>();
        int res = 0;

        for (int i = 0; i < len; i++) {
            // 这里heights[stack.peek()] > heights[i]和width = i - stack.peek() - 1，配合的太好了，面对这种动态问题判断大小是关键
            // 知道一个最高的/后面都是比他高的，后面就可以放心向后扩展了
            while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                // top 所在的柱形的最大高度可以确定
                int top = stack.pop();
                int width;

                if (stack.isEmpty()) {
                    width = i;
                } else {
                    width = i - stack.peek() - 1;
                }

                res = Math.max(res, heights[top] * width);
            }
            stack.push(i);
        }

        // 注意：如果栈里有元素，需要清空
        while (!stack.isEmpty()) {
            // top 所在的柱形的最大高度可以确定
            int top = stack.pop();
            int width;

            if (stack.isEmpty()) {
                width = len;
            } else {
                width = len - stack.peek() - 1;
            }

            res = Math.max(res, heights[top] * width);
        }
        return res;
    }
}