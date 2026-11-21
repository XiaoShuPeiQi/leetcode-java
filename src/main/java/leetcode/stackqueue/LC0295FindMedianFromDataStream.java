package leetcode.stackqueue;

import java.util.PriorityQueue;
import common.TreeSupport;

public class LC0295FindMedianFromDataStream {
    public static void main(String[] args) {
        TreeSupport.check("MedianFinder", "依次 addNum(3)、addNum(1)、addNum(2)、addNum(5)，每次加入后查询中位数",
                new double[]{3.0, 2.0, 2.0, 2.5}, runSolution());
    }

    // 测试来源：飞书 Hot100 第 76 题的实例走过程。
    static Object runSolution() {
        MedianFinder finder = new MedianFinder();
        double[] result = new double[4];
        finder.addNum(3);
        result[0] = finder.findMedian();
        finder.addNum(1);
        result[1] = finder.findMedian();
        finder.addNum(2);
        result[2] = finder.findMedian();
        finder.addNum(5);
        result[3] = finder.findMedian();
        return result;
    }

    static class MedianFinder {
        private PriorityQueue<Integer> maxHeap; // 大顶堆
        private PriorityQueue<Integer> minHeap; // 小顶堆

        public MedianFinder() {
            maxHeap = new PriorityQueue<>((a, b) -> b - a);
            minHeap = new PriorityQueue<>();
        }

        public void addNum(int num) {
            maxHeap.offer(num);
            minHeap.offer(maxHeap.poll());

            if (minHeap.size() > maxHeap.size()) {
                maxHeap.offer(minHeap.poll());
            }
        }

        public double findMedian() {
            if (maxHeap.size() > minHeap.size()) {
                return maxHeap.peek();
            } else {
                return (maxHeap.peek() + minHeap.peek()) / 2.0;
            }
        }
    }
}
