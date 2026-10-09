package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.12。
public class LC0226InvertBinaryTree {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(4, 6, 2, 7, 5, 3, 1));
        Solution solution = new Solution();
        var answer = solution.invertTree(root);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(4, 6, 2, 7, 5, 3, 1));
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.invertTree(root);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7]", expected, actual);
    }


    static class Solution {
        // ① 返回当前子树翻转后的根；操作直接修改原节点。
        public TreeNode invertTree(TreeNode root) {
            // ② 空树翻转后仍为空。
            if (root == null) {
                return null;
            }
            // ③ 交换两侧，再递归翻转交换后的孩子；当前根仍是 root。
            TreeNode temporary = root.left;
            root.left = root.right;
            root.right = temporary;
            invertTree(root.left);
            invertTree(root.right);
            return root;
        }
    }

    static class SolutionIterative {
        public TreeNode invertTree(TreeNode root) {
            if (root == null) {
                return null;
            }
            Queue<TreeNode> queue = new ArrayDeque<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                // 每个节点只交换一次，所有局部交换组成整棵镜像。
                TreeNode temporary = node.left;
                node.left = node.right;
                node.right = temporary;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            return root;
        }
    }
}
