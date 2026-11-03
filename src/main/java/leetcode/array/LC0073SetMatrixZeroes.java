package leetcode.array;

import java.util.*;

public class LC0073SetMatrixZeroes {
    public static void main(String[] args) {
        // 测试来源：Hot100笔记中相同的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf
        int[][] matrix = new int[][]{{1,1,1},{1,0,1},{1,1,1}};
        new Solution().setZeroes(matrix);
        Object actual = matrix;
        String expected = "[[1,0,1],[0,0,0],[1,0,1]]";
        String result = canonical(actual);
        System.out.println("Input: " + "matrix=[[1,1,1],[1,0,1],[1,1,1]]");
        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(result);
        if (!expected.equals(result)) {
            throw new AssertionError("Result: FAIL");
        }
        System.out.println("Result: PASS");
    }


    private static String canonical(Object value) {
        if (value instanceof int[]) {
            return Arrays.toString((int[]) value).replace(" ", "");
        }
        if (value instanceof int[][]) {
            return Arrays.deepToString((int[][]) value).replace(" ", "");
        }
        if (value instanceof List<?>) {
            List<String> parts = new ArrayList<>();
            for (Object item : (List<?>) value) {
                parts.add(canonical(item));
            }

            return "[" + String.join(",", parts) + "]";
        }
        if (value instanceof String) {
            return "\"" + value + "\"";
        }
        return String.valueOf(value);
    }


    static class Solution {
        public void setZeroes(int[][] matrix) {
            // 结果直接写回matrix，先标记原有的0，再统一清零
            int m = matrix.length;
            int n = matrix[0].length;
            boolean firstRow = false;
            boolean firstCol = false;
            for (int j = 0; j < n; j++) {
                if (matrix[0][j] == 0) {
                    firstRow = true;
                }
            }
            for (int i = 0; i < m; i++) {
                if (matrix[i][0] == 0) {
                    firstCol = true;
                }
            }
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    if (matrix[i][j] == 0) { // 标记阶段只写第一行、第一列
                        matrix[i][0] = 0;
                        matrix[0][j] = 0;
                    }
                }
            }
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                        matrix[i][j] = 0;
                    }
                }
            }
            if (firstRow) {
                for (int j = 0; j < n; j++) {
                    matrix[0][j] = 0;
                }
            }
            if (firstCol) {
                for (int i = 0; i < m; i++) {
                    matrix[i][0] = 0;
                }
            }
        }
    }

    // 保留此前版本，便于对照；main和Hot100调试器使用上方的清晰写法。
    static class SolutionOriginal {
        public void setZeroes(int[][] matrix) {
            int m = matrix.length;
            int n = matrix[0].length;
            boolean firstRow = false;
            boolean firstCol = false;
            for (int j = 0; j < n; j++) {
                if (matrix[0][j] == 0) {
                    firstRow = true;
                }
            }
            for (int i = 0; i < m; i++) {
                if (matrix[i][0] == 0) {
                    firstCol = true;
                }
            }
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    if (matrix[i][j] == 0) { // 标记阶段只写第一行、第一列
                        matrix[i][0] = 0;
                        matrix[0][j] = 0;
                    }
                }
            }
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                        matrix[i][j] = 0;
                    }
                }
            }
            if (firstRow) {
                for (int j = 0; j < n; j++) {
                    matrix[0][j] = 0;
                }
            }
            if (firstCol) {
                for (int i = 0; i < m; i++) {
                    matrix[i][0] = 0;
                }
            }
        }
    }
}
