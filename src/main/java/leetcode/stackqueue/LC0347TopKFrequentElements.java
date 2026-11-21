package leetcode.stackqueue;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import common.TreeSupport;

public class LC0347TopKFrequentElements {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "nums = [1, 1, 1, 2, 2, 3], k = 2",
                new int[]{1, 2}, runSolution());
    }

    // 测试来源：飞书 Hot100 第 75 题的实例走过程。
    static Object runSolution() {
        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = 2;
        return new Solution().topKFrequent(nums, k);
    }

    static class Solution {
        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> count = new HashMap<>();

            for (int num : nums) {
                count.put(num, count.getOrDefault(num, 0) + 1);
            }

            PriorityQueue<int[]> minHeap =
                    new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));

            for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
                minHeap.offer(new int[]{entry.getKey(), entry.getValue()});

                // 人数超额时，淘汰当前候选里频率最低的。
                if (minHeap.size() > k) {
                    minHeap.poll();
                }
            }

            int[] result = new int[k];

            for (int i = k - 1; i >= 0; i--) {
                // 堆弹出的是数值与频次；答案只要数值。
                result[i] = minHeap.poll()[0];
            }

            return result;
        }
    }
}
