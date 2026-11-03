package leetcode.array;

import java.util.*;

public class LC0048RotateImage {
    public static void main(String[] args) {
        // 测试来源：Hot100笔记中相同的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf
        int[][] matrix = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
        new Solution().rotate(matrix);
        Object actual = matrix;
        String expected = "[[7,4,1],[8,5,2],[9,6,3]]";
        String result = canonical(actual);
        System.out.println("Input: " + "matrix=[[1,2,3],[4,5,6],[7,8,9]]");
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
        public void rotate(int[][] matrix) {
            // 结果直接写回matrix：先转置，再左右反转每一行
            int n = matrix.length;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) { // 每对对称位置只交换一次
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = temp;
                }
            }
            // 转置完成后，逐行交换左右两端
            for (int row = 0; row < n; row++) {
                int left = 0;
                int right = n - 1;
                while (left < right) {
                    int temp = matrix[row][left];
                    matrix[row][left] = matrix[row][right];
                    matrix[row][right] = temp;
                    left++;
                    right--;
                }
            }
        }
    }

    // 保留此前版本，便于对照；main和Hot100调试器使用上方的清晰写法。
    static class SolutionOriginal {
        public void rotate(int[][] matrix) {
            int n = matrix.length;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) { // 每对对称位置只交换一次
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = temp;
                }
            }
            for (int row = 0; row < n; row++) {
                int left = 0;
                int right = n - 1;
                while (left < right) {
                    int temp = matrix[row][left];
                    matrix[row][left] = matrix[row][right];
                    matrix[row][right] = temp;
                    left++;
                    right--;
                }
            }
        }
    }
}
