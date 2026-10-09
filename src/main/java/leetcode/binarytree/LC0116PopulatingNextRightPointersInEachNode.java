package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.10。
public class LC0116PopulatingNextRightPointersInEachNode {

    public static void main(String[] args) {
        // 测试来源：本地设计的代表性用例；原文未提供该题独立输入。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        Node root = nextTree(1, 2, 3, 4, 5, 6, 7);
        Object expected = List.of(List.of(1), List.of(2, 3), List.of(4, 5, 6, 7));
        Solution solution = new Solution();
        var answer = solution.connect(root);
        Object actual = nextLevels(answer);
        TreeSupport.check("Solution", "root = [1, 2, 3, 4, 5, 6, 7]", expected, actual);
    }

    private static void runSolutionIterative() {
        Node root = nextTree(1, 2, 3, 4, 5, 6, 7);
        Object expected = List.of(List.of(1), List.of(2, 3), List.of(4, 5, 6, 7));
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.connect(root);
        Object actual = nextLevels(answer);
        TreeSupport.check("SolutionIterative", "root = [1, 2, 3, 4, 5, 6, 7]", expected, actual);
    }

    static class Node {
        public int val;
        public Node left, right, next;
        public Node(int val) { this.val = val; }
    }

    private static Node nextTree(Integer... values) {
        Node root = new Node(values[0]);
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        int index = 1;
        // 按层构建，先父节点，再下一层孩子。
        while (!queue.isEmpty() && index < values.length) {
            Node parent = queue.poll();
            Integer left = values[index++];
            if (left != null) { parent.left = new Node(left); queue.offer(parent.left); }
            if (index < values.length) {
                Integer right = values[index++];
                if (right != null) { parent.right = new Node(right); queue.offer(parent.right); }
            }
        }
        return root;
    }

    private static List<List<Integer>> nextLevels(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Node> level = new ArrayList<>();
            for (int size = queue.size(); size > 0; size--) {
                Node node = queue.poll();
                level.add(node);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            List<Integer> values = new ArrayList<>();
            for (int i = 0; i < level.size(); i++) {
                Node node = level.get(i);
                Node expectedNext = i + 1 < level.size() ? level.get(i + 1) : null;
                if (node.next != expectedNext) throw new AssertionError("next 指针错误: " + node.val);
                values.add(node.val);
            }
            result.add(values);
        }
        return result;
    }

    static class Solution {
        public Node connect(Node root) {
            if (root != null) {
                root.next = null;
            }
            link(root);
            return root;
        }
    
        // ① 进入 link 时，本层 next 已可用；现在负责连接孩子。
        private void link(Node node) {
            // ② 空节点或叶子没有孩子可连接。
            if (node == null || node.left == null) {
                return;
            }
            // ③ 先连同父兄弟，再连跨父邻居；连好后孩子才能继续使用 next。
            node.left.next = node.right;
            node.right.next = null;
            if (node.next != null) {
                // 跨父连接：自己的右孩子，连隔壁父亲的左孩子。
                node.right.next = node.next.left;
            }
            link(node.left);
            link(node.right);
        }
    }

    static class SolutionIterative {
        public Node connect(Node root) {
            if (root == null) {
                return null;
            }
            root.next = null;
            Node levelStart = root;
            // 叶子层已由上一层连好；没有下一层时停止。
            while (levelStart.left != null) {
                Node cur = levelStart;
                while (cur != null) {
                    // 连同父兄弟，再连隔壁父亲的左孩子。
                    cur.left.next = cur.right;
                    cur.right.next = null;
                    if (cur.next != null) {
                        cur.right.next = cur.next.left;
                    }
                    // 本层 next 已经连好，可代替队列横向遍历。
                    cur = cur.next;
                }
                // 本层完成后，转到下一层的最左节点。
                levelStart = levelStart.left;
            }
            return root;
        }
    }
}
