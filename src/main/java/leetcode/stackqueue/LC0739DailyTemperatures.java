package leetcode.stackqueue;

import java.util.ArrayDeque;
import java.util.Deque;
import common.TreeSupport;

public class LC0739DailyTemperatures {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "temperatures = [73, 74, 74, 71, 75]",
                new int[]{1, 3, 2, 1, 0}, runSolution());
    }

    // 测试来源：飞书 Hot100 第 72 题的实例走过程。
    static Object runSolution() {
        int[] temperatures = {73, 74, 74, 71, 75};
        return new Solution().dailyTemperatures(temperatures);
    }

    static class Solution {
        public int[] dailyTemperatures(int[] temperatures) {
            int[] answer = new int[temperatures.length];
            Deque<Integer> stack = new ArrayDeque<>();

            for (int i = 0; i < temperatures.length; i++) {
                // 当前温度为栈顶日期找到了右侧第一个更高温度
                while (!stack.isEmpty()
                        && temperatures[i] > temperatures[stack.peek()]) {
                    int index = stack.pop();
                    answer[index] = i - index;
                }
                stack.push(i);
            }

            return answer;
        }
    }
}
