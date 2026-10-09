package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.17。
public class LC0222CountCompleteTreeNodes {

    public static void main(String[] args) {
        // 测试来源：本地设计的代表性用例；原文未提供该题独立输入。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(1, 2, 3, 4, 5, 6);
        Object expected = 6;
        Solution solution = new Solution();
        var answer = solution.countNodes(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [1, 2, 3, 4, 5, 6]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(1, 2, 3, 4, 5, 6);
        Object expected = 6;
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.countNodes(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [1, 2, 3, 4, 5, 6]", expected, actual);
    }


    static class Solution {
        // ① 返回当前完全子树的节点总数。
        public int countNodes(TreeNode root) {
            // ② 空树没有节点，返回 0。
            if (root == null) {
                return 0;
            }
            // ③ 先看是否整棵满；能用公式就直接算，否则递归左右再加自己。
            int leftHeight = leftHeight(root);
            int rightHeight = 0;
            TreeNode cur = root;
            while (cur != null) {
                rightHeight++;
                cur = cur.right;
            }
            if (leftHeight == rightHeight) {
                // 完全树的两端同高，才能判定整棵满；h 层共 2^h - 1 个节点。
                return (int) ((1L << leftHeight) - 1);
            }
            return countNodes(root.left) + countNodes(root.right) + 1;
        }
    
        private int leftHeight(TreeNode node) {
            int height = 0;
            while (node != null) {
                height++;
                node = node.left;
            }
            return height;
        }
    }

    static class SolutionIterative {
        private int leftHeight(TreeNode node) {
            int height = 0;
            while (node != null) {
                height++;
                node = node.left;
            }
            return height;
        }
    
        public int countNodes(TreeNode root) {
            long count = 0;
            TreeNode cur = root;
            while (cur != null) {
                // 比较的是左右子树的左链高度，用完全树性质确定哪侧满。
                int leftHeight = leftHeight(cur.left);
                int rightHeight = leftHeight(cur.right);
                if (leftHeight == rightHeight) {
                    // 左子树满：左树节点数 + 当前根 = 2^leftHeight。
                    count += 1L << leftHeight;
                    cur = cur.right;
                } else {
                    // 右子树满且矮一层，数掉它和当前根。
                    count += 1L << rightHeight;
                    cur = cur.left;
                }
            }
            return (int) count;
        }
    }
}
