# Hot100 第 75 题 · 力扣 347 · 前 K 个高频元素 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0347-top-k-frequent-elements/

Hot100 · 堆 · Top K Frequent Elements

## 题意

给你一个整数数组 `nums` 和一个整数 `k` ，请你返回其中出现频率前 `k` 高的元素。你可以按 **任意顺序** 返回答案。

**示例 1：**

**输入：**nums = [1,1,1,2,2,3], k = 2

**输出：**[1,2]

**示例 2：**

**输入：**nums = [1], k = 1

**输出：**[1]

**示例 3：**

**输入：**nums = [1,2,1,2,1,2,3,1,3,2], k = 2

**输出：**[1,2]

**提示：**

- `1 <= nums.length <= 10^5`

- `-10^4 <= nums[i] <= 10^4`

- `k` 的取值范围是 `[1, 数组中不相同的元素的个数]`

- 题目数据保证答案唯一，换句话说，数组中前 `k` 个高频元素的集合是唯一的

**进阶：**你所设计算法的时间复杂度 **必须** 优于 `O(n log n)` ，其中 `n` 是数组大小。

官方原题：https://leetcode.cn/problems/top-k-frequent-elements

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0300-0399/0347.Top%20K%20Frequent%20Elements/README.md

## 回放用例

输入：nums = [1, 1, 1, 2, 2, 3], k = 2

预期结果：[1, 2]

实际结果：[1, 2]

测试来源：飞书 Hot100 第 75 题「实例走过程」

## 状态含义

处理完一种数值之后，堆中保存目前的前 k 高频候选，堆顶是候选里频次最低的。遍历完频次表后，从结果数组的尾部往前填入弹出的数值，最后返回 result。

## 状态变化

先用哈希表统计频次，再用小顶堆留下频次最高的 k 个数。

## Java 解法

```java
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class Solution {
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
```

## 来源与版本

算法学习总笔记（Java）原码 · 小顶堆版。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/stackqueue/LC0347TopKFrequentElements.java

源码指纹：99b13447fd8075d63864cefe8637cf8407db7e2e24757a7a8bbb86c62d9ae035

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcn6y6BCRpg4kLgKJjzmBup2g（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：44 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0347-top-k-frequent-elements/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
