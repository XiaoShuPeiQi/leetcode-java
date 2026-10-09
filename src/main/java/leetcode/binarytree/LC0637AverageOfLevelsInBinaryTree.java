package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.7。
public class LC0637AverageOfLevelsInBinaryTree {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of(4.0, 4.0, 4.0);
        Solution solution = new Solution();
        var answer = solution.averageOfLevels(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of(4.0, 4.0, 4.0);
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.averageOfLevels(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }


    static class Solution {
        public List<Double> averageOfLevels(TreeNode root) {
            List<Double> sums = new ArrayList<>();
            List<Integer> counts = new ArrayList<>();
            traversal(root, 0, sums, counts);
            for (int i = 0; i < sums.size(); i++) {
                sums.set(i, sums.get(i) / counts.get(i));
            }
            return sums;
        }
    
        // ① depth 标记层；sums 和 counts 分别记录各层总和、数量。
        private void traversal(TreeNode node, int depth,
                List<Double> sums, List<Integer> counts) {
            // ② 空节点不参与统计。
            if (node == null) {
                return;
            }
            // ③ 新层先建统计位置；当前值和数量更新后，再处理孩子。
            if (depth == sums.size()) {
                // 总和用 double，避免一整层用 int 相加溢出。
                sums.add(0.0);
                counts.add(0);
            }
            sums.set(depth, sums.get(depth) + node.val);
            counts.set(depth, counts.get(depth) + 1);
            traversal(node.left, depth + 1, sums, counts);
            traversal(node.right, depth + 1, sums, counts);
        }
    }

    static class SolutionIterative {
        public List<Double> averageOfLevels(TreeNode root) {
            List<Double> result = new ArrayList<>();
            if (root == null) {
                return result;
            }
            Queue<TreeNode> queue = new ArrayDeque<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                // 先固定本层数量；本轮新入队的孩子留到下一层处理。
                int levelSize = queue.size();
                // 每层重新累计，用 double 防止 int 求和溢出。
                double sum = 0;
                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    sum += node.val;
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }
                result.add(sum / levelSize);
            }
            return result;
        }
    }
}
