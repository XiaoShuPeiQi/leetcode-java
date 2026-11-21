package leetcode.stackqueue;

import java.util.ArrayDeque;
import java.util.Deque;
import common.TreeSupport;

public class LC0084LargestRectangleInHistogram {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "heights = [2, 1, 5, 6, 2, 3]",
                10, runSolution());
    }

    // 测试来源：飞书 Hot100 第 73 题的实例走过程。
    static Object runSolution() {
        int[] heights = {2, 1, 5, 6, 2, 3};
        return new Solution().largestRectangleArea(heights);
    }

    static class Solution {
        public int largestRectangleArea(int[] heights) {
            int[] newHeights = new int[heights.length + 2];
            System.arraycopy(heights, 0, newHeights, 1, heights.length);

            Deque<Integer> stack = new ArrayDeque<>();
            stack.push(0);
            int result = 0;

            for (int i = 1; i < newHeights.length; i++) {
                while (newHeights[i] < newHeights[stack.peek()]) {
                    int mid = stack.pop();
                    int width = i - stack.peek() - 1;
                    result = Math.max(result, newHeights[mid] * width);
                }
                stack.push(i);
            }

            return result;
        }
    }
}
