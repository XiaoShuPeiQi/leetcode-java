package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.18。
public class LC0110BalancedBinaryTree {

    public static void main(String[] args) {
        // 测试来源：本地设计的代表性用例；原文未提供该题独立输入。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(1, 2, 3, 4, null, null, null, 5);
        Object expected = false;
        Solution solution = new Solution();
        var answer = solution.isBalanced(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [1, 2, 3, 4, null, null, null, 5]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(1, 2, 3, 4, null, null, null, 5);
        Object expected = false;
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.isBalanced(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [1, 2, 3, 4, null, null, null, 5]", expected, actual);
    }


    static class Solution {
        public boolean isBalanced(TreeNode root) {
            return height(root) != -1;
        }
    
        // ① 平衡时返回高度，不平衡时返回 -1；一次递归同时回答两件事。
        private int height(TreeNode node) {
            // ② 空树高度为 0，并且平衡。
            if (node == null) {
                return 0;
            }
            // ③ 先拿孩子结果；发现失衡就向上传 -1，否则合成当前高度。
            int leftHeight = height(node.left);
            // 孩子已失衡，父节点也必然失衡，无需再算另一边。
            if (leftHeight == -1) {
                return -1;
            }
            int rightHeight = height(node.right);
            if (rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1) {
                return -1;
            }
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    static class SolutionIterative {
        public boolean isBalanced(TreeNode root) {
            // 递归用返回值传高度；这里用 Map 存已完成子树的高度。
            Map<TreeNode, Integer> heights = new HashMap<>();
            Deque<TreeNode> stack = new ArrayDeque<>();
            TreeNode cur = root;
            TreeNode lastVisited = null;
            while (cur != null || !stack.isEmpty()) {
                if (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                } else {
                    TreeNode node = stack.peek();
                    // 右树未完成先处理右树；两边完成后才合成当前高度。
                    if (node.right != null && node.right != lastVisited) {
                        cur = node.right;
                    } else {
                        // 此时两个孩子已处理完，空孩子默认高度 0。
                        int leftHeight = heights.getOrDefault(node.left, 0);
                        int rightHeight = heights.getOrDefault(node.right, 0);
                        if (Math.abs(leftHeight - rightHeight) > 1) {
                            return false;
                        }
                        heights.put(node, Math.max(leftHeight, rightHeight) + 1);
                        lastVisited = stack.pop();
                    }
                }
            }
            return true;
        }
    }
}
