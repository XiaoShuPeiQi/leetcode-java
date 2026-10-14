package leetcode.greedy;

import java.util.*;

public class LC0135Candy {

    public static void main(String[] args) {
        // 测试来源：飞书贪心笔记的实例走过程，2026-10-14读取。
        // https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf
        int[] ratings = new int[]{1,0,2};
        int expected = 5;
        int actual = new Solution().candy(ratings);
        System.out.println("Input:");
        System.out.println("ratings = [1, 0, 2]");
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
        public int candy(int[] ratings) {
            int[] candy = new int[ratings.length];
            Arrays.fill(candy, 1);
            for (int i = 1; i < ratings.length; i++) {
                if (ratings[i] > ratings[i - 1]) candy[i] = candy[i - 1] + 1;
            }
            for (int i = ratings.length - 2; i >= 0; i--) {
                if (ratings[i] > ratings[i + 1]) {
                    candy[i] = Math.max(candy[i], candy[i + 1] + 1);
                }
            }
            int result = 0;
            for (int count : candy) result += count;
            return result;
        }
    }
}
