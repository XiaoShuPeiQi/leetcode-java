package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.35。
public class LC0450DeleteNodeInABST {

    public static void main(String[] args) {
        // 测试来源：飞书原文 2.2 的七节点树（相关数组取该树的遍历序列）。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(5, 2, 6, 1, 3, null, 7));
        Solution solution = new Solution();
        var answer = solution.deleteNode(root, 4);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("Solution", "root = [4, 2, 6, 1, 3, 5, 7], key = 4", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 6, 1, 3, 5, 7);
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(5, 2, 6, 1, 3, null, 7));
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.deleteNode(root, 4);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("SolutionIterative", "root = [4, 2, 6, 1, 3, 5, 7], key = 4", expected, actual);
    }


    static class Solution {
        // ① 返回删除后的子树根；删掉当前根时，返回的节点可能改变。
        public TreeNode deleteNode(TreeNode root, int key) {
            // ② 空位置说明没找到目标，返回 null。
            if (root == null) {
                return null;
            }
            // ③ 未命中只改一侧；命中后按孩子数量，决定由谁接替当前根。
            if (key < root.val) {
                root.left = deleteNode(root.left, key);
            } else if (key > root.val) {
                root.right = deleteNode(root.right, key);
            } else {
                // 无孩子或只有右孩子：由右边接替；返回值可能为 null。
                if (root.left == null) {
                    return root.right;
                }
                // 只有左孩子：由左边接替。
                if (root.right == null) {
                    return root.left;
                }
                // 两个孩子都在：右子树最小节点是中序后继，且没有左孩子。
                TreeNode successor = root.right;
                while (successor.left != null) {
                    successor = successor.left;
                }
                root.val = successor.val;
                // 复制后继值后，删掉原后继，避免重复，并接回修改后的右树。
                root.right = deleteNode(root.right, successor.val);
            }
            return root;
        }
    }

    static class SolutionIterative {
        public TreeNode deleteNode(TreeNode root, int key) {
            // 迭代没有返回值接回机制，需要记住父节点来改孩子引用。
            TreeNode parent = null;
            TreeNode cur = root;
            while (cur != null && cur.val != key) {
                parent = cur;
                if (key < cur.val) {
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }
            }
            if (cur == null) {
                return root;
            }
            // 两个孩子：复制右侧最小值，再把待删除对象改成后继。
            if (cur.left != null && cur.right != null) {
                TreeNode successorParent = cur;
                TreeNode successor = cur.right;
                while (successor.left != null) {
                    successorParent = successor;
                    successor = successor.left;
                }
                cur.val = successor.val;
                parent = successorParent;
                cur = successor;
            }
            // 后继没有左孩子；一般待删节点至多一个孩子，由非空侧接替。
            TreeNode replacement = cur.left;
            if (replacement == null) {
                replacement = cur.right;
            }
            // 删的是整棵树根，直接返回 replacement 作为新根。
            if (parent == null) {
                return replacement;
            }
            if (parent.left == cur) {
                parent.left = replacement;
            } else {
                parent.right = replacement;
            }
            return root;
        }
    }
}
