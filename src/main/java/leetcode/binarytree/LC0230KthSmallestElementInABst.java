package leetcode.binarytree;

import java.util.*;
import common.TreeSupport;
import common.TreeNode;


public class LC0230KthSmallestElementInABst {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "root=[5,3,6,2,4,null,null,1], k=3",
                "3", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(5,3,6,2,4,null,null,1);
        return new Solution().kthSmallest(root, 3);
    }

    static class Solution {
        private int visited;
        private int result;

        public int kthSmallest(TreeNode root, int k) {
            visited = 0;
            result = 0;
            traversal(root, k);
            return result;
        }

        private void traversal(TreeNode node, int k) {
            if (node == null || visited == k) {
                return;
            }
            traversal(node.left, k);
            if (visited == k) {
                return; // 左子树已经找到第 k 个
            }
            visited++;
            if (visited == k) {
                result = node.val;
                return;
            }
            traversal(node.right, k);
        }
    }
}
