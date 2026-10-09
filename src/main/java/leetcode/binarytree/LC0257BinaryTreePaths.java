package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.19。
public class LC0257BinaryTreePaths {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of("4->2->1", "4->2->3", "4->6->5", "4->6->7");
        Solution solution = new Solution();
        var answer = solution.binaryTreePaths(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual, true);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = List.of("4->2->1", "4->2->3", "4->6->5", "4->6->7");
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.binaryTreePaths(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual, true);
    }


    static class Solution {
        public List<String> binaryTreePaths(TreeNode root) {
            List<String> result = new ArrayList<>();
            traversal(root, new StringBuilder(), result);
            return result;
        }
    
        // ① path 是从根走到当前节点前的共享路径，result 保存完整答案。
        private void traversal(TreeNode node, StringBuilder path, List<String> result) {
            // ② 空节点没有值可追加，直接返回。
            if (node == null) {
                return;
            }
            // ③ 先记旧长度、加自己；孩子结束后恢复，兄弟分支才不会串路径。
            int oldLength = path.length();
            if (oldLength > 0) {
                path.append("->");
            }
            path.append(node.val);
            if (node.left == null && node.right == null) {
                // 到叶子才是一条完整路径；toString 保存独立结果。
                result.add(path.toString());
            } else {
                traversal(node.left, path, result);
                traversal(node.right, path, result);
            }
            // 回到进入前的父路径，防止左分支内容混入右分支。
            path.setLength(oldLength);
        }
    }

    static class SolutionIterative {
        private static class State {
            TreeNode node;
            // 每个栈状态带独立字符串路径，所以不需要共享路径的撤销。
            String path;
    
            State(TreeNode node, String path) {
                this.node = node;
                this.path = path;
            }
        }
    
        public List<String> binaryTreePaths(TreeNode root) {
            List<String> result = new ArrayList<>();
            if (root == null) {
                return result;
            }
            Deque<State> stack = new ArrayDeque<>();
            stack.push(new State(root, String.valueOf(root.val)));
            while (!stack.isEmpty()) {
                State state = stack.pop();
                TreeNode node = state.node;
                // 到叶子才收集完整的根到叶路径。
                if (node.left == null && node.right == null) {
                    result.add(state.path);
                }
                if (node.right != null) {
                    String nextPath = state.path + "->" + node.right.val;
                    stack.push(new State(node.right, nextPath));
                }
                if (node.left != null) {
                    String nextPath = state.path + "->" + node.left.val;
                    stack.push(new State(node.left, nextPath));
                }
            }
            return result;
        }
    }
}
