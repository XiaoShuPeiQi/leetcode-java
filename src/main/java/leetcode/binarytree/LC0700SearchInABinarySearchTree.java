package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.28。
public class LC0700SearchInABinarySearchTree {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(2, 1, 3));
        Solution solution = new Solution();
        var answer = solution.searchBST(root, 2);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7], val = 2", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(2, 1, 3));
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.searchBST(root, 2);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7], val = 2", expected, actual);
    }


    static class Solution {
        // ① 返回目标节点本身；没找到返回 null。
        public TreeNode searchBST(TreeNode root, int val) {
            // ② 空树表示没找到，值相等则已经命中，都无需继续。
            if (root == null || root.val == val) {
                return root;
            }
            // ③ BST 排除另一侧，只递归可能含目标的一边，并把结果返回。
            if (val < root.val) {
                return searchBST(root.left, val);
            }
            return searchBST(root.right, val);
        }
    }

    static class SolutionIterative {
        public TreeNode searchBST(TreeNode root, int val) {
            TreeNode cur = root;
            while (cur != null) {
                if (cur.val == val) {
                    return cur;
                }
                // 比较大小后只走可能含目标的一侧，不必遍历另一边。
                if (val < cur.val) {
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }
            }
            return null;
        }
    }
}
