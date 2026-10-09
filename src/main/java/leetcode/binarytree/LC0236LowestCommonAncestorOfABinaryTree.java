package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.32。
public class LC0236LowestCommonAncestorOfABinaryTree {

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
        // ① 返回本子树发现的目标，或已经确定的公共祖先；未发现返回 null。
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            // ② 空返回 null；遇到 p/q 返回自己，因为目标也可以是祖先。
            if (root == null || root == p || root == q) {
                return root;
            }
            // ③ 先让左右报告；两边都有则在当前汇合，一边有则继续向上传。
            TreeNode left = lowestCommonAncestor(root.left, p, q);
            TreeNode right = lowestCommonAncestor(root.right, p, q);
            // 左右各有目标（或已确定结果），它们在当前节点汇合。
            if (left != null && right != null) {
                return root;
            }
            if (left != null) {
                return left;
            }
            return right;
        }
    }

    static class SolutionIterative {
        public TreeNode lowestCommonAncestor(TreeNode root,
                TreeNode p, TreeNode q) {
            if (root == null) {
                return null;
            }
            // 先记录父节点，后面才能从目标向上走。
            Map<TreeNode, TreeNode> parents = new HashMap<>();
            Deque<TreeNode> stack = new ArrayDeque<>();
            parents.put(root, null);
            stack.push(root);
            while (!stack.isEmpty() && (!parents.containsKey(p) || !parents.containsKey(q))) {
                TreeNode node = stack.pop();
                if (node.left != null) {
                    parents.put(node.left, node);
                    stack.push(node.left);
                }
                if (node.right != null) {
                    parents.put(node.right, node);
                    stack.push(node.right);
                }
            }
            if (!parents.containsKey(p) || !parents.containsKey(q)) {
                return null;
            }
            // 保存 p 的整条祖先链，再让 q 向上找第一个交点。
            Set<TreeNode> ancestors = new HashSet<>();
            TreeNode cur = p;
            while (cur != null) {
                ancestors.add(cur);
                cur = parents.get(cur);
            }
            cur = q;
            // q 从下往上，第一个交点就是最近公共祖先。
            while (!ancestors.contains(cur)) {
                cur = parents.get(cur);
            }
            return cur;
        }
    }
}
