package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.8。
public class LC0429NAryTreeLevelOrderTraversal {

    public static void main(String[] args) {
        // 测试来源：本地设计的代表性用例；原文未提供该题独立输入。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        Node root = naryTree();
        Object expected = List.of(List.of(1), List.of(3, 2, 4), List.of(5, 6));
        Solution solution = new Solution();
        var answer = solution.levelOrder(root);
        Object actual = answer;
        TreeSupport.check("Solution", "N 叉树：1 的孩子为 3、2、4；3 的孩子为 5、6", expected, actual);
    }

    private static void runSolutionIterative() {
        Node root = naryTree();
        Object expected = List.of(List.of(1), List.of(3, 2, 4), List.of(5, 6));
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.levelOrder(root);
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
        public List<List<Integer>> levelOrder(Node root) {
            List<List<Integer>> result = new ArrayList<>();
            traversal(root, 0, result);
            return result;
        }
    
        // ① depth 决定放在哪一层；result 保存各层节点。
        private void traversal(Node node, int depth, List<List<Integer>> result) {
            // ② 空节点没有内容，直接返回。
            if (node == null) {
                return;
            }
            // ③ 记录当前节点，再按 children 的顺序处理下一层。
            if (depth == result.size()) {
                result.add(new ArrayList<>());
            }
            result.get(depth).add(node.val);
            if (node.children != null) {
                // 二叉树的两次递归，在这里换成遍历所有孩子。
                for (Node child : node.children) {
                    traversal(child, depth + 1, result);
                }
            }
        }
    }

    static class SolutionIterative {
        public List<List<Integer>> levelOrder(Node root) {
            List<List<Integer>> result = new ArrayList<>();
            if (root == null) {
                return result;
            }
            Queue<Node> queue = new ArrayDeque<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                // 先固定本层数量；本轮新入队的孩子留到下一层处理。
                int levelSize = queue.size();
                List<Integer> level = new ArrayList<>();
                for (int i = 0; i < levelSize; i++) {
                    Node node = queue.poll();
                    level.add(node.val);
                    if (node.children != null) {
                        // 按孩子列表顺序入队，保持下一层从左到右。
                        for (Node child : node.children) {
                            if (child != null) {
                                queue.offer(child);
                            }
                        }
                    }
                }
                result.add(level);
            }
            return result;
        }
    }
}
