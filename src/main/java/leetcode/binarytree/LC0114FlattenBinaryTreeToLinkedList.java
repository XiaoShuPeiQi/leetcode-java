package leetcode.binarytree;

import java.util.*;
import common.TreeSupport;
import common.TreeNode;


public class LC0114FlattenBinaryTreeToLinkedList {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "root=[1,2,5,3,4,null,6]",
                "[1, 2, 3, 4, 5, 6]", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(1,2,5,3,4,null,6);
        new Solution().flatten(root);
        List<Integer> values = new ArrayList<>();
        for (TreeNode node = root; node != null; node = node.right) {
            if (node.left != null || values.size() >= 6) {
                throw new AssertionError("展开后必须是单条右链");
            }
            values.add(node.val);
        }
        return values;
    }

    static class Solution {
        private TreeNode next;

        public void flatten(TreeNode root) {
            next = null;
            traversal(root);
        }

        private void traversal(TreeNode node) {
            if (node == null) {
                return;
            }
            // 前序是根、左、右。倒着处理，才能先把后缀接好。
            traversal(node.right);
            traversal(node.left);
            node.right = next;
            node.left = null;
            next = node;
        }
    }
}
