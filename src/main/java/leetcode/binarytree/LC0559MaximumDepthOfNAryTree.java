package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.15。
public class LC0559MaximumDepthOfNAryTree {

    public static void main(String[] args) {
        // 测试来源：本地设计的代表性用例；原文未提供该题独立输入。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        Node root = naryTree();
        Object expected = 3;
        Solution solution = new Solution();
        var answer = solution.maxDepth(root);
        Object actual = answer;
        TreeSupport.check("Solution", "N 叉树：1 的孩子为 3、2、4；3 的孩子为 5、6", expected, actual);
    }

    private static void runSolutionIterative() {
        Node root = naryTree();
        Object expected = 3;
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.maxDepth(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "N 叉树：1 的孩子为 3、2、4；3 的孩子为 5、6", expected, actual);
    }

    static class Node {
        public int val;
        public List<Node> children;
        public Node(int val) { this.val = val; this.children = new ArrayList<>(); }
    }

    private static Node naryTree() {
        // 从上到下逐层创建；同层从左到右连接。
        Node root = new Node(1);
        Node three = new Node(3), two = new Node(2), four = new Node(4);
        root.children.addAll(List.of(three, two, four));
        three.children.addAll(List.of(new Node(5), new Node(6)));
        return root;
    }

    static class Solution {
        // ① 返回当前 N 叉子树高度，供父节点选最高的孩子。
        public int maxDepth(Node root) {
            // ② 空树高度为 0。
            if (root == null) {
                return 0;
            }
            // ③ 所有孩子取最大高度；没有孩子时保留 0，再加自己一层。
            int childHeight = 0;
            if (root.children != null) {
                for (Node child : root.children) {
                    childHeight = Math.max(childHeight, maxDepth(child));
                }
            }
            return childHeight + 1;
        }
    }

    static class SolutionIterative {
        public int maxDepth(Node root) {
            if (root == null) {
                return 0;
            }
            Queue<Node> queue = new ArrayDeque<>();
            queue.offer(root);
            int depth = 0;
            while (!queue.isEmpty()) {
                // 先固定本层数量；本轮新入队的孩子留到下一层处理。
                int levelSize = queue.size();
                // 每轮完整处理一层，统计总层数。
                depth++;
                for (int i = 0; i < levelSize; i++) {
                    Node node = queue.poll();
                    if (node.children != null) {
                        for (Node child : node.children) {
                            if (child != null) {
                                queue.offer(child);
                            }
                        }
                    }
                }
            }
            return depth;
        }
    }
}
