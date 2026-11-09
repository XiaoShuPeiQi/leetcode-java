package leetcode.binarytree;

import java.util.*;
import common.*;
import static common.ListSupport.*;

public class LC0543DiameterOfBinaryTree {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "root=[1,2,3,4,5]",
                "3", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(1, 2, 3, 4, 5);
        return new Solution().diameterOfBinaryTree(root);
    }

    static class Solution {
        private int diameter;

        public int diameterOfBinaryTree(TreeNode root) {
            diameter = 0;
            height(root);
            return diameter;
        }

        // 返回当前子树的高度，同时检查经过当前节点的路径。
        private int height(TreeNode node) {
            if (node == null) {
                return 0;
            }
            int left = height(node.left);
            int right = height(node.right);
            diameter = Math.max(diameter, left + right);
            return Math.max(left, right) + 1;
        }
    }
}
