package leetcode.stackqueue;

import java.util.*;

public class LC0239SlidingWindowMaximum {
    public static void main(String[] args) {
        // 测试来源：Hot100笔记中相同的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf
        int[] nums = new int[]{1,3,-1,-3,5,3,6,7};
        int k = 3;
        Object actual = new Solution().maxSlidingWindow(nums, k);
        String expected = "[3,3,5,5,6,7]";
        String result = canonical(actual);
        System.out.println("Input: " + "nums=[1,3,-1,-3,5,3,6,7]; k=3");
        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(result);
        if (!expected.equals(result)) {
            throw new AssertionError("Result: FAIL");
        }
        System.out.println("Result: PASS");
    }


    private static String canonical(Object value) {
        if (value instanceof int[]) {
            return Arrays.toString((int[]) value).replace(" ", "");
        }
        if (value instanceof int[][]) {
            return Arrays.deepToString((int[][]) value).replace(" ", "");
        }
        if (value instanceof List<?>) {
            List<String> parts = new ArrayList<>();
            for (Object item : (List<?>) value) {
                parts.add(canonical(item));
            }

            return "[" + String.join(",", parts) + "]";
        }
        if (value instanceof String) {
            return "\"" + value + "\"";
        }
        return String.valueOf(value);
    }


    static class Solution {
        public int[] maxSlidingWindow(int[] nums, int k) {
            int[] res = new int[nums.length - k + 1]; // 每个完整窗口对应一个答案
            int write = 0;
            Deque<Integer> deque = new ArrayDeque<>();

            for (int right = 0; right < nums.length; right++) {
                int left = right - k + 1;

                // 队首按下标判断是否已经离开窗口
                while (!deque.isEmpty() && deque.peekFirst() < left) {
                    deque.pollFirst();
                }
                // 队尾按数值淘汰旧候选：新数更大或相等，且过期更晚
                while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[right]) {
                    deque.pollLast();
                }
                deque.offerLast(right);

                if (left >= 0) { // 窗口凑满k个元素后，才向结果数组写入
                    int maxIndex = deque.peekFirst();
                    res[write] = nums[maxIndex];
                    write++;
                }
            }
            return res;
        }
    }

    // 保留此前版本，便于对照；main和Hot100调试器使用上方的清晰写法。
    static class SolutionOriginal {
        public int[] maxSlidingWindow(int[] nums, int k) {
            int[] result = new int[nums.length - k + 1];
            Deque<Integer> deque = new ArrayDeque<>();

            int write = 0;

            for (int right = 0; right < nums.length; right++) {
                int left = right - k + 1;

                // 先从队首删除已经离开窗口的下标。
                while (!deque.isEmpty() && deque.peekFirst() < left) {
                    deque.pollFirst();
                }

                // 新值更大或相等，旧下标以后也不可能更优。
                while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[right]) {
                    deque.pollLast();
                }

                deque.offerLast(right);

                // 窗口凑满 k 个元素后，队首才写入答案。
                if (left >= 0) {
                    result[write++] = nums[deque.peekFirst()];
                }
            }

            return result;
        }
    }
}
