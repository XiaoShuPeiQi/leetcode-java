package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.22。
public class LC0112PathSum {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = true;
        Solution solution = new Solution();
        var answer = solution.hasPathSum(root, 7);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7], targetSum = 7", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = true;
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.hasPathSum(root, 7);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7], targetSum = 7", expected, actual);
    }


    static class Solution {
        public boolean hasPathSum(TreeNode root, int targetSum) {
            return exists(root, targetSum);
        }
    
        // ① remaining 是当前节点到叶子还需凑出的和，尚未扣当前值。
        private boolean exists(TreeNode node, long remaining) {
            // ② 空树没有根到叶路径，所以返回 false。
            if (node == null) {
                return false;
            }
            // ③ 到叶子才判成功；非叶子扣掉自己，把剩余任务交给任一孩子。
            if (node.left == null && node.right == null) {
                return remaining == node.val;
            }
            // 数字按值传递，每个孩子拿相同余量，不需要手动恢复。
            long nextRemaining = remaining - node.val;
            // 任一侧找到即可；|| 会在左侧成功时停止搜索。
            return exists(node.left, nextRemaining) || exists(node.right, nextRemaining);
        }
    }

    static class SolutionIterative {
        private static class State {
            TreeNode node;
            // 保存到达当前节点之前的余量，弹出时再扣当前值。
            long remaining;
    
            State(TreeNode node, long remaining) {
                this.node = node;
                this.remaining = remaining;
            }
        }
    
        public boolean hasPathSum(TreeNode root, int targetSum) {
            if (root == null) {
                return false;
            }
            Deque<State> stack = new ArrayDeque<>();
            stack.push(new State(root, targetSum));
            while (!stack.isEmpty()) {
                State state = stack.pop();
                TreeNode node = state.node;
                long remaining = state.remaining - node.val;
                // 扣完且到叶子才成功；非叶子余量为 0 也不能提前返回。
                if (node.left == null && node.right == null && remaining == 0) {
                    return true;
                }
                if (node.right != null) {
                    stack.push(new State(node.right, remaining));
                }
                if (node.left != null) {
                    stack.push(new State(node.left, remaining));
                }
            }
            return false;
        }
    }
}
