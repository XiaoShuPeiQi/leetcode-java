# 45 · 跳跃游戏 II — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0045-jump-game-ii/

贪心 · Jump Game II

## 题意

从数组首项出发，每个值限制当前位置最远可以向右跳多少格。返回到达末项所需的最少跳数；题目保证能够到达。

官方原题：https://leetcode.cn/problems/jump-game-ii/

## 课堂示例

输入：nums = [2, 3, 1, 1, 4]

预期结果：2

实际结果：2

测试来源：飞书贪心章节实例走过程（revision 87）

## Java 解法

```java
import java.util.*;

class Solution {
    public int jump(int[] nums) {
        int curEnd = 0;
        int nextEnd = 0;
        int steps = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            nextEnd = Math.max(nextEnd, i + nums[i]);
            if (i == curEnd) {
                steps++; // 扫完一层，才增加一步
                curEnd = nextEnd;
            }
        }
        return steps;
    }
}
```

## 来源与版本

录制代码与2026-10-14读取的飞书Solution类体一致，只增加static与外层运行适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/greedy/LC0045JumpGameII.java

源码指纹：62d6aeb80f7e1d7c4d54f9ca890a366d387c64911ac327663e61613624517936

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：23 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0045-jump-game-ii/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
