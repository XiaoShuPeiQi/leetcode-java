package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.24。
public class LC0106ConstructBinaryTreeFromInorderAndPostorderTraversal {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        int[] inorder = {1, 2, 3, 4, 5, 6, 7};
        int[] postorder = {1, 3, 2, 5, 7, 6, 4};
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7));
        Solution solution = new Solution();
        var answer = solution.buildTree(inorder, postorder);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("Solution", "inorder = [1, 2, 3, 4, 5, 6, 7], postorder = [1, 3, 2, 5, 7, 6, 4]", expected, actual);
    }

    private static void runSolutionIterative() {
        int[] inorder = {1, 2, 3, 4, 5, 6, 7};
        int[] postorder = {1, 3, 2, 5, 7, 6, 4};
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7));
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.buildTree(inorder, postorder);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("SolutionIterative", "inorder = [1, 2, 3, 4, 5, 6, 7], postorder = [1, 3, 2, 5, 7, 6, 4]", expected, actual);
    }


    static class Solution {
        public TreeNode buildTree(int[] inorder, int[] postorder) {
            Map<Integer, Integer> positions = new HashMap<>();
            for (int i = 0; i < inorder.length; i++) {
                positions.put(inorder[i], i);
            }
            return build(postorder, 0, postorder.length, 0, inorder.length, positions);
        }
    
        // ① 两组 [left, right) 区间描述同一棵子树；返回建好的根。
        private TreeNode build(int[] postorder, int postLeft, int postRight,
                int inLeft, int inRight, Map<Integer, Integer> positions) {
            // ② 空区间没有节点，返回 null 供父节点挂接。
            if (postLeft == postRight) {
                return null;
            }
            // ③ 后序末尾是根；中序定位根后，用左侧节点数切分后序。
            int rootValue = postorder[postRight - 1];
            int pivot = positions.get(rootValue);
            int leftSize = pivot - inLeft;
            TreeNode root = new TreeNode(rootValue);
            // 后序先左后右再根；左区间取前 leftSize 个，右区间排除末尾根。
            root.left = build(postorder, postLeft, postLeft + leftSize,
                    inLeft, pivot, positions);
            root.right = build(postorder, postLeft + leftSize, postRight - 1,
                    pivot + 1, inRight, positions);
            return root;
        }
    }

    static class SolutionIterative {
        public TreeNode buildTree(int[] inorder, int[] postorder) {
            if (postorder.length == 0) {
                return null;
            }
            TreeNode root = new TreeNode(postorder[postorder.length - 1]);
            Deque<TreeNode> stack = new ArrayDeque<>();
            stack.push(root);
            // 倒着读后序为根→右→左；倒着读中序为右→根→左。
            int inorderIndex = inorder.length - 1;
            for (int i = postorder.length - 2; i >= 0; i--) {
                TreeNode parent = stack.peek();
                TreeNode child = new TreeNode(postorder[i]);
                // 还没到当前根的中序位置，说明继续构造右侧。
                if (parent.val != inorder[inorderIndex]) {
                    parent.right = child;
                } else {
                    while (!stack.isEmpty()
                            && stack.peek().val == inorder[inorderIndex]) {
                        parent = stack.pop();
                        inorderIndex--;
                    }
                    // 匹配中序后弹回祖先，下一节点接最后弹出祖先的左侧。
                    parent.left = child;
                }
                stack.push(child);
            }
            return root;
        }
    }
}
