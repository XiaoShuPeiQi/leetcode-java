# 406 · 根据身高重建队列 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0406-queue-reconstruction-by-height/

贪心 · Queue Reconstruction By Height

## 题意

每个人用 [h, k] 表示，h 是身高，k 是队伍中排在他前面且身高至少为 h 的人数。重建满足这些条件的队列。

官方原题：https://leetcode.cn/problems/queue-reconstruction-by-height/

## 课堂示例

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
