package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.4。
public class LC0102BinaryTreeLevelOrderTraversal {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of(List.of(4), List.of(2, 6), List.of(1, 3, 5, 7));
        Solution solution = new Solution();
        var answer = solution.levelOrder(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of(List.of(4), List.of(2, 6), List.of(1, 3, 5, 7));
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.levelOrder(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }


    static class Solution {
        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();
            traversal(root, 0, result);
            return result;
        }
    
        // ① depth 是层下标，根为 0；节点写入 result[depth]。
        private void traversal(TreeNode node, int depth, List<List<Integer>> result) {
            // ② 空节点不属于任何层，直接返回。
            if (node == null) {
                return;
            }
            // ③ 首次到达这一层先建列表，再记当前值并继续下一层。
            if (depth == result.size()) {
                result.add(new ArrayList<>());
            }
            result.get(depth).add(node.val);
            // 孩子属于下一层；先左后右保证同层从左到右。
            traversal(node.left, depth + 1, result);
            traversal(node.right, depth + 1, result);
        }
    }

    static class SolutionIterative {
        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();
            if (root == null) {
                return result;
            }
            Queue<TreeNode> queue = new ArrayDeque<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                // 先固定本层数量；本轮新入队的孩子留到下一层处理。
                int levelSize = queue.size();
                List<Integer> level = new ArrayList<>();
                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    level.add(node.val);
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }
                result.add(level);
            }
            return result;
        }
    }
}
