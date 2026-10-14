package leetcode.greedy;

import java.util.*;
import common.TreeNode;
import common.TreeSupport;

public class LC0968BinaryTreeCameras {

    public static void main(String[] args) {
        // 测试来源：飞书贪心笔记的实例走过程，2026-10-14读取。
        // https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf
        TreeNode root = TreeSupport.fromLevelOrder(0, 0, null, 0, 0);
        int expected = 1;
        int actual = runSolution(root);
        System.out.println("Input:");
        System.out.println("root = [0, 0, null, 0, 0]");
        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        boolean passed = actual == expected;
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) throw new AssertionError("结果与预期不同");
    }

    private static int runSolution(TreeNode root) {
        return new Solution().minCameraCover(root);
    }

    // 保留飞书当前Solution类体，只增加static和一层缩进。
    static class Solution {
        private static final int UNCOVERED = 0;
        private static final int CAMERA = 1;
        private static final int COVERED = 2;
        private int cameras;

        public int minCameraCover(TreeNode root) {
            cameras = 0;
            if (traverse(root) == UNCOVERED) cameras++; // 根没有父节点兜底
            return cameras;
        }

        private int traverse(TreeNode node) {
            if (node == null) return COVERED;
            int left = traverse(node.left);
            int right = traverse(node.right);
            if (left == UNCOVERED || right == UNCOVERED) {
                cameras++;
                return CAMERA;
            }
            if (left == CAMERA || right == CAMERA) return COVERED;
            return UNCOVERED;
        }
    }
}
