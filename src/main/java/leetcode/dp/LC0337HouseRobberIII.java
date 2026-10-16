package leetcode.dp;

import java.util.*;
import common.TreeNode;
import common.TreeSupport;

public class LC0337HouseRobberIII {

    public static void main(String[] args) {
        // 测试来源：动态规划笔记中的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe
        TreeNode root = TreeSupport.fromLevelOrder(3,2,3,null,3,null,1);
        int expected = 7;
        int actual = runSolution(root);
        System.out.println("Input:\n" + "root = [3, 2, 3, null, 3, null, 1]");
        System.out.println("Expected:\n" + expected);
        System.out.println("Actual:\n" + actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    private static int runSolution(TreeNode root) {
        return new Solution().rob(root);
    }

    static class Solution {
        public int rob(TreeNode root) {
            int[] result = traverse(root);
            return Math.max(result[0], result[1]);
        }

        private int[] traverse(TreeNode node) {
            if (node == null) return new int[]{0, 0};
            int[] left = traverse(node.left);
            int[] right = traverse(node.right);
            int skip = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
            int take = node.val + left[0] + right[0]; // 偷父节点，孩子必须不偷
            return new int[]{skip, take};
        }
    }
}
