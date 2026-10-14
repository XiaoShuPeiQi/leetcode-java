package leetcode.greedy;

import java.util.*;

public class LC0455AssignCookies {

    public static void main(String[] args) {
        // 测试来源：飞书贪心笔记的实例走过程，2026-10-14读取。
        // https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf
        int[] g = new int[]{1,2,3};
        int[] s = new int[]{1,1,3};
        int expected = 2;
        int actual = new Solution().findContentChildren(g, s);
        System.out.println("Input:");
        System.out.println("g = [1, 2, 3], s = [1, 1, 3]");
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
        public int findContentChildren(int[] g, int[] s) {
            g = g.clone();
            s = s.clone();
            Arrays.sort(g);
            Arrays.sort(s);
            int child = 0;
            for (int cookie = 0; cookie < s.length && child < g.length; cookie++) {
                if (s[cookie] >= g[child]) {
                    child++; // 当前饼干满足最小的待满足胃口
                }
            }
            return child;
        }
    }
}
