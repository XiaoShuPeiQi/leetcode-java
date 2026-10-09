package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.23。
public class LC0113PathSumII {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of(List.of(4, 2, 1));
        Solution solution = new Solution();
        var answer = solution.pathSum(root, 7);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7], targetSum = 7", expected, actual, true);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of(List.of(4, 2, 1));
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.pathSum(root, 7);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7], targetSum = 7", expected, actual, true);
    }


    static class Solution {
        public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
            List<List<Integer>> result = new ArrayList<>();
            traversal(root, targetSum, new ArrayList<>(), result);
            return result;
        }
    
        // ① remaining 尚未扣当前值；path 是共享的当前路径，结果要复制。
        private void traversal(TreeNode node, long remaining,
                List<Integer> path, List<List<Integer>> result) {
            // ② 空节点没有路径可继续，直接返回。
            if (node == null) {
                return;
            }
            // ③ 加自己、扣目标，到合格叶子存快照；两边搜索完后撤销当前节点。
            path.add(node.val);
            long nextRemaining = remaining - node.val;
            if (node.left == null && node.right == null && nextRemaining == 0) {
                // 保存副本；直接放 path，后续撤销会把已有答案改掉。
                result.add(new ArrayList<>(path));
            }
            traversal(node.left, nextRemaining, path, result);
            traversal(node.right, nextRemaining, path, result);
            // path 被各层共享，返回前删掉自己，恢复父路径。
            path.remove(path.size() - 1);
        }
    }

    static class SolutionIterative {
        private static class Frame {
            TreeNode node;
            long remaining;
            // false 模拟进入递归，true 模拟返回时撤销路径。
            boolean exit;
    
            Frame(TreeNode node, long remaining, boolean exit) {
                this.node = node;
                this.remaining = remaining;
                this.exit = exit;
            }
        }
    
        public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
            List<List<Integer>> result = new ArrayList<>();
            if (root == null) {
                return result;
            }
            List<Integer> path = new ArrayList<>();
            Deque<Frame> stack = new ArrayDeque<>();
            stack.push(new Frame(root, targetSum, false));
            while (!stack.isEmpty()) {
                Frame frame = stack.pop();
                // 退出时移除当前节点，恢复父路径后再处理兄弟分支。
                if (frame.exit) {
                    path.remove(path.size() - 1);
                    continue;
                }
                TreeNode node = frame.node;
                path.add(node.val);
                long remaining = frame.remaining - node.val;
                if (node.left == null && node.right == null && remaining == 0) {
                    // 答案保存副本，避免退出事件撤销共享 path 时破坏结果。
                    result.add(new ArrayList<>(path));
                }
                // 退出事件最后执行，确保左右子树处理期间父路径一直存在。
                stack.push(new Frame(node, remaining, true));
                if (node.right != null) {
                    stack.push(new Frame(node.right, remaining, false));
                }
                if (node.left != null) {
                    stack.push(new Frame(node.left, remaining, false));
                }
            }
            return result;
        }
    }
}
