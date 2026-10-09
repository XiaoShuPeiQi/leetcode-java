package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.27。
public class LC0617MergeTwoBinaryTrees {

    public static void main(String[] args) {
        // 测试来源：本地设计的代表性用例；原文未提供该题独立输入。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root1 = TreeSupport.fromLevelOrder(1, 3, 2, 5);
        TreeNode root2 = TreeSupport.fromLevelOrder(2, 1, 3, null, 4, null, 7);
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(3, 4, 5, 5, 4, null, 7));
        Solution solution = new Solution();
        var answer = solution.mergeTrees(root1, root2);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("Solution", "root1 = [1, 3, 2, 5], root2 = [2, 1, 3, null, 4, null, 7]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root1 = TreeSupport.fromLevelOrder(1, 3, 2, 5);
        TreeNode root2 = TreeSupport.fromLevelOrder(2, 1, 3, null, 4, null, 7);
        Object expected = TreeSupport.toLevelOrder(TreeSupport.fromLevelOrder(3, 4, 5, 5, 4, null, 7));
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.mergeTrees(root1, root2);
        Object actual = TreeSupport.toLevelOrder(answer);
        TreeSupport.check("SolutionIterative", "root1 = [1, 3, 2, 5], root2 = [2, 1, 3, null, 4, null, 7]", expected, actual);
    }


    static class Solution {
        // ① 两个参数表示同一位置的子树；返回合并后的根。
        public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
            // ② 一边为空，直接复用另一边；两边都空自然返回 null。
            if (root1 == null) {
                return root2;
            }
            // 第二棵空，第一棵原样保留；这里不会新建独立副本。
            if (root2 == null) {
                return root1;
            }
            // ③ 两边都有才相加；孩子可能换根，必须接回两次递归的返回值。
            root1.val += root2.val;
            root1.left = mergeTrees(root1.left, root2.left);
            root1.right = mergeTrees(root1.right, root2.right);
            return root1;
        }
    }

    static class SolutionIterative {
        public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
            if (root1 == null) {
                return root2;
            }
            if (root2 == null) {
                return root1;
            }
            Deque<TreeNode[]> stack = new ArrayDeque<>();
            // 每对节点代表两棵树中的同一位置，值写回第一棵。
            stack.push(new TreeNode[] {root1, root2});
            while (!stack.isEmpty()) {
                TreeNode[] pair = stack.pop();
                TreeNode target = pair[0];
                TreeNode source = pair[1];
                target.val += source.val;
                if (target.left == null) {
                    // 目标侧为空就直接复用来源侧；两边都有才继续成对处理。
                    target.left = source.left;
                } else if (source.left != null) {
                    stack.push(new TreeNode[] {target.left, source.left});
                }
                if (target.right == null) {
                    target.right = source.right;
                } else if (source.right != null) {
                    stack.push(new TreeNode[] {target.right, source.right});
                }
            }
            return root1;
        }
    }
}
