package leetcode.greedy;

import java.util.*;

public class LC0406QueueReconstructionByHeight {

    public static void main(String[] args) {
        // 测试来源：飞书贪心笔记的实例走过程，2026-10-14读取。
        // https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf
        int[][] people = new int[][]{{7,0},{4,4},{7,1},{5,0},{6,1},{5,2}};
        int[][] expected = new int[][]{{5,0},{7,0},{5,2},{6,1},{4,4},{7,1}};
        int[][] actual = new Solution().reconstructQueue(people);
        System.out.println("Input:");
        System.out.println("people = [[7, 0], [4, 4], [7, 1], [5, 0], [6, 1], [5, 2]]");
        System.out.println("Expected:");
        System.out.println(Arrays.deepToString(expected));
        System.out.println("Actual:");
        System.out.println(Arrays.deepToString(actual));
        boolean passed = Arrays.deepEquals(actual, expected);
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    // 保留飞书当前Solution类体，只增加static和一层缩进。
    static class Solution {
        public int[][] reconstructQueue(int[][] people) {
            people = people.clone();
            Arrays.sort(people, (a, b) -> a[0] == b[0]
                    ? Integer.compare(a[1], b[1]) : Integer.compare(b[0], a[0]));
            List<int[]> result = new ArrayList<>();
            for (int[] p : people) {
                result.add(p[1], p.clone()); // 已插入的人都不比当前人矮
            }
            return result.toArray(new int[result.size()][]);
        }
    }
}
