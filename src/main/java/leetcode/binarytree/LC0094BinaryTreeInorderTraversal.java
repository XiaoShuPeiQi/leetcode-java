package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.2。
public class LC0094BinaryTreeInorderTraversal {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of(1, 2, 3, 4, 5, 6, 7);
        Solution solution = new Solution();
        var answer = solution.inorderTraversal(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of(1, 2, 3, 4, 5, 6, 7);
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.inorderTraversal(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }


    static class Solution {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            traversal(root, result);
            return result;
        }
    
        // ① 把当前子树的中序结果追加到 result，无需返回值。
        private void traversal(TreeNode node, List<Integer> result) {
            // ② 空节点没有内容，直接返回。
            if (node == null) {
                return;
            }
            // ③ 中序要等左边完成，才记录自己，再处理右边。
            traversal(node.left, result);
            result.add(node.val);
            traversal(node.right, result);
        }
    }

    static class SolutionIterative {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            Deque<TreeNode> stack = new ArrayDeque<>();
            TreeNode cur = root;
            while (cur != null || !stack.isEmpty()) {
                // 沿左边走到底，栈记住稍后要回来的父节点。
                while (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                }
                // 左子树完成了，现在记录当前节点，再转右边。
                cur = stack.pop();
                result.add(cur.val);
                cur = cur.right;
            }
            return result;
        }
    }
}
