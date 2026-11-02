package leetcode.hashtable;

import java.util.*;

public class LC00184Sum {

    public static void main(String[] args) {
        // 测试来源：飞书哈希表笔记的实例走过程。
        // https://acn581cm6pdd.feishu.cn/wiki/F4qJw6Pr2if4LkkJBjBc8aM3nXd
        int[] nums = new int[]{1,0,-1,0,-2,2};
        int target = 0;
        List<List<Integer>> expected = Arrays.asList(Arrays.asList(-2,-1,1,2),Arrays.asList(-2,0,0,2),Arrays.asList(-1,0,0,1));
        List<List<Integer>> actual = new Solution().fourSum(nums, target);
        System.out.println("Input: nums = [1, 0, -1, 0, -2, 2], target = 0");
        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + actual);
        boolean passed = actual.equals(expected);
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("结果与预期不同");
        }
    }

    static class Solution {
        public List<List<Integer>> fourSum(int[] nums, int target) {
            List<List<Integer>> result = new ArrayList<>();
            Arrays.sort(nums);
            for (int k = 0; k < nums.length - 3; k++) {
                if (k > 0 && nums[k] == nums[k - 1]) {
                    continue;
                }
                for (int i = k + 1; i < nums.length - 2; i++) {
                    // 只跳过当前 k 下面重复的第二个固定值。
                    if (i > k + 1 && nums[i] == nums[i - 1]) {
                        continue;
                    }
                    int left = i + 1;
                    int right = nums.length - 1;
                    while (left < right) {
                        long sum = (long) nums[k] + nums[i] + nums[left] + nums[right];
                        if (sum < target) {
                            left++;
                        } else if (sum > target) {
                            right--;
                        } else {
                            result.add(Arrays.asList(nums[k], nums[i], nums[left], nums[right]));
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
            }
            return result;
        }
    }
}
