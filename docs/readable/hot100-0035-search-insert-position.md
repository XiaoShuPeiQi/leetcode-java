# Hot100 第 63 题 · 力扣 35 · 搜索插入位置 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0035-search-insert-position/

Hot100 · 二分查找 · Search Insert Position

## 题意

在无重复的升序数组中查找 target。如果不存在，返回插入后仍保持有序的下标。要求 O(log n) 时间。

官方原题：https://leetcode.cn/problems/search-insert-position/

## 课堂示例

输入：nums = [1, 3, 5, 6], target = 2

预期结果：1

实际结果：1

测试来源：飞书 Hot100 第 63 题「实例走过程」

## 状态含义

候选下标在闭区间 [left,right]。移走的左侧位置都小于目标，移走的右侧位置都大于目标。区间空时 left == right + 1，left 正好是两个区域的分界。

## 状态变化

有序且无重复，找到就返回下标，找不到时返回二分结束的 left。

## Java 解法

```java
class Solution {
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return left; // 未命中时，left就是插入位置
    }
}
```

## 来源与版本

算法学习总笔记（Java）原码。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0035SearchInsertPosition.java

源码指纹：78366b85bf2784c3d84a5f1cdc4f1ea3aa07480aa105391205a284ede62a5199

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcn8jBqcIEezbil5Ltru8Zjae（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：17 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0035-search-insert-position/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
