package leetcode.greedy;

import java.util.*;

public class LC0134GasStation {

    public static void main(String[] args) {
        // 测试来源：飞书贪心笔记的实例走过程，2026-10-14读取。
        // https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf
        int[] gas = new int[]{1,2,3,4,5};
        int[] cost = new int[]{3,4,5,1,2};
        int expected = 3;
        int actual = new Solution().canCompleteCircuit(gas, cost);
        System.out.println("Input:");
        System.out.println("gas = [1, 2, 3, 4, 5], cost = [3, 4, 5, 1, 2]");
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
        public int canCompleteCircuit(int[] gas, int[] cost) {
            int total = 0;
            int sum = 0;
            int start = 0;
            for (int i = 0; i < gas.length; i++) {
                int diff = gas[i] - cost[i];
                total += diff;
                sum += diff;
                if (sum < 0) {
                    start = i + 1;
                    sum = 0;
                }
            }
            return total < 0 ? -1 : start;
        }
    }
}
