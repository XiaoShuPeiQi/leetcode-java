package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.3。
public class LC0145BinaryTreePostorderTraversal {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of(1, 3, 2, 5, 7, 6, 4);
        Solution solution = new Solution();
        var answer = solution.postorderTraversal(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of(1, 3, 2, 5, 7, 6, 4);
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.postorderTraversal(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }


    static class Solution {
        public List<Integer> postorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            traversal(root, result);
            return result;
        }
    
        // ① 把当前子树的后序结果追加到 result，无需返回值。
        private void traversal(TreeNode node, List<Integer> result) {
            // ② 空节点没有内容，直接返回。
            if (node == null) {
                return;
            }
            // ③ 后序先完成两个孩子，最后记录自己。
            traversal(node.left, result);
            traversal(node.right, result);
            result.add(node.val);
        }
    }

    static class SolutionIterative {
        public List<Integer> postorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            Deque<TreeNode> stack = new ArrayDeque<>();
            TreeNode cur = root;
            // 记住刚完成的节点，判断右子树是否已经处理过。
            TreeNode lastVisited = null;
            while (cur != null || !stack.isEmpty()) {
                if (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                } else {
                    // 先看栈顶，不能马上弹出：它还可能要等右子树。
                    TreeNode node = stack.peek();
                    // 右子树尚未完成就先走右边；否则才处理根。
                    if (node.right != null && node.right != lastVisited) {
                        cur = node.right;
                    } else {
                        result.add(node.val);
                        lastVisited = stack.pop();
                    }
                }
            }
            return result;
        }
    }
}
