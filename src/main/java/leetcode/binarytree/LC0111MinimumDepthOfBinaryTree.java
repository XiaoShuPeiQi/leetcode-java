package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.16。
public class LC0111MinimumDepthOfBinaryTree {

    public static void main(String[] args) {
        // 测试来源：飞书原文 4.4 的最小反例。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(1, null, 2);
        Object expected = 2;
        Solution solution = new Solution();
        var answer = solution.minDepth(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [1, null, 2]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(1, null, 2);
        Object expected = 2;
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.minDepth(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [1, null, 2]", expected, actual);
    }


    static class Solution {
        // ① 返回从当前根到最近叶子的节点数。
        public int minDepth(TreeNode root) {
            // ② 空树为 0，但空孩子不能当作可走的叶子路径。
            if (root == null) {
                return 0;
            }
            // ③ 单孩子只能走存在的一边；两边都在才取 min，最后加自己。
            if (root.left == null) {
                return minDepth(root.right) + 1;
            }
            if (root.right == null) {
                return minDepth(root.left) + 1;
            }
            int leftDepth = minDepth(root.left);
            int rightDepth = minDepth(root.right);
            return Math.min(leftDepth, rightDepth) + 1;
        }
    }

    static class SolutionIterative {
        public int minDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            Queue<TreeNode> queue = new ArrayDeque<>();
            queue.offer(root);
            int depth = 1;
            while (!queue.isEmpty()) {
                // 先固定本层数量；本轮新入队的孩子留到下一层处理。
                int levelSize = queue.size();
                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    // BFS 从浅到深；第一个叶子就给出最短根到叶路径。
                    if (node.left == null && node.right == null) {
                        return depth;
                    }
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }
                depth++;
            }
            return depth;
        }
    }
}
