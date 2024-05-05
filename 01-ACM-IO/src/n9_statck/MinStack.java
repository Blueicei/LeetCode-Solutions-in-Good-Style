package n9_statck;

import java.util.Stack;

class MinStack {
    // LC155. 最小栈
    // 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
    // 输入：
    // ["MinStack","push","push","push","getMin","pop","top","getMin"]
    // [[],[-2],[0],[-3],[],[],[],[]]
    // 输出：
    // [null,null,null,null,-3,null,0,-2]
    // 解释：
    // MinStack minStack = new MinStack();
    // minStack.push(-2);
    // minStack.push(0);
    // minStack.push(-3);
    // minStack.getMin();   --> 返回 -3.
    // minStack.pop();
    // minStack.top();      --> 返回 0.
    // minStack.getMin();   --> 返回 -2.

    private Stack<Integer> stack;
    private Stack<Integer> min_stack;
    public MinStack() {
        stack = new Stack<>();
        min_stack = new Stack<>();
    }
    public void push(int x) {
        stack.push(x);
        // 栈的先进后出的特性，保证中间数的前面一定存在最小值，而不是当前值因此不用再遍历找最小值
        if(min_stack.isEmpty() || x <= min_stack.peek())
            min_stack.push(x);
    }
    public void pop() {
        if(stack.pop().equals(min_stack.peek()))
            min_stack.pop();
    }
    public int top() {
        return stack.peek();
    }
    public int getMin() {
        return min_stack.peek();
    }
}
