# 406 · 根据身高重建队列 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0406-queue-reconstruction-by-height/

贪心 · Queue Reconstruction By Height

## 题意

假设有打乱顺序的一群人站成一个队列，数组 `people` 表示队列中一些人的属性（不一定按顺序）。每个 `people[i] = [h_i, k_i]` 表示第 `i` 个人的身高为 `h_i` ，前面 **正好** 有 `k_i`_ 个身高大于或等于 `h_i` 的人。

请你重新构造并返回输入数组 `people` 所表示的队列。返回的队列应该格式化为数组 `queue` ，其中 `queue[j] = [h_j, k_j]` 是队列中第 `j` 个人的属性（`queue[0]` 是排在队列前面的人）。

**示例 1：**

```text
输入：people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
输出：[[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
解释：
编号为 0 的人身高为 5 ，没有身高更高或者相同的人排在他前面。
编号为 1 的人身高为 7 ，没有身高更高或者相同的人排在他前面。
编号为 2 的人身高为 5 ，有 2 个身高更高或者相同的人排在他前面，即编号为 0 和 1 的人。
编号为 3 的人身高为 6 ，有 1 个身高更高或者相同的人排在他前面，即编号为 1 的人。
编号为 4 的人身高为 4 ，有 4 个身高更高或者相同的人排在他前面，即编号为 0、1、2、3 的人。
编号为 5 的人身高为 7 ，有 1 个身高更高或者相同的人排在他前面，即编号为 1 的人。
因此 [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]] 是重新构造后的队列。
```

**示例 2：**

```text
输入：people = [[6,0],[5,0],[4,0],[3,2],[2,2],[1,4]]
输出：[[4,0],[5,0],[2,2],[3,2],[1,4],[6,0]]
```

**提示：**

- `1 <= people.length <= 2000`

- `0 <= h_i <= 10^6`

- `0 <= k_i < people.length`

- 题目数据确保队列可以被重建

官方原题：https://leetcode.cn/problems/queue-reconstruction-by-height

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0400-0499/0406.Queue%20Reconstruction%20by%20Height/README.md

## 回放用例

输入：people = [[7, 0], [4, 4], [7, 1], [5, 0], [6, 1], [5, 2]]

预期结果：[[5, 0], [7, 0], [5, 2], [6, 1], [4, 4], [7, 1]]

实际结果：[[5, 0], [7, 0], [5, 2], [6, 1], [4, 4], [7, 1]]

测试来源：飞书贪心章节实例走过程（revision 87）

## Java 解法

```java
import java.util.*;

class Solution {
    public int[][] reconstructQueue(int[][] people) {
        people = people.clone();
        Arrays.sort(people, (a, b) -> a[0] == b[0]
                ? Integer.compare(a[1], b[1]) : Integer.compare(b[0], a[0]));
        List<int[]> result = new ArrayList<>();
        for (int[] p : people) {
            result.add(p[1], p.clone()); // 已插入的人都不比当前人矮
        }
        return result.toArray(new int[result.size()][]);
    }
}
```

## 来源与版本

录制代码与2026-10-14读取的飞书Solution类体一致，只增加static与外层运行适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/greedy/LC0406QueueReconstructionByHeight.java

源码指纹：3632124817a4b664107b91c039e66d61ecb2795b29da9b423c4ad72e2431868c

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：65 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0406-queue-reconstruction-by-height/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
