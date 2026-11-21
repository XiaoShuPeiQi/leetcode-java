# Hot100 第 72 题 · 力扣 739 · 每日温度 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0739-daily-temperatures/

Hot100 · 栈 · Daily Temperatures

## 题意

对于每一天，求距离下一次出现更高温度需要等待的天数。之后没有更高温度时填 0。

官方原题：https://leetcode.cn/problems/daily-temperatures/

## 课堂示例

输入：temperatures = [73, 74, 74, 71, 75]

预期结果：[1, 3, 2, 1, 0]

实际结果：[1, 3, 2, 1, 0]

测试来源：飞书 Hot100 第 72 题「实例走过程」

## 解法

用单调栈存尚未等到升温的日期，下一个更热的日期出现时结算等待天数。

## 状态含义

从栈底到栈顶，对应温度单调不增。整个过程只给弹出的下标写 answer[index] = i - index。扫描结束仍在栈里的日期没有等到更热的一天，保留 0。

## 状态变化

用单调栈存尚未等到升温的日期，下一个更热的日期出现时结算等待天数。

## Java 解法

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int[] answer = new int[temperatures.length];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < temperatures.length; i++) {
            // 当前温度为栈顶日期找到了右侧第一个更高温度
            while (!stack.isEmpty()
                    && temperatures[i] > temperatures[stack.peek()]) {
                int index = stack.pop();
                answer[index] = i - index;
            }
            stack.push(i);
        }

        return answer;
    }
}
```

## 来源与版本

代码随想录 · Java。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/stackqueue/LC0739DailyTemperatures.java

源码指纹：802f30e848971aba139f132fd39393e2fa2c1dc6b5b2a2588676dd609d26c315

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcniO7byvZWfSheSQ5RGYtOVd（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0739.daily-temperatures.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：43 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0739-daily-temperatures/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
