package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.20。
public class LC0404SumOfLeftLeaves {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = 6;
        Solution solution = new Solution();
        var answer = solution.sumOfLeftLeaves(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = 6;
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.sumOfLeftLeaves(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }


    static class Solution {
        // ① 返回当前子树内部所有左叶子的和，不把当前根自动算进去。
        public int sumOfLeftLeaves(TreeNode root) {
            // ② 空树贡献为 0。
            if (root == null) {
                return 0;
            }
            // ③ 从父节点认出左叶子；不是叶子就继续找，再加右树内部贡献。
            int leftSum;
            if (isLeaf(root.left)) {
                leftSum = root.left.val;
            } else {
                leftSum = sumOfLeftLeaves(root.left);
            }
            // 右孩子本身不计入，但右子树内部仍可能有左叶子。
            return leftSum + sumOfLeftLeaves(root.right);
        }
    
        private boolean isLeaf(TreeNode node) {
            return node != null && node.left == null && node.right == null;
        }
    }

    static class SolutionIterative {
        private boolean isLeaf(TreeNode node) {
            return node != null && node.left == null && node.right == null;
        }
    
        public int sumOfLeftLeaves(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int sum = 0;
            Deque<TreeNode> stack = new ArrayDeque<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                TreeNode node = stack.pop();
                // 从父节点检查左孩子，必须同时满足“在左边”和“是叶子”。
                if (isLeaf(node.left)) {
                    sum += node.left.val;
                }
                if (node.right != null) {
                    stack.push(node.right);
                }
                if (node.left != null) {
                    stack.push(node.left);
                }
            }
            return sum;
        }
    }
}
