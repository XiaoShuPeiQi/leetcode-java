package leetcode.greedy;

import java.util.*;

public class LC0056MergeIntervals {

    public static void main(String[] args) {
        // 测试来源：飞书贪心笔记的实例走过程，2026-10-14读取。
        // https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf
        int[][] intervals = new int[][]{{1,3},{2,6},{8,10},{15,18}};
        int[][] expected = new int[][]{{1,6},{8,10},{15,18}};
        int[][] actual = new Solution().merge(intervals);
        System.out.println("Input:");
        System.out.println("intervals = [[1, 3], [2, 6], [8, 10], [15, 18]]");
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
        public int[][] merge(int[][] intervals) {
            int[][] res = new int[intervals.length][2]; // 最多产生n段，先分配足够的结果位置
            int count = 0; // res中已经写好的区间个数
            intervals = intervals.clone();
            Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

            for (int[] current : intervals) {
                if (count == 0 || current[0] > res[count - 1][1]) {
                    res[count][0] = current[0]; // 没有重叠，写入一段新的区间
                    res[count][1] = current[1];
                    count++;
                } else {
                    int lastEnd = res[count - 1][1];
                    if (current[1] > lastEnd) { // 有重叠，只需要延长最后一段的右端
                        res[count - 1][1] = current[1];
                    }
                }
            }

            res = Arrays.copyOf(res, count); // 只保留已经写入的count段，去掉未使用的位置
            return res;
        }
    }

    // 保留此前版本，便于对照；main和Hot100调试器使用上方的清晰写法。
    static class SolutionOriginal {
        public int[][] merge(int[][] intervals) {
            intervals = intervals.clone();
            Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
            List<int[]> result = new ArrayList<>();
            for (int[] current : intervals) {
                if (result.isEmpty() || current[0] > result.get(result.size() - 1)[1]) {
                    result.add(current.clone());
                } else {
                    int[] last = result.get(result.size() - 1);
                    last[1] = Math.max(last[1], current[1]);
                }
            }
            return result.toArray(new int[result.size()][]);
        }
    }
}
