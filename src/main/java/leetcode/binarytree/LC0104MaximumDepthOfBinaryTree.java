package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.14。
public class LC0104MaximumDepthOfBinaryTree {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionDepth();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = 3;
        Solution solution = new Solution();
        var answer = solution.maxDepth(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }

    private static void runSolutionDepth() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = 3;
        SolutionDepth solution = new SolutionDepth();
        var answer = solution.maxDepth(root);
        Object actual = answer;
        TreeSupport.check("SolutionDepth", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = 3;
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.maxDepth(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }


    static class Solution {
        // ① 返回当前子树的高度，不是当前节点距离根的深度。
        public int maxDepth(TreeNode root) {
            // ② 空树有 0 层；这样叶子才能由 max(0, 0) + 1 得到 1。
            if (root == null) {
                return 0;
            }
            // ③ 父高度依赖孩子：先拿左右高度，再取较大值加自己一层。
            int leftHeight = maxDepth(root.left);
            int rightHeight = maxDepth(root.right);
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    static class SolutionDepth {
        private int maximum;
    
        public int maxDepth(TreeNode root) {
            // 每次调用重新统计；根从第 1 层开始。
            maximum = 0;
            traversal(root, 1);
            return maximum;
        }
    
        // ① depth 是从根走到当前节点的层数，maximum 保存全树最大值。
        private void traversal(TreeNode node, int depth) {
            // ② 空位置不算一层，直接返回。
            if (node == null) {
                return;
            }
            // ③ 先用当前深度更新最大值，再给孩子传 depth + 1。
            maximum = Math.max(maximum, depth);
            traversal(node.left, depth + 1);
            traversal(node.right, depth + 1);
        }
    }

    static class SolutionIterative {
        public int maxDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            Queue<TreeNode> queue = new ArrayDeque<>();
            queue.offer(root);
            int depth = 0;
            while (!queue.isEmpty()) {
                // 先固定本层数量；本轮新入队的孩子留到下一层处理。
                int levelSize = queue.size();
                // 每处理一轮就是一层；层数即最大深度。
                depth++;
                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }
            }
            return depth;
        }
    }
}
