package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.34。
public class LC0701InsertIntoABinarySearchTree {

    public static void main(String[] args) {
        // 测试来源：本地设计的代表性用例；原文未提供该题独立输入。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 7, 1, 3);
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(4, 2, 7, 1, 3, 5));
        Solution solution = new Solution();
        var answer = solution.insertIntoBST(root, 5);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("Solution", "root = [4, 2, 7, 1, 3], val = 5", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(4, 2, 7, 1, 3);
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(4, 2, 7, 1, 3, 5));
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.insertIntoBST(root, 5);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("SolutionIterative", "root = [4, 2, 7, 1, 3], val = 5", expected, actual);
    }


    static class Solution {
        // ① 返回插入后的子树根，父节点靠这个返回值接回孩子。
        public TreeNode insertIntoBST(TreeNode root, int val) {
            // ② 走到空位置就创建节点，它就是这一小棵树的新根。
            if (root == null) {
                return new TreeNode(val);
            }
            // ③ 按大小只改一侧；接回新根后，当前子树仍以 root 为根。
            if (val < root.val) {
                // 递归可能把空孩子变成新节点，必须用返回值接回。
                root.left = insertIntoBST(root.left, val);
            } else {
                root.right = insertIntoBST(root.right, val);
            }
            return root;
        }
    }

    static class SolutionIterative {
        public TreeNode insertIntoBST(TreeNode root, int val) {
            if (root == null) {
                return new TreeNode(val);
            }
            TreeNode cur = root;
            while (true) {
                if (val < cur.val) {
                    // 沿搜索方向找到空孩子，在那里挂上新节点即可。
                    if (cur.left == null) {
                        cur.left = new TreeNode(val);
                        break;
                    }
                    cur = cur.left;
                } else {
                    if (cur.right == null) {
                        cur.right = new TreeNode(val);
                        break;
                    }
                    cur = cur.right;
                }
            }
            return root;
        }
    }
}
