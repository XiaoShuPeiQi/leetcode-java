package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.11。
public class LC0117PopulatingNextRightPointersInEachNodeII {

    public static void main(String[] args) {
        // 测试来源：本地设计的代表性用例；原文未提供该题独立输入。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        Node root = nextTree(1, 2, 3, 4, null, null, 5);
        Object expected = List.of(List.of(1), List.of(2, 3), List.of(4, 5));
        Solution solution = new Solution();
        var answer = solution.connect(root);
        Object actual = nextLevels(answer);
        TreeSupport.check("Solution", "root = [1, 2, 3, 4, null, null, 5]", expected, actual);
    }

    private static void runSolutionIterative() {
        Node root = nextTree(1, 2, 3, 4, null, null, 5);
        Object expected = List.of(List.of(1), List.of(2, 3), List.of(4, 5));
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.connect(root);
        Object actual = nextLevels(answer);
        TreeSupport.check("SolutionIterative", "root = [1, 2, 3, 4, null, null, 5]", expected, actual);
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
            link(root, 0, new ArrayList<>());
            return root;
        }
    
        // ① previous[depth] 记住本层刚访问的节点，便于连到当前。
        private void link(Node node, int depth, List<Node> previous) {
            // ② 空位置略过，不加入 next 链。
            if (node == null) {
                return;
            }
            // ③ 先清空旧 next；新层登记首节点，已有层把前一个节点连过来。
            node.next = null;
            if (depth == previous.size()) {
                previous.add(node);
            } else {
                previous.get(depth).next = node;
                previous.set(depth, node);
            }
            // 先左后右，使同一层节点按从左到右到达。
            link(node.left, depth + 1, previous);
            link(node.right, depth + 1, previous);
        }
    }

    static class SolutionIterative {
        public Node connect(Node root) {
            if (root == null) {
                return null;
            }
            root.next = null;
            Node levelStart = root;
            while (levelStart != null) {
                // dummy 不进原树，只作为下一层链表的虚拟头。
                Node dummy = new Node(0);
                Node tail = dummy;
                for (Node node = levelStart; node != null; node = node.next) {
                    if (node.left != null) {
                        // 把非空孩子依次接到链尾，空缺自然被跳过。
                        tail.next = node.left;
                        tail = tail.next;
                    }
                    if (node.right != null) {
                        tail.next = node.right;
                        tail = tail.next;
                    }
                }
                // 最后节点收尾，避免保留旧 next。
                tail.next = null;
                // dummy.next 就是下一层首节点，没有孩子时为 null。
                levelStart = dummy.next;
            }
            return root;
        }
    }
}
