package leetcode.binarytree;

import java.util.*;
import common.TreeSupport;
import common.TreeNode;


public class LC0124BinaryTreeMaximumPathSum {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "root=[-10,9,20,null,null,15,7]",
                "42", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(-10,9,20,null,null,15,7);
        return new Solution().maxPathSum(root);
    }

    static class Solution {
        private int best;

        public int maxPathSum(TreeNode root) {
            best = root.val;
            gain(root);
            return best;
        }

        private int gain(TreeNode node) {
            if (node == null) {
                return 0;
            }
            int left = Math.max(0, gain(node.left));
            int right = Math.max(0, gain(node.right));
            best = Math.max(best, node.val + left + right);
            // 交给父节点的路径只能接其中一边。
            return node.val + Math.max(left, right);
        }
    }
}
