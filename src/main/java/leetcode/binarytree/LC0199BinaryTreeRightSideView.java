package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.6。
public class LC0199BinaryTreeRightSideView {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of(4, 6, 7);
        Solution solution = new Solution();
        var answer = solution.rightSideView(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of(4, 6, 7);
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.rightSideView(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }


    static class Solution {
        public List<Integer> rightSideView(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            traversal(root, 0, result);
            return result;
        }
    
        // ① depth 标记层；result 每层只保留最右边一个值。
        private void traversal(TreeNode node, int depth, List<Integer> result) {
            // ② 空节点看不到，直接返回。
            if (node == null) {
                return;
            }
            // ③ 先右后左，所以新深度的第一个节点就是最右节点。
            if (depth == result.size()) {
                result.add(node.val);
            }
            // 右侧优先占位；右侧缺失时，左子树仍能补上更深层。
            traversal(node.right, depth + 1, result);
            traversal(node.left, depth + 1, result);
        }
    }

    static class SolutionIterative {
        public List<Integer> rightSideView(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            if (root == null) {
                return result;
            }
            Queue<TreeNode> queue = new ArrayDeque<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                // 先固定本层数量；本轮新入队的孩子留到下一层处理。
                int levelSize = queue.size();
                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    // 本层从左到右出队，最后一个就是右视图。
                    if (i == levelSize - 1) {
                        result.add(node.val);
                    }
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }
            }
            return result;
        }
    }
}
