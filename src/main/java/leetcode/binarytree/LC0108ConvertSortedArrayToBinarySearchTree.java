package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.37。
public class LC0108ConvertSortedArrayToBinarySearchTree {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7));
        Solution solution = new Solution();
        var answer = solution.sortedArrayToBST(nums);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("Solution", "nums = [1, 2, 3, 4, 5, 6, 7]", expected, actual);
    }

    private static void runSolutionIterative() {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7));
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.sortedArrayToBST(nums);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("SolutionIterative", "nums = [1, 2, 3, 4, 5, 6, 7]", expected, actual);
    }


    static class Solution {
        public TreeNode sortedArrayToBST(int[] nums) {
            return build(nums, 0, nums.length);
        }
    
        // ① 返回 nums[left, right) 构成的平衡 BST 根。
        private TreeNode build(int[] nums, int left, int right) {
            // ② 空区间没有节点，返回 null。
            if (left == right) {
                return null;
            }
            // ③ 有序保证左小右大；选中点让两侧数量接近，再分别建孩子。
            int middle = left + (right - left) / 2;
            TreeNode root = new TreeNode(nums[middle]);
            root.left = build(nums, left, middle);
            root.right = build(nums, middle + 1, right);
            return root;
        }
    }

    static class SolutionIterative {
        private static class Task {
            TreeNode node;
            int left;
            int right;
    
            Task(TreeNode node, int left, int right) {
                this.node = node;
                this.left = left;
                this.right = right;
            }
        }
    
        public TreeNode sortedArrayToBST(int[] nums) {
            if (nums.length == 0) {
                return null;
            }
            TreeNode root = new TreeNode(0);
            Deque<Task> stack = new ArrayDeque<>();
            // 一个任务表示：把指定半开区间建成以 node 为根的子树。
            stack.push(new Task(root, 0, nums.length));
            while (!stack.isEmpty()) {
                Task task = stack.pop();
                // 当前区间选中点；左右区间分别交给新任务，排除中点。
                int middle = task.left + (task.right - task.left) / 2;
                task.node.val = nums[middle];
                if (middle + 1 < task.right) {
                    task.node.right = new TreeNode(0);
                    stack.push(new Task(task.node.right, middle + 1, task.right));
                }
                if (task.left < middle) {
                    task.node.left = new TreeNode(0);
                    stack.push(new Task(task.node.left, task.left, middle));
                }
            }
            return root;
        }
    }
}
