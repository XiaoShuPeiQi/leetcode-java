package leetcode.backtracking;

import java.util.*;
import common.TreeSupport;


public class LC0079WordSearch {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "board=[\"ABCE\",\"SFCS\",\"ADEE\"], word=\"ABCCED\"",
                "true", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        char[][] board = {"ABCE".toCharArray(), "SFCS".toCharArray(), "ADEE".toCharArray()};
        String word = "ABCCED";
        return new Solution().exist(board, word);
    }

    static class Solution {
        private int rows;
        private int cols;

        public boolean exist(char[][] board, String word) {
            rows = board.length;
            cols = board[0].length;
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (backtracking(board, word, row, col, 0)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean backtracking(char[][] board, String word, int row, int col, int index) {
            if (index == word.length()) {
                return true;
            }
            if (row < 0 || row >= rows || col < 0 || col >= cols
                    || board[row][col] == '#' || board[row][col] != word.charAt(index)) {
                return false;
            }

            char saved = board[row][col];
            board[row][col] = '#';
            boolean found = backtracking(board, word, row + 1, col, index + 1)
                    || backtracking(board, word, row - 1, col, index + 1)
                    || backtracking(board, word, row, col + 1, index + 1)
                    || backtracking(board, word, row, col - 1, index + 1);
            board[row][col] = saved;
            return found;
        }
    }
}
