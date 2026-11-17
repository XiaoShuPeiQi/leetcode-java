# 904 · 水果成篮 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0904-fruit-into-baskets/

数组 · Fruit Into Baskets

## 题意

从一排果树中的某个位置开始连续摘果。两个篮子各自只能装一种水果，容量不限。求最多能摘多少棵树的水果。

官方原题：https://leetcode.cn/problems/fruit-into-baskets/

## 课堂示例

输入：fruits = [1, 2, 3, 2, 2]

预期结果：4

实际结果：4

测试来源：本次数组章节实例走过程；题目链接 https://leetcode.cn/problems/fruit-into-baskets/

## 状态含义

count记录窗口内各水果出现次数，count.size()才是种类数；result记录最长有效长度。

## 状态变化

找至多两种值的最长连续区间，超限才收缩。

## Java 解法

```java
import java.util.*;

class Solution {
    public int totalFruit(int[] fruits) {
        Map<Integer, Integer> count = new HashMap<>();
        int left = 0;
        int result = 0;
        for (int right = 0; right < fruits.length; right++) {
            int fruit = fruits[right];
            count.put(fruit, count.getOrDefault(fruit, 0) + 1);
            while (count.size() > 2) { // 超过两种才缩，缩到有效再记最长
                int removed = fruits[left];
                count.put(removed, count.get(removed) - 1);
                if (count.get(removed) == 0) {
                    count.remove(removed);
                }
                left++;
            }
            result = Math.max(result, right - left + 1);
        }
        return result;
    }
}
```

## 来源与版本

Solution类体与数组笔记主解一致，只增加static、外层类和独立main。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0904FruitIntoBaskets.java

源码指纹：0eb0364dede3ab00bc5af205badb788f1ce4dcfb4fc5dce41b4eeadbbb256c96

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/SYdww8LbniCUBckZHRDcZ8QSnWb（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：39 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0904-fruit-into-baskets/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
