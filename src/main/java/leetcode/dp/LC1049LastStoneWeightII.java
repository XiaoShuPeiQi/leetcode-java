package leetcode.dp;

import java.util.*;

public class LC1049LastStoneWeightII {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        int[] stones = new int[]{2,7,4,1,8,1};
        int expected = 1;
        int actual = new Solution().lastStoneWeightII(stones);
        System.out.println("Input:\n" + "stones = [2, 7, 4, 1, 8, 1]");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    static class Solution {
        public int lastStoneWeightII(int[] stones) {
            int sum = 0;
            for (int stone : stones) sum += stone;
            int target = sum / 2;
            int[] dp = new int[target + 1];
            for (int stone : stones) {
                for (int j = target; j >= stone; j--) {
                    dp[j] = Math.max(dp[j], dp[j - stone] + stone);
                }
            }
            return sum - 2 * dp[target];
        }
    }
}
