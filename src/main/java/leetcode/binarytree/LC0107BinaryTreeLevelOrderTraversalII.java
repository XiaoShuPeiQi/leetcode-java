package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.5。
public class LC0107BinaryTreeLevelOrderTraversalII {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of(List.of(1, 3, 5, 7), List.of(2, 6), List.of(4));
        Solution solution = new Solution();
        var answer = solution.levelOrderBottom(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of(List.of(1, 3, 5, 7), List.of(2, 6), List.of(4));
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.levelOrderBottom(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }


    static class Solution {
        public List<List<Integer>> levelOrderBottom(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();
            traversal(root, 0, result);
            // 只反转层的先后，不改变每层内部的左右顺序。
            Collections.reverse(result);
            return result;
        }
    
        // ① 先按 depth 收集正常层序，反转交给入口统一做。
        private void traversal(TreeNode node, int depth, List<List<Integer>> result) {
            // ② 空节点没有值，结束这一支。
            if (node == null) {
                return;
            }
            // ③ 先建当前层、加当前值，再按左到右收集孩子。
            if (depth == result.size()) {
                result.add(new ArrayList<>());
            }
            result.get(depth).add(node.val);
            traversal(node.left, depth + 1, result);
            traversal(node.right, depth + 1, result);
        }
    }

    static class SolutionIterative {
        public List<List<Integer>> levelOrderBottom(TreeNode root) {
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
            // 只反转层的先后，不反转每层的节点。
            Collections.reverse(result);
            return result;
        }
    }
}
