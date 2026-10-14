package leetcode.greedy;

import java.util.*;

public class LC0122BestTimeToBuyAndSellStockII {

    public static void main(String[] args) {
        // 测试来源：飞书贪心笔记的实例走过程，2026-10-14读取。
        // https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf
        int[] prices = new int[]{7,1,5,3,6,4};
        int expected = 7;
        int actual = new Solution().maxProfit(prices);
        System.out.println("Input:");
        System.out.println("prices = [7, 1, 5, 3, 6, 4]");
        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    // 保留飞书当前Solution类体，只增加static和一层缩进。
    static class Solution {
        public int maxProfit(int[] prices) {
            int result = 0;
            for (int i = 1; i < prices.length; i++) {
                int profit = prices[i] - prices[i - 1];
                if (profit > 0) result += profit;
            }
            return result;
        }
    }
}
