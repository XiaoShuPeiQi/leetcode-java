package leetcode.binarytree;

import java.util.*;
import common.TreeSupport;
import common.TreeNode;


public class LC0437PathSumIii {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "root=[10,5,-3,3,2,null,11,3,-2,null,1], targetSum=8",
                "3", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        TreeNode root = TreeSupport.fromLevelOrder(10,5,-3,3,2,null,11,3,-2,null,1);
        return new Solution().pathSum(root, 8);
    }

    static class Solution {
        public int pathSum(TreeNode root, int targetSum) {
            Map<Long, Integer> count = new HashMap<>();
            count.put(0L, 1);
            return traversal(root, targetSum, 0L, count);
        }

        private int traversal(TreeNode node, int targetSum, long sum, Map<Long, Integer> count) {
            if (node == null) {
                return 0;
            }
            sum += node.val;
            int res = count.getOrDefault(sum - targetSum, 0);
            count.put(sum, count.getOrDefault(sum, 0) + 1);
            res += traversal(node.left, targetSum, sum, count);
            res += traversal(node.right, targetSum, sum, count);
            // 离开当前分支，撤销这一层的累计和。
            int frequency = count.get(sum);
            if (frequency == 1) {
                count.remove(sum);
            } else {
                count.put(sum, frequency - 1);
            }
            return res;
        }
    }
}
