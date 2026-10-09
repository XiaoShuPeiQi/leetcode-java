package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.33。
public class LC0235LowestCommonAncestorOfABinarySearchTree {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        TreeNode p = root.left.left;
        TreeNode q = root.left.right;
        Object expected = TreeSupport.toLevelOrder(root.left);
        Solution solution = new Solution();
        var answer = solution.lowestCommonAncestor(root, p, q);
        if (answer != root.left) throw new AssertionError("必须返回原树中的祖先节点");
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7], p = 节点 1, q = 节点 3", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        TreeNode p = root.left.left;
        TreeNode q = root.left.right;
        Object expected = TreeSupport.toLevelOrder(root.left);
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.lowestCommonAncestor(root, p, q);
        if (answer != root.left) throw new AssertionError("必须返回原树中的祖先节点");
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7], p = 节点 1, q = 节点 3", expected, actual);
    }


    static class Solution {
        // ① 返回当前子树中 p、q 的最近公共祖先，题目保证目标存在。
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            // ② 空树没有可返回的祖先。
            if (root == null) {
                return null;
            }
            // ③ 两个目标同侧就继续走；分居两侧或命中当前时，当前就是祖先。
            if (p.val < root.val && q.val < root.val) {
                return lowestCommonAncestor(root.left, p, q);
            }
            if (p.val > root.val && q.val > root.val) {
                return lowestCommonAncestor(root.right, p, q);
            }
            return root;
        }
    }

    static class SolutionIterative {
        public TreeNode lowestCommonAncestor(TreeNode root,
                TreeNode p, TreeNode q) {
            TreeNode cur = root;
            while (cur != null) {
                // 两目标在同侧就继续走；第一次分叉或命中就是公共祖先。
                if (p.val < cur.val && q.val < cur.val) {
                    cur = cur.left;
                } else if (p.val > cur.val && q.val > cur.val) {
                    cur = cur.right;
                } else {
                    return cur;
                }
            }
            return null;
        }
    }
}
