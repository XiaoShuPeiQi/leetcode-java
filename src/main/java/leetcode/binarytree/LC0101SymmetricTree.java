package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.13。
public class LC0101SymmetricTree {

    public static void main(String[] args) {
        // 测试来源：本地设计的代表性用例；原文未提供该题独立输入。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(1, 2, 2, 3, 4, 4, 3);
        Object expected = true;
        Solution solution = new Solution();
        var answer = solution.isSymmetric(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [1, 2, 2, 3, 4, 4, 3]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(1, 2, 2, 3, 4, 4, 3);
        Object expected = true;
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.isSymmetric(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [1, 2, 2, 3, 4, 4, 3]", expected, actual);
    }


    static class Solution {
        public boolean isSymmetric(TreeNode root) {
            if (root == null) {
                return true;
            }
            return mirror(root.left, root.right);
        }
    
        // ① 两个参数是待比较的子树根；返回它们是否镜像。
        private boolean mirror(TreeNode left, TreeNode right) {
            // ② 两边都空才匹配；只有一边空说明结构不对称。
            if (left == null || right == null) {
                return left == right;
            }
            // ③ 值相同后，再同时检查外侧和内侧的镜像关系。
            if (left.val != right.val) {
                return false;
            }
            // 外侧对外侧、内侧对内侧；两组都匹配才是镜像。
            return mirror(left.left, right.right)
                    && mirror(left.right, right.left);
        }
    }

    static class SolutionIterative {
        public boolean isSymmetric(TreeNode root) {
            if (root == null) {
                return true;
            }
            // 队列里保存节点对；数组可以保留 null，空结构也要比较。
            Queue<TreeNode[]> queue = new ArrayDeque<>();
            queue.offer(new TreeNode[] {root.left, root.right});
            while (!queue.isEmpty()) {
                TreeNode[] pair = queue.poll();
                TreeNode left = pair[0];
                TreeNode right = pair[1];
                // 两空匹配；一空说明结构不对称，直接失败。
                if (left == null || right == null) {
                    if (left != right) {
                        return false;
                    }
                    continue;
                }
                if (left.val != right.val) {
                    return false;
                }
                // 按镜像配对：外侧对外侧，内侧对内侧。
                queue.offer(new TreeNode[] {left.left, right.right});
                queue.offer(new TreeNode[] {left.right, right.left});
            }
            return true;
        }
    }
}
