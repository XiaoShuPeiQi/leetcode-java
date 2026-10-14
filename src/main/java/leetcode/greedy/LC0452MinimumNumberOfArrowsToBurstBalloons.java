package leetcode.greedy;

import java.util.*;

public class LC0452MinimumNumberOfArrowsToBurstBalloons {

    public static void main(String[] args) {
        // 测试来源：飞书贪心笔记的实例走过程，2026-10-14读取。
        // https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf
        int[][] points = new int[][]{{10,16},{2,8},{1,6},{7,12}};
        int expected = 2;
        int actual = new Solution().findMinArrowShots(points);
        System.out.println("Input:");
        System.out.println("points = [[10, 16], [2, 8], [1, 6], [7, 12]]");
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
        public int findMinArrowShots(int[][] points) {
            if (points.length == 0) return 0;
            points = points.clone();
            Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
            int arrow = points[0][1];
            int result = 1;
            for (int i = 1; i < points.length; i++) {
                if (points[i][0] > arrow) {
                    result++;
                    arrow = points[i][1];
                }
            }
            return result;
        }
    }
}
