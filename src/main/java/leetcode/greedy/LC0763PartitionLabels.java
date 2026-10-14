package leetcode.greedy;

import java.util.*;

public class LC0763PartitionLabels {

    public static void main(String[] args) {
        // 测试来源：飞书贪心笔记的实例走过程，2026-10-14读取。
        // https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf
        String s = "abac";
        List<Integer> expected = List.of(3, 1);
        List<Integer> actual = new Solution().partitionLabels(s);
        System.out.println("Input:");
        System.out.println("s = abac");
        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        boolean passed = actual.equals(expected);
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    // 保留飞书当前Solution类体，只增加static和一层缩进。
    static class Solution {
        public List<Integer> partitionLabels(String s) {
            int[] last = new int[26];
            for (int i = 0; i < s.length(); i++) last[s.charAt(i) - 'a'] = i;
            List<Integer> result = new ArrayList<>();
            int start = 0;
            int end = 0;
            for (int i = 0; i < s.length(); i++) {
                end = Math.max(end, last[s.charAt(i) - 'a']);
                if (i == end) {
                    result.add(i - start + 1);
                    start = i + 1;
                }
            }
            return result;
        }
    }
}
