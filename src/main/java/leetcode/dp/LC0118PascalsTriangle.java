package leetcode.dp;

import common.TreeSupport;
import java.util.ArrayList;
import java.util.List;

public class LC0118PascalsTriangle {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "numRows = 5",
                List.of(List.of(1), List.of(1, 1), List.of(1, 2, 1), List.of(1, 3, 3, 1), List.of(1, 4, 6, 4, 1)), runSolution());
    }

    // 力扣官方示例，与本节推演一致。
    static Object runSolution() {
        int numRows = 5;
        return new Solution().generate(numRows);
    }

    static class Solution {
        public List<List<Integer>> generate(int numRows) {
            List<List<Integer>> result = new ArrayList<>();
            for (int i = 0; i < numRows; i++) {
                List<Integer> row = new ArrayList<>();
                for (int j = 0; j <= i; j++) {
                    if (j == 0 || j == i) {
                        row.add(1);
                    } else {
                        row.add(result.get(i - 1).get(j - 1) + result.get(i - 1).get(j));
                    }
                }
                result.add(row);
            }
            return result;
        }
    }
}
