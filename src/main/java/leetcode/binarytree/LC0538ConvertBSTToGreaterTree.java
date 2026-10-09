package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.38。
public class LC0538ConvertBSTToGreaterTree {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(22, 27, 13, 28, 25, 18, 7));
        Solution solution = new Solution();
        var answer = solution.convertBST(root);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(22, 27, 13, 28, 25, 18, 7));
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.convertBST(root);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }


    static class Solution {
        private int sum;
    
        public TreeNode convertBST(TreeNode root) {
            // 每次转换重新开始累计，避免复用 Solution 时带入旧结果。
            sum = 0;
            accumulate(root);
            return root;
        }
    
        // ① sum 是已访问的更大原值之和；遍历时直接修改节点值。
        private void accumulate(TreeNode node) {
            // ② 空节点没有值可累加。
            if (node == null) {
                return;
            }
            // ③ 右→根→左从大到小；先累加当前旧值再写回，供更小节点使用。
            accumulate(node.right);
            sum += node.val;
            node.val = sum;
            accumulate(node.left);
        }
    }

    static class SolutionIterative {
        public TreeNode convertBST(TreeNode root) {
            int runningSum = 0;
            Deque<TreeNode> stack = new ArrayDeque<>();
            TreeNode cur = root;
            while (cur != null || !stack.isEmpty()) {
                // 先沿右链走到底，反中序才能从大到小访问。
                while (cur != null) {
                    stack.push(cur);
                    cur = cur.right;
                }
                cur = stack.pop();
                // 先加原值再写回，此时累计和已包含所有更大的原值。
                runningSum += cur.val;
                cur.val = runningSum;
                cur = cur.left;
            }
            return root;
        }
    }
}
