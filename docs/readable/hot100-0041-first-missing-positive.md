# 41 · 缺失的第一个正数 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0041-first-missing-positive/

Hot100 · 普通数组 · First Missing Positive

## 题意

求 nums 中缺失的最小正整数。要求 O(n) 时间和 O(1) 额外空间。

官方原题：https://leetcode.cn/problems/first-missing-positive/

## 课堂示例

输入：nums=[3,4,-1,1]

预期结果：2

实际结果：2

测试来源：力扣公开示例（已读取题目与Java模板）

## 状态含义

i是当前归位位置；target=nums[i]-1是该值应去的位置；只处理[1,n]内且尚未就位的值。 res先设为n+1，扫描到第一个错位位置时更新res并结束检查，最后统一返回。

## 状态变化

把值x放到下标x-1，第一次错位的位置就是缺失正数。

## Java 解法

```java
import java.util.*;

class Solution {
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        int res = n + 1; // 1到n都存在时，答案就是n+1

        for (int i = 0; i < n; i++) {

            //一定注意这里是while，直至这个位置正确
            while (nums[i] >= 1 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                int target = nums[i] - 1; // 值x应该放到下标x-1；重复值已就位时停止
                int temp = nums[target];
                nums[target] = nums[i];
                nums[i] = temp;
            }
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                res = i + 1;
                break; // 第一个错位的位置，就是最小缺失正数
            }
        }
        return res;
    }
}
```

## 来源与版本

学习扩展：总笔记未检出本题完整题解，按已有章节的方法组织新解法。 真实JDI回放对应的Solution类体，算法中未加入调试输出。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0041FirstMissingPositive.java

源码指纹：95940ba6d7bfe6f6900a2b77c1c27528b275cd7c4d8758554b9c066ad2df9aeb

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：38 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0041-first-missing-positive/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
