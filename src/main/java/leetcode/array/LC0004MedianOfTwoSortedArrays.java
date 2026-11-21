package leetcode.array;

import common.TreeSupport;

public class LC0004MedianOfTwoSortedArrays {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "nums1 = [1, 3], nums2 = [2, 4]",
                2.5, runSolution());
    }

    // 测试来源：飞书 Hot100 第 68 题的实例走过程。
    static Object runSolution() {
        int[] nums1 = {1, 3};
        int[] nums2 = {2, 4};
        return new Solution().findMedianSortedArrays(nums1, nums2);
    }

    static class Solution {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int m = nums1.length, n = nums2.length;
            int total = m + n;

            if (total % 2 == 1) {
                return findKth(nums1, 0, nums2, 0, total / 2 + 1);
            } else {
                int left = findKth(nums1, 0, nums2, 0, total / 2);
                int right = findKth(nums1, 0, nums2, 0, total / 2 + 1);
                return (left + right) / 2.0;
            }
        }

        private int findKth(int[] nums1, int start1, int[] nums2, int start2, int k) {
            if (start1 >= nums1.length) {
                return nums2[start2 + k - 1];
            }
            if (start2 >= nums2.length) {
                return nums1[start1 + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[start1], nums2[start2]);
            }

            int half = k / 2;
            int mid1 = start1 + half - 1 < nums1.length ? nums1[start1 + half - 1] : Integer.MAX_VALUE;
            int mid2 = start2 + half - 1 < nums2.length ? nums2[start2 + half - 1] : Integer.MAX_VALUE;

            if (mid1 < mid2) {
                return findKth(nums1, start1 + half, nums2, start2, k - half);
            } else {
                return findKth(nums1, start1, nums2, start2 + half, k - half);
            }
        }
    }
}
