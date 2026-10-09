package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.25。
public class LC0105ConstructBinaryTreeFromPreorderAndInorderTraversal {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        int[] preorder = {4, 2, 1, 3, 6, 5, 7};
        int[] inorder = {1, 2, 3, 4, 5, 6, 7};
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7));
        Solution solution = new Solution();
        var answer = solution.buildTree(preorder, inorder);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("Solution", "preorder = [4, 2, 1, 3, 6, 5, 7], inorder = [1, 2, 3, 4, 5, 6, 7]", expected, actual);
    }

    private static void runSolutionIterative() {
        int[] preorder = {4, 2, 1, 3, 6, 5, 7};
        int[] inorder = {1, 2, 3, 4, 5, 6, 7};
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7));
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.buildTree(preorder, inorder);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("SolutionIterative", "preorder = [4, 2, 1, 3, 6, 5, 7], inorder = [1, 2, 3, 4, 5, 6, 7]", expected, actual);
    }


    static class Solution {
        public TreeNode buildTree(int[] preorder, int[] inorder) {
            Map<Integer, Integer> positions = new HashMap<>();
            for (int i = 0; i < inorder.length; i++) {
                positions.put(inorder[i], i);
            }
            return build(preorder, 0, 0, inorder.length, positions);
        }
    
        // ① preLeft 指向子树根；[inLeft, inRight) 圈定这棵子树的全部节点。
        private TreeNode build(int[] preorder, int preLeft, int inLeft, int inRight,
                Map<Integer, Integer> positions) {
            // ② 中序区间为空，说明这棵子树为空。
            if (inLeft == inRight) {
                return null;
            }
            // ③ 前序起点是根；中序左侧数量决定两边的前序起点。
            TreeNode root = new TreeNode(preorder[preLeft]);
            int pivot = positions.get(root.val);
            int leftSize = pivot - inLeft;
            root.left = build(preorder, preLeft + 1, inLeft, pivot, positions);
            // 跳过前序里的根和整棵左子树，才到右子树起点。
            root.right = build(preorder, preLeft + leftSize + 1,
                    pivot + 1, inRight, positions);
            return root;
        }
    }

    static class SolutionIterative {
        public TreeNode buildTree(int[] preorder, int[] inorder) {
            if (preorder.length == 0) {
                return null;
            }
            TreeNode root = new TreeNode(preorder[0]);
            Deque<TreeNode> stack = new ArrayDeque<>();
            stack.push(root);
            // 前序按根→左→右读入，中序用来判断何时从左侧返回。
            int inorderIndex = 0;
            for (int i = 1; i < preorder.length; i++) {
                TreeNode parent = stack.peek();
                TreeNode child = new TreeNode(preorder[i]);
                // 还没匹配当前根的中序位置，继续往左构造。
                if (parent.val != inorder[inorderIndex]) {
                    parent.left = child;
                } else {
                    while (!stack.isEmpty()
                            && stack.peek().val == inorder[inorderIndex]) {
                        parent = stack.pop();
                        inorderIndex++;
                    }
                    // 连续匹配并弹栈后，下一节点属于最后弹出祖先的右侧。
                    parent.right = child;
                }
                stack.push(child);
            }
            return root;
        }
    }
}
