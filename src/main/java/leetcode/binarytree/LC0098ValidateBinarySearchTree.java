package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.29。
public class LC0098ValidateBinarySearchTree {

    public static void main(String[] args) {
        // 测试来源：飞书原文 4.4 的最小反例。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionBounds();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(10, null, 15, 6);
        Object expected = false;
        Solution solution = new Solution();
        var answer = solution.isValidBST(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [10, null, 15, 6]", expected, actual);
    }

    private static void runSolutionBounds() {
        TreeNode root = TreeSupport.fromLevelOrder(10, null, 15, 6);
        Object expected = false;
        SolutionBounds solution = new SolutionBounds();
        var answer = solution.isValidBST(root);
        Object actual = answer;
        TreeSupport.check("SolutionBounds", "root = [10, null, 15, 6]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(10, null, 15, 6);
        Object expected = false;
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.isValidBST(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [10, null, 15, 6]", expected, actual);
    }


    static class Solution {
        private TreeNode previous;
    
        public boolean isValidBST(TreeNode root) {
            // 每次入口重置，避免上次调用的前驱影响本次判断。
            previous = null;
            return validate(root);
        }
    
        // ① 检查当前子树；previous 保存整个中序过程的上一个节点。
        private boolean validate(TreeNode node) {
            // ② 空树不会破坏严格递增，返回 true。
            if (node == null) {
                return true;
            }
            // ③ 先左、再比当前与前驱、最后右；任一步失败就停止。
            if (!validate(node.left)) {
                return false;
            }
            // 必须严格递增；相等也不是本题的合法 BST。
            if (previous != null && node.val <= previous.val) {
                return false;
            }
            previous = node;
            return validate(node.right);
        }
    }

    static class SolutionBounds {
        public boolean isValidBST(TreeNode root) {
            // 边界用 long，允许节点本身取 int 的最小或最大值。
            return within(root, Long.MIN_VALUE, Long.MAX_VALUE);
        }
    
        // ① 当前子树所有值必须在 (lower, upper) 内，边界来自全部祖先。
        private boolean within(TreeNode node, long lower, long upper) {
            // ② 空树没有违反边界的节点，返回 true。
            if (node == null) {
                return true;
            }
            // ③ 先检查当前值；左边收紧上界，右边收紧下界。
            if (node.val <= lower || node.val >= upper) {
                return false;
            }
            return within(node.left, lower, node.val)
                    && within(node.right, node.val, upper);
        }
    }

    static class SolutionIterative {
        public boolean isValidBST(TreeNode root) {
            Deque<TreeNode> stack = new ArrayDeque<>();
            TreeNode cur = root;
            TreeNode previous = null;
            while (cur != null || !stack.isEmpty()) {
                // 沿左链压栈，保存之后返回并处理中间节点的位置。
                while (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                }
                // 中序轮到当前值；必须大于前驱，否则立即失败。
                cur = stack.pop();
                if (previous != null && cur.val <= previous.val) {
                    return false;
                }
                previous = cur;
                cur = cur.right;
            }
            return true;
        }
    }
}
