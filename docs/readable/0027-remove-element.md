# 27 · 移除元素 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0027-remove-element/

数组 · Remove Element

## 题意

在 nums 中原地移除所有等于 val 的元素。返回剩余个数 k，把保留元素放在前 k 项，顺序任意。

官方原题：https://leetcode.cn/problems/remove-element/

## 课堂示例

输入：nums = [3, 2, 2, 3], val = 3

预期结果：[2, 2]

实际结果：[2, 2]

测试来源：本次数组章节实例走过程；题目链接 https://leetcode.cn/problems/remove-element/

## 状态含义

[0,slow)是已保留的有效前缀；fast是当前读下标；[slow,fast)不再关心。

## 状态变化

把要保留的元素依次写到前面，数组长度不变，只返回有效长度。

## Java 解法

```java
import java.util.*;

class Solution {
    public int removeElement(int[] nums, int val) {
        int slow = 0; // 下次写入位置，也是有效长度
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        return slow;
    }
}
```

## 来源与版本

Solution类体与数组笔记主解一致，只增加static、外层类和独立main。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0027RemoveElement.java

源码指纹：2c12c7e07084a2b26538bc4f285de51774a12aa7f38fb77f35833bf76f6ec203

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/SYdww8LbniCUBckZHRDcZ8QSnWb（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：17 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0027-remove-element/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
