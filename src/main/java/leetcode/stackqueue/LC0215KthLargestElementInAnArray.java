package leetcode.stackqueue;

import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;
import common.TreeSupport;

public class LC0215KthLargestElementInAnArray {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "nums = [3, 2, 3, 1, 2, 4, 5, 5, 6], k = 4",
                4, runSolution());
    }

    // 测试来源：飞书 Hot100 第 74 题的实例走过程。
    static Object runSolution() {
        int[] nums = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        int k = 4;
        return new Solution().findKthLargest(nums, k);
    }

    static class Solution {
        public int findKthLargest(int[] nums, int k) {
            PriorityQueue<Integer> heap = new PriorityQueue<>();

            for (int num : nums) {
                heap.offer(num);

                if (heap.size() > k) {
                    heap.poll();
                }
            }

            return heap.peek();
        }
    }

    // 原随机三路快速选择实现。
    static class SolutionQuickselect {
        public int findKthLargest(int[] nums, int k) {
            int targetIndex = nums.length - k;
            int left = 0;
            int right = nums.length - 1;

            while (left < right) {
                int pivotIndex = ThreadLocalRandom.current().nextInt(left, right + 1);
                int pivot = nums[pivotIndex];
                int less = left;
                int scan = left;
                int greater = right;

                // 把候选区间分成小于、等于、大于 pivot 的三段。
                while (scan <= greater) {
                    if (nums[scan] < pivot) {
                        swap(nums, less, scan);
                        less++;
                        scan++;
                    } else if (nums[scan] > pivot) {
                        swap(nums, scan, greater);
                        greater--; // 换到 scan 的数还需要检查。
                    } else {
                        scan++;
                    }
                }

                if (targetIndex < less) {
                    right = less - 1;
                } else if (targetIndex > greater) {
                    left = greater + 1;
                } else {
                    return pivot;
                }
            }

            return nums[left];
        }

        private void swap(int[] nums, int first, int second) {
            int saved = nums[first];
            nums[first] = nums[second];
            nums[second] = saved;
        }
    }
}
