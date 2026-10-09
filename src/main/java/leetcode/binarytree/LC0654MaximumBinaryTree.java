package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.26。
public class LC0654MaximumBinaryTree {

    public static void main(String[] args) {
        // 测试来源：本地设计的代表性用例；原文未提供该题独立输入。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        int[] nums = {3, 2, 1, 6, 0, 5};
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(6, 3, 5, null, 2, 0, null, null, 1));
        Solution solution = new Solution();
        var answer = solution.constructMaximumBinaryTree(nums);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("Solution", "nums = [3, 2, 1, 6, 0, 5]", expected, actual);
    }

    private static void runSolutionIterative() {
        int[] nums = {3, 2, 1, 6, 0, 5};
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(6, 3, 5, null, 2, 0, null, null, 1));
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.constructMaximumBinaryTree(nums);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("SolutionIterative", "nums = [3, 2, 1, 6, 0, 5]", expected, actual);
    }


    static class Solution {
        public TreeNode constructMaximumBinaryTree(int[] nums) {
            return build(nums, 0, nums.length);
        }
    
        // ① 返回 nums[left, right) 构成的最大二叉树根。
        private TreeNode build(int[] nums, int left, int right) {
            // ② 空区间没有节点，返回 null。
            if (left == right) {
                return null;
            }
            // ③ 找区间最大值建根，再递归两侧；根下标不能重复进入子区间。
            int maximumIndex = left;
            for (int i = left + 1; i < right; i++) {
                if (nums[i] > nums[maximumIndex]) {
                    maximumIndex = i;
                }
            }
            TreeNode root = new TreeNode(nums[maximumIndex]);
            root.left = build(nums, left, maximumIndex);
            root.right = build(nums, maximumIndex + 1, right);
            return root;
        }
    }

    static class SolutionIterative {
        public TreeNode constructMaximumBinaryTree(int[] nums) {
            Deque<TreeNode> stack = new ArrayDeque<>();
            for (int value : nums) {
                TreeNode node = new TreeNode(value);
                // 新值更大，弹出的较小右链归入新节点左树；最后弹出者是这棵树的根。
                while (!stack.isEmpty() && stack.peek().val < value) {
                    node.left = stack.pop();
                }
                if (!stack.isEmpty()) {
                    // 留下的栈顶更大且在左边，新节点成为它的右孩子。
                    stack.peek().right = node;
                }
                stack.push(node);
            }
            // 栈底是没有更大父节点的最大值，即整棵树的根。
            return stack.peekLast();
        }
    }
}
