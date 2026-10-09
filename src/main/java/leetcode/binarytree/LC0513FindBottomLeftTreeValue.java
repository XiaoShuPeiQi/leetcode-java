package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.21。
public class LC0513FindBottomLeftTreeValue {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = 1;
        Solution solution = new Solution();
        var answer = solution.findBottomLeftValue(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = 1;
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.findBottomLeftValue(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }


    static class Solution {
        private int deepest;
    
        private int answer;
    
        public int findBottomLeftValue(TreeNode root) {
            deepest = -1;
            answer = root.val;
            traversal(root, 0);
            return answer;
        }
    
        // ① depth 是当前层；deepest 和 answer 记录已见最深层及其首节点。
        private void traversal(TreeNode node, int depth) {
            // ② 空节点不更新答案。
            if (node == null) {
                return;
            }
            // ③ 先左后右，只在更深时更新；同层后来的节点不能覆盖最左值。
            if (depth > deepest) {
                deepest = depth;
                answer = node.val;
            }
            traversal(node.left, depth + 1);
            traversal(node.right, depth + 1);
        }
    }

    static class SolutionIterative {
        public int findBottomLeftValue(TreeNode root) {
            Queue<TreeNode> queue = new ArrayDeque<>();
            queue.offer(root);
            int result = root.val;
            while (!queue.isEmpty()) {
                // 先固定本层数量；本轮新入队的孩子留到下一层处理。
                int levelSize = queue.size();
                // 每层开始时队首是本层最左节点；更深一层会覆盖答案。
                result = queue.peek().val;
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
            return result;
        }
    }
}
