package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.9。
public class LC0515FindLargestValueInEachTreeRow {

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
        var answer = solution.largestValues(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of(4, 6, 7);
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.largestValues(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }


    static class Solution {
        public List<Integer> largestValues(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            traversal(root, 0, result);
            return result;
        }
    
        // ① depth 标记层；result[depth] 保存这层目前的最大值。
        private void traversal(TreeNode node, int depth, List<Integer> result) {
            // ② 空节点不参与最大值比较。
            if (node == null) {
                return;
            }
            // ③ 新层用真实节点值初始化，避免全负数时误取 0。
            if (depth == result.size()) {
                result.add(node.val);
            } else {
                result.set(depth, Math.max(result.get(depth), node.val));
            }
            traversal(node.left, depth + 1, result);
            traversal(node.right, depth + 1, result);
        }
    }

    static class SolutionIterative {
        public List<Integer> largestValues(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            if (root == null) {
                return result;
            }
            Queue<TreeNode> queue = new ArrayDeque<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                // 先固定本层数量；本轮新入队的孩子留到下一层处理。
                int levelSize = queue.size();
                // 一层可能全是负数，不能用 0 当初始最大值。
                int maximum = Integer.MIN_VALUE;
                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    maximum = Math.max(maximum, node.val);
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }
                result.add(maximum);
            }
            return result;
        }
    }
}
