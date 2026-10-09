package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.1。
public class LC0144BinaryTreePreorderTraversal {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of(4, 2, 1, 3, 6, 5, 7);
        Solution solution = new Solution();
        var answer = solution.preorderTraversal(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of(4, 2, 1, 3, 6, 5, 7);
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.preorderTraversal(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }


    static class Solution {
        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            traversal(root, result);
            return result;
        }
    
        // ① node 是当前子树；result 收集答案，不需要返回子树结果。
        private void traversal(TreeNode node, List<Integer> result) {
            // ② 空节点没有值可记录，结束这一支。
            if (node == null) {
                return;
            }
            // ③ 前序先记自己，再让左右子树做同样的事。
            result.add(node.val);
            traversal(node.left, result);
            traversal(node.right, result);
        }
    }

    static class SolutionIterative {
        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            if (root == null) {
                return result;
            }
            Deque<TreeNode> stack = new ArrayDeque<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                TreeNode node = stack.pop();
                result.add(node.val);
                // 栈后进先出：先压右、再压左，弹出时才是左→右。
                if (node.right != null) {
                    stack.push(node.right);
                }
                if (node.left != null) {
                    stack.push(node.left);
                }
            }
            return result;
        }
    }
}
