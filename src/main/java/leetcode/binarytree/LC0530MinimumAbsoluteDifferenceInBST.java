package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.30。
public class LC0530MinimumAbsoluteDifferenceInBST {

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
        var answer = solution.getMinimumDifference(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = 1;
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.getMinimumDifference(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }


    static class Solution {
        private TreeNode previous;
    
        private int minimum;
    
        public int getMinimumDifference(TreeNode root) {
            previous = null;
            minimum = Integer.MAX_VALUE;
            traversal(root);
            return minimum;
        }
    
        // ① 中序访问当前子树；previous 和 minimum 保存相邻比较状态。
        private void traversal(TreeNode node) {
            // ② 空节点没有数值可比较。
            if (node == null) {
                return;
            }
            // ③ 先完成左边，当前才紧挨 previous；比较后更新前驱，再走右边。
            traversal(node.left);
            if (previous != null) {
                minimum = Math.min(minimum, node.val - previous.val);
            }
            // 比较完成再更新，不能先覆盖前驱再求差。
            previous = node;
            traversal(node.right);
        }
    }

    static class SolutionIterative {
        public int getMinimumDifference(TreeNode root) {
            int result = Integer.MAX_VALUE;
            TreeNode previous = null;
            TreeNode cur = root;
            Deque<TreeNode> stack = new ArrayDeque<>();
            while (cur != null || !stack.isEmpty()) {
                // 沿左链压栈，保存之后返回并处理中间节点的位置。
                while (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                }
                cur = stack.pop();
                // 有序序列只需比较相邻值；第一个没有前驱。
                if (previous != null) {
                    result = Math.min(result, cur.val - previous.val);
                }
                // 比较后再更新前驱，供下一个中序节点使用。
                previous = cur;
                cur = cur.right;
            }
            return result;
        }
    }
}
