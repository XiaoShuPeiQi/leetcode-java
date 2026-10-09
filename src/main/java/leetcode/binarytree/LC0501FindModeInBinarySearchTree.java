package leetcode.binarytree;

import common.TreeNode;
import common.TreeSupport;
import java.util.*;

// 原文：https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng，3.31。
public class LC0501FindModeInBinarySearchTree {

    public static void main(String[] args) {
        // 测试来源：本地设计的代表性用例；原文未提供该题独立输入。
        // 每种解法重新构建输入，防止结构修改影响下一种解法。
        runSolution();
        runSolutionIterative();
    }

    private static void runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(2, 1, 2);
        Object expected = new int[] {2};
        Solution solution = new Solution();
        var answer = solution.findMode(root);
        Object actual = answer;
        TreeSupport.check("Solution", "root = [2, 1, 2]", expected, actual);
    }

    private static void runSolutionIterative() {
        TreeNode root = TreeSupport.fromLevelOrder(2, 1, 2);
        Object expected = new int[] {2};
        SolutionIterative solution = new SolutionIterative();
        var answer = solution.findMode(root);
        Object actual = answer;
        TreeSupport.check("SolutionIterative", "root = [2, 1, 2]", expected, actual);
    }


    static class Solution {
        private Integer previous;
    
        private int count;
    
        private int maximumCount;
    
        private List<Integer> modes;
    
        private void reset() {
            previous = null;
            count = 0;
            maximumCount = 0;
            modes = new ArrayList<>();
        }
    
        public int[] findMode(TreeNode root) {
            // 新一次遍历必须清空上次调用留下的计数和答案。
            reset();
            traversal(root);
            return toArray();
        }
    
        // ① 中序访问当前子树，连续计数和众数由共享字段维护。
        private void traversal(TreeNode node) {
            // ② 空节点不参与频次统计。
            if (node == null) {
                return;
            }
            // ③ 左→当前→右让相同值连续出现；record 只需统计连续段。
            traversal(node.left);
            record(node.val);
            traversal(node.right);
        }
    
        private void record(int value) {
            // 中序中相同值连续：相同就续段，不同就从 1 重新计数。
            if (previous != null && previous == value) {
                count++;
            } else {
                count = 1;
            }
            if (count > maximumCount) {
                maximumCount = count;
                // 出现更高频率，原来的候选都不再是众数。
                modes.clear();
                modes.add(value);
            // 达到相同最高频率，当前值也应列入答案。
            } else if (count == maximumCount) {
                modes.add(value);
            }
            previous = value;
        }
    
        private int[] toArray() {
            int[] result = new int[modes.size()];
            for (int i = 0; i < modes.size(); i++) {
                result[i] = modes.get(i);
            }
            return result;
        }
    }

    static class SolutionIterative {
        private Integer previous;
    
        private int count;
    
        private int maximumCount;
    
        private List<Integer> modes;
    
        private void reset() {
            previous = null;
            count = 0;
            maximumCount = 0;
            modes = new ArrayList<>();
        }
    
        private void record(int value) {
            // 中序中相同值连续：相同就续段，不同就从 1 重新计数。
            if (previous != null && previous == value) {
                count++;
            } else {
                count = 1;
            }
            if (count > maximumCount) {
                maximumCount = count;
                // 出现更高频率，原来的候选都不再是众数。
                modes.clear();
                modes.add(value);
            // 达到相同最高频率，当前值也应列入答案。
            } else if (count == maximumCount) {
                modes.add(value);
            }
            previous = value;
        }
    
        private int[] toArray() {
            int[] result = new int[modes.size()];
            for (int i = 0; i < modes.size(); i++) {
                result[i] = modes.get(i);
            }
            return result;
        }
    
        public int[] findMode(TreeNode root) {
            // 入口重置状态；中序使相同值连续，record 负责统计。
            reset();
            Deque<TreeNode> stack = new ArrayDeque<>();
            TreeNode cur = root;
            while (cur != null || !stack.isEmpty()) {
                // 沿左链压栈，保存之后返回并处理中间节点的位置。
                while (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                }
                cur = stack.pop();
                record(cur.val);
                cur = cur.right;
            }
            return toArray();
        }
    }
}
