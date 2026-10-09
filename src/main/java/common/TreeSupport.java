package common;

import java.util.*;

// 运行适配：层序构建、结构输出和校验，不参与题目算法。
public final class TreeSupport {
    private TreeSupport() {
    }

    public static TreeNode fromLevelOrder(Integer... values) {
        if (values.length == 0 || values[0] == null) return null;
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int index = 1;
        // 从上到下逐层构建，同层内先左后右；null 占据孩子位置。
        while (!queue.isEmpty() && index < values.length) {
            TreeNode parent = queue.poll();
            Integer left = values[index++];
            if (left != null) {
                parent.left = new TreeNode(left);
                queue.offer(parent.left);
            }
            if (index < values.length) {
                Integer right = values[index++];
                if (right != null) {
                    parent.right = new TreeNode(right);
                    queue.offer(parent.right);
                }
            }
        }
        if (index < values.length) throw new IllegalArgumentException("存在没有父节点的输入");
        return root;
    }

    public static List<Integer> toLevelOrder(TreeNode root) {
        List<Integer> values = new ArrayList<>();
        if (root == null) return values;
        // ArrayList 允许空节点，保留左右结构；仅删掉末尾无意义的 null。
        List<TreeNode> queue = new ArrayList<>();
        queue.add(root);
        for (int i = 0; i < queue.size(); i++) {
            TreeNode node = queue.get(i);
            values.add(node == null ? null : node.val);
            if (node != null) {
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        while (values.get(values.size() - 1) == null) values.remove(values.size() - 1);
        return values;
    }

    public static void check(String method, String input, Object expected, Object actual) {
        check(method, input, expected, actual, false);
    }

    public static void check(String method, String input, Object expected, Object actual,
            boolean unordered) {
        boolean pass;
        if (unordered) {
            List<String> left = new ArrayList<>(), right = new ArrayList<>();
            for (Object value : (List<?>) expected) left.add(value.toString());
            for (Object value : (List<?>) actual) right.add(value.toString());
            Collections.sort(left);
            Collections.sort(right);
            pass = left.equals(right); // 保留重复结果的数量，不改变 actual。
        } else if (expected instanceof List<?> left && actual instanceof List<?> right
                && !left.isEmpty() && left.get(0) instanceof Double) {
            pass = left.size() == right.size();
            for (int i = 0; pass && i < left.size(); i++) {
                pass = Math.abs((Double) left.get(i) - (Double) right.get(i)) < 1e-9;
            }
        } else {
            pass = Objects.deepEquals(expected, actual);
        }
        System.out.println("Method: " + method);
        System.out.println("Input: " + input);
        System.out.println("Expected:\n" + display(expected));
        System.out.println("Actual:\n" + display(actual));
        System.out.println("Result: " + (pass ? "PASS" : "FAIL"));
        if (!pass) throw new AssertionError(method + " 校验失败");
    }

    private static String display(Object value) {
        return value instanceof int[] array ? Arrays.toString(array) : String.valueOf(value);
    }
}
