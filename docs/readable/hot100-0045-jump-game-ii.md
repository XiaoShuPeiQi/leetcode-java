# Hot100 第 79 题 · 力扣 45 · 跳跃游戏 II — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0045-jump-game-ii/

Hot100 · 贪心算法 · Jump Game Ii

## 题意

从数组首项出发，每个值限制当前位置最远可以向右跳多少格。返回到达末项所需的最少跳数；题目保证能够到达。

官方原题：https://leetcode.cn/problems/jump-game-ii/

## 课堂示例

输入：nums = [2, 3, 1, 1, 4]

预期结果：2

实际结果：2

测试来源：飞书 Hot100 第 79 题「实例走过程」

## 状态含义

遍历到 i 时先更新 nextEnd = max(nextEnd, i + nums[i])。只有 i == curEnd 才做 steps++ 和 curEnd = nextEnd。每次推进的是同样次数的所有跳法所能覆盖的最远边界，首次覆盖终点所需的层数就是最少跳数。

## 状态变化

扫完当前步数覆盖的这一层，再多跳一次，把边界扩到下一层的最远点。

## Java 解法

```java
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

算法学习总笔记（Java）原码。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/greedy/LC0045JumpGameII.java

源码指纹：62d6aeb80f7e1d7c4d54f9ca890a366d387c64911ac327663e61613624517936

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnkB2rwefxvUHu5C8cKCuRKb（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：23 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0045-jump-game-ii/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
