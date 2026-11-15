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
        return new Solution().exist(board, "ABCCED");
    }

    static class Solution {
        public boolean exist(char[][] board, String word) {
            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            boolean[][] used = new boolean[board.length][board[0].length];
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[0].length; col++) {
                    if (backtracking(board, word, row, col, 0, used, directions)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean backtracking(char[][] board, String word, int row, int col,
                int index, boolean[][] used, int[][] directions) {
            if (used[row][col] || board[row][col] != word.charAt(index)) {
                return false;
            }
            if (index == word.length() - 1) {
                return true;
            }
            used[row][col] = true;
            boolean found = false;
            for (int[] direction : directions) {
                int nextRow = row + direction[0];
                int nextCol = col + direction[1];
                if (nextRow < 0 || nextRow >= board.length
                        || nextCol < 0 || nextCol >= board[0].length) {
                    continue;
                }
                if (backtracking(board, word, nextRow, nextCol, index + 1, used, directions)) {
                    found = true;
                    break;
                }
            }
            used[row][col] = false;
            return found;
        }
    }
}
