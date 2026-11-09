package leetcode.array;

import java.util.*;
import common.*;
import static common.ListSupport.*;

public class LC0240SearchA2dMatrixIi {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "matrix=[[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target=5",
                "true", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        int[][] matrix = {{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}};
        return new Solution().searchMatrix(matrix, 5);
    }

    static class Solution {
        public boolean searchMatrix(int[][] matrix, int target) {
            int row = 0;
            int col = matrix[0].length - 1;
            while (row < matrix.length && col >= 0) {
                int value = matrix[row][col];
                if (value == target) {
                    return true;
                }
                if (value > target) {
                    col--; // 当前列剩下的数都更大
                } else {
                    row++; // 当前行剩下的数都更小
                }
            }
            return false;
        }
    }
}
