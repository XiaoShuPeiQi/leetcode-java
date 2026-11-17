# 55 · 跳跃游戏 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0055-jump-game/

贪心 · Jump Game

## 题意

从 nums 的第一个位置出发，每项给出当前位置允许向右跳的最大距离。判断能否到达最后一个位置。

官方原题：https://leetcode.cn/problems/jump-game/

## 课堂示例

输入：nums = [3, 2, 1, 0, 4]

预期结果：false

实际结果：false

测试来源：飞书贪心章节实例走过程（revision 87）

## Java 解法

```java
import java.util.*;

class Solution {
    public boolean canJump(int[] nums) {
        int cover = 0;
        for (int i = 0; i < nums.length && i <= cover; i++) {
            cover = Math.max(cover, i + nums[i]);
            if (cover >= nums.length - 1) return true;
        }
        return false;
    }
}
```

## 来源与版本

录制代码与2026-10-14读取的飞书Solution类体一致，只增加static与外层运行适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/greedy/LC0055JumpGame.java

源码指纹：1e7ffcbd5d2d0bd600aaf3293116f13104935116aaf3328e718859490fd95eaf

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：17 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0055-jump-game/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
