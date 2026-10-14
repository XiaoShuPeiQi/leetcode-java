package leetcode.greedy;

import java.util.*;

public class LC0435NonOverlappingIntervals {

    public static void main(String[] args) {
        // 测试来源：飞书贪心笔记的实例走过程，2026-10-14读取。
        // https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf
        int[][] intervals = new int[][]{{1,2},{2,3},{3,4},{1,3}};
        int expected = 1;
        int actual = new Solution().eraseOverlapIntervals(intervals);
        System.out.println("Input:");
        System.out.println("intervals = [[1, 2], [2, 3], [3, 4], [1, 3]]");
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
        public int eraseOverlapIntervals(int[][] intervals) {
            if (intervals.length == 0) return 0;
            intervals = intervals.clone();
            Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
            int end = intervals[0][1];
            int kept = 1;
            for (int i = 1; i < intervals.length; i++) {
                if (intervals[i][0] >= end) {
                    kept++;
                    end = intervals[i][1];
                }
            }
            return intervals.length - kept;
        }
    }
}
