package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.36。
public class LC0669TrimABinarySearchTree {

    public static void main(String[] args) {
        // 测试来源：本地设计的代表性用例；原文未提供该题独立输入。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(3, 1, 4, null, 2);
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(3, 2, 4));
        Solution solution = new Solution();
        var answer = solution.trimBST(root, 2, 4);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("Solution", "root = [3, 1, 4, null, 2], low = 2, high = 4", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(3, 1, 4, null, 2);
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(3, 2, 4));
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.trimBST(root, 2, 4);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("SolutionIterative", "root = [3, 1, 4, null, 2], low = 2, high = 4", expected, actual);
    }


    static class Solution {
        // ① 返回保留 [low, high] 后的子树根，根可能被替换。
        public TreeNode trimBST(TreeNode root, int low, int high) {
            // ② 空树无需修剪，返回 null。
            if (root == null) {
                return null;
            }
            // ③ 利用 BST 舍弃整侧；越界时返回仍可能合法的一侧，合法时接回两侧。
            if (root.val < low) {
                // 当前和左侧都太小；右侧可能合法，要递归保留，不能直接返回 null。
                return trimBST(root.right, low, high);
            }
            if (root.val > high) {
                // 当前和右侧都太大；合法节点只可能留在左侧。
                return trimBST(root.left, low, high);
            }
            // 当前合法，接回修剪后的孩子；孩子的根可能已换掉。
            root.left = trimBST(root.left, low, high);
            root.right = trimBST(root.right, low, high);
            return root;
        }
    }

    static class SolutionIterative {
        public TreeNode trimBST(TreeNode root, int low, int high) {
            // 先沿可能合法的一侧移动，找到能保留的新根。
            while (root != null && (root.val < low || root.val > high)) {
                if (root.val < low) {
                    root = root.right;
                } else {
                    root = root.left;
                }
            }
            if (root == null) {
                return null;
            }
            TreeNode cur = root;
            while (cur != null) {
                while (cur.left != null && cur.left.val < low) {
                    // 左孩子太小，它和其左侧都丢弃，但右侧可能合法，要接上。
                    cur.left = cur.left.right;
                }
                cur = cur.left;
            }
            cur = root;
            while (cur != null) {
                while (cur.right != null && cur.right.val > high) {
                    // 右孩子太大，对称地保留它仍可能合法的左侧。
                    cur.right = cur.right.left;
                }
                cur = cur.right;
            }
            return root;
        }
    }
}
