# Hot100 第 78 题 · 力扣 55 · 跳跃游戏 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0055-jump-game/

Hot100 · 贪心算法 · Jump Game

## 题意

给你一个非负整数数组 `nums` ，你最初位于数组的 **第一个下标** 。数组中的每个元素代表你在该位置可以跳跃的最大长度。

判断你是否能够到达最后一个下标，如果可以，返回 `true` ；否则，返回 `false` 。

**示例 1：**

```text
输入：nums = [2,3,1,1,4]
输出：true
解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
```

**示例 2：**

```text
输入：nums = [3,2,1,0,4]
输出：false
解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。
```

**提示：**

- `1 <= nums.length <= 10^4`

- `0 <= nums[i] <= 10^5`

官方原题：https://leetcode.cn/problems/jump-game

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0055.Jump%20Game/README.md

## 回放用例

输入：nums = [3, 2, 1, 0, 4]

预期结果：false

实际结果：false

测试来源：飞书 Hot100 第 78 题「实例走过程」

## 状态含义

[0,cover] 内的下标都可达。只有 i <= cover 才能用该位置的跳跃能力。当 cover >= nums.length - 1 时返回 true，扫完可达范围仍未覆盖终点时返回 false。

## 状态变化

站在可达的下标上，持续扩大最远能到的位置 cover。

## Java 解法

```java
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

算法学习总笔记（Java）原码。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/greedy/LC0055JumpGame.java

源码指纹：1e7ffcbd5d2d0bd600aaf3293116f13104935116aaf3328e718859490fd95eaf

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcngMatBGncaX5XuWsOLzYxKc（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：17 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0055-jump-game/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
