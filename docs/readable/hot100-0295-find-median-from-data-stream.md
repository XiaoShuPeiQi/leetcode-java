# Hot100 第 76 题 · 力扣 295 · 数据流的中位数 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0295-find-median-from-data-stream/

Hot100 · 堆 · Find Median From Data Stream

## 题意

**中位数**是有序整数列表中的中间值。如果列表的大小是偶数，则没有中间值，中位数是两个中间值的平均值。

- 例如 `arr = [2,3,4]` 的中位数是 `3` 。

- 例如 `arr = [2,3]` 的中位数是 `(2 + 3) / 2 = 2.5` 。

实现 MedianFinder 类:

- `MedianFinder()` 初始化 `MedianFinder` 对象。

- `void addNum(int num)` 将数据流中的整数 `num` 添加到数据结构中。

- `double findMedian()` 返回到目前为止所有元素的中位数。与实际答案相差 `10^(-5)` 以内的答案将被接受。

**示例 1：**

```text
输入
["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
[[], [1], [2], [], [3], []]
输出
[null, null, null, 1.5, null, 2.0]

解释
MedianFinder medianFinder = new MedianFinder();
medianFinder.addNum(1);    // arr = [1]
medianFinder.addNum(2);    // arr = [1, 2]
medianFinder.findMedian(); // 返回 1.5 ((1 + 2) / 2)
medianFinder.addNum(3);    // arr[1, 2, 3]
medianFinder.findMedian(); // return 2.0
```

**提示:**

- `-10^5 <= num <= 10^5`

- 在调用 `findMedian` 之前，数据结构中至少有一个元素

- 最多 `5 * 10^4` 次调用 `addNum` 和 `findMedian`

官方原题：https://leetcode.cn/problems/find-median-from-data-stream

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0200-0299/0295.Find%20Median%20from%20Data%20Stream/README.md

## 回放用例

输入：依次 addNum(3)、addNum(1)、addNum(2)、addNum(5)，每次加入后查询中位数

预期结果：[3.0, 2.0, 2.0, 2.5]

实际结果：[3.0, 2.0, 2.0, 2.5]

测试来源：飞书 Hot100 第 76 题「实例走过程」

## 解法

用大顶堆存较小一半，小顶堆存较大一半，查询时只看两个堆顶。

## 状态含义

每次 addNum 结束时，maxHeap 中任一值都小于等于 minHeap 中任一值。maxHeap 的大小等于 minHeap 或比它多 1。数量为奇数时读 maxHeap.peek()，偶数时求两个堆顶的平均值。

## 状态变化

用大顶堆存较小一半，小顶堆存较大一半，查询时只看两个堆顶。

## Java 解法

```java
import java.util.PriorityQueue;

class MedianFinder {
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
```

## 来源与版本

代码随想录 · Java（补齐 import）。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/stackqueue/LC0295FindMedianFromDataStream.java

源码指纹：304296bf563c4465d332a9c7c228dbc7dc575526d1f337034c9282c62cc9a913

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnyDuM8LF8Ib6vZW8qBlFCLe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0295.find-median-from-data-stream.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：57 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0295-find-median-from-data-stream/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
