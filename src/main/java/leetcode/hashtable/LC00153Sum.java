package leetcode.hashtable;

import java.util.*;

public class LC00153Sum {

    public static void main(String[] args) {
        // 测试来源：飞书哈希表笔记的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/F4qJw6Pr2if4LkkJBjBc8aM3nXd
        int[] nums = new int[]{-1,0,1,2,-1,-4};
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(-1,-1,2),Arrays.asList(-1,0,1));
        List<List<Integer>> actual = new Solution().threeSum(nums);
        System.out.println("Input: nums = [-1, 0, 1, 2, -1, -4]");
        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + actual);
        boolean passed = actual.equals(expected);
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("结果与预期不同");
        }
    }

    static class Solution {
        public List<List<Integer>> threeSum(int[] nums) {
            List<List<Integer>> result = new ArrayList<>();
            Arrays.sort(nums);
            for (int i = 0; i < nums.length - 2; i++) {
                if (nums[i] > 0) {
                    break;
                }
                // 同一层固定值只处理一次，仍允许一组中有相同数值。
                if (i > 0 && nums[i] == nums[i - 1]) {
                    continue;
                }

                int left = i + 1;
                int right = nums.length - 1;
                while (left < right) {
                    long sum = (long) nums[i] + nums[left] + nums[right];
                    if (sum < 0) {
                        left++;
                    } else if (sum > 0) {
                        right--;
                    } else {
                        result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                        // 先保存，再跨过相同的左右值。
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        left++;
                        right--;
                    }
                }
            }
            return result;
        }
    }
}
