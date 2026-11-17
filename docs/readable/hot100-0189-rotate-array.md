# 189 · 轮转数组 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0189-rotate-array/

Hot100 · 普通数组 · Rotate Array

## 题意

把 nums 原地向右轮转 k 个位置。跨过数组末尾的元素从开头继续。

官方原题：https://leetcode.cn/problems/rotate-array/

## 课堂示例

输入：nums=[1,2,3,4,5,6,7]; k=3

预期结果：[5,6,7,1,2,3,4]

实际结果：[5,6,7,1,2,3,4]

测试来源：力扣公开示例（已读取题目与Java模板）

## 状态含义

k是归一化后的右移量；reverse处理闭区间[left,right]，交换后两端向内收缩。

## 状态变化

整体反转，再分别反转前k个与剩下部分。

## Java 解法

```java
import java.util.*;

class Solution {
    public void rotate(int[] nums, int k) {
        // 结果直接写回nums，三次反转完成右移
        k %= nums.length;
        // 整体反转后分别恢复两段内部顺序，AB变成BA
        reverse(nums, 0, nums.length - 1); // 先反转整个数组
        reverse(nums, 0, k - 1); // 恢复前k个元素的顺序
        reverse(nums, k, nums.length - 1); // 恢复剩余元素的顺序
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
}
```

## 来源与版本

学习扩展：总笔记未检出本题完整题解，按已有章节的方法组织新解法。 真实JDI回放对应的Solution类体，算法中未加入调试输出。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0189RotateArray.java

源码指纹：4882f2644aab59cb764d938104c80a022aca71ea2b88629659d131d6830159c8

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：61 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0189-rotate-array/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
