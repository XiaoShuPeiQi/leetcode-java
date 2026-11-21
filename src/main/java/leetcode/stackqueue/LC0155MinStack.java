package leetcode.stackqueue;

import java.util.ArrayDeque;
import java.util.Deque;
import common.TreeSupport;

public class LC0155MinStack {
    public static void main(String[] args) {
        TreeSupport.check("MinStack", "push(3), push(1), push(1), getMin(), pop(), getMin(), pop(), top(), getMin()",
                new int[]{1, 1, 3, 3}, runSolution());
    }

    // 测试来源：飞书 Hot100 第 70 题的实例走过程。
    static Object runSolution() {
        MinStack stack = new MinStack();
        stack.push(3);
        stack.push(1);
        stack.push(1);
        int firstMin = stack.getMin();
        stack.pop();
        int secondMin = stack.getMin();
        stack.pop();
        int top = stack.top();
        int thirdMin = stack.getMin();
        return new int[]{firstMin, secondMin, top, thirdMin};
    }

    static class MinStack {
        private final Deque<Integer> stack;
        private final Deque<Integer> minStack;

        public MinStack() {
            stack = new ArrayDeque<>();
            minStack = new ArrayDeque<>();
        }

        public void push(int val) {
            stack.push(val);

            // 记录数据栈处于当前状态时的最小值
            if (minStack.isEmpty()) {
                minStack.push(val);
            } else {
                minStack.push(Math.min(val, minStack.peek()));
            }
        }

        public void pop() {
            // 两个栈同步弹出，恢复到上一个状态
            stack.pop();
            minStack.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }
}
