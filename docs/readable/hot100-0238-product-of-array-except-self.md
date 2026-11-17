# 238 · 除了自身以外数组的乘积 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0238-product-of-array-except-self/

Hot100 · 普通数组 · Product of Array Except Self

## 题意

构造结果数组，其中第 i 项为原数组除 nums[i] 之外所有元素的乘积。要求 O(n) 时间，不能使用除法。

官方原题：https://leetcode.cn/problems/product-of-array-except-self/

## 课堂示例

输入：nums=[1,2,3,4]

预期结果：[24,12,8,6]

实际结果：[24,12,8,6]

测试来源：力扣公开示例（已读取题目与Java模板）

## 状态含义

leftProduct始终不含当前数；第二趟rightProduct也不含当前数；res数组先存左乘积，再变成左右乘积。

## 状态变化

答案等于左边所有数的乘积，再乘右边所有数的乘积。

## Java 解法

```java
import java.util.*;

class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length]; // res[i]最终保存除nums[i]外的全部元素乘积
        int leftProduct = 1;
        for (int i = 0; i < nums.length; i++) {
            res[i] = leftProduct; // 先写入左侧乘积，此时不包含nums[i]
            leftProduct *= nums[i];
        }

        int rightProduct = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            res[i] = res[i] * rightProduct; // 左侧乘积再乘右侧乘积
            rightProduct *= nums[i];
        }
        return res;
    }
}
```

## 来源与版本

学习扩展：总笔记未检出本题完整题解，按已有章节的方法组织新解法。 真实JDI回放对应的Solution类体，算法中未加入调试输出。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0238ProductOfArrayExceptSelf.java

源码指纹：f42246637598f88087d59f30c04043c6cd36f2dfb8e38b25a460b8dd0e4115d1

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：32 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0238-product-of-array-except-self/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
