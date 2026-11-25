# 349 · 两个数组的交集 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0349-intersection-of-two-arrays/

哈希表 · Intersection of Two Arrays

## 题意

给定两个数组 `nums1` 和 `nums2` ，返回 *它们的 交集* 。输出结果中的每个元素一定是 **唯一** 的。我们可以 **不考虑输出结果的顺序** 。

**示例 1：**

```text
输入：nums1 = [1,2,2,1], nums2 = [2,2]
输出：[2]
```

**示例 2：**

```text
输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
输出：[9,4]
解释：[4,9] 也是可通过的
```

**提示：**

- `1 <= nums1.length, nums2.length <= 1000`

- `0 <= nums1[i], nums2[i] <= 1000`

官方原题：https://leetcode.cn/problems/intersection-of-two-arrays

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0300-0399/0349.Intersection%20of%20Two%20Arrays/README.md

## 回放用例

输入：nums1 = [1, 2, 2, 1], nums2 = [2, 2]

预期结果：[2]

实际结果：[2]

测试来源：与飞书本题“实例走过程”使用相同输入。

## 状态含义

主解用 HashSet；题设的数值范围0～1000也允许数组标记，后面给出这一版。先根据要保存的信息选择结构，再比较范围带来的优化空间。

## 状态变化

这道题本质是：查询另一个数组中有没有这个数，并让答案中的数值只出现一次。

## Java 解法

```java
import java.util.*;

class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> seen = new HashSet<>();
        Set<Integer> result = new HashSet<>();
        for (int value : nums1) {
            seen.add(value);
        }
        for (int value : nums2) {
            // seen 负责查找，result 负责答案去重。
            if (seen.contains(value)) {
                result.add(value);
            }
        }

        int[] answer = new int[result.size()];
        int index = 0;
        for (int value : result) {
            answer[index++] = value;
        }
        return answer;
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/hashtable/LC0349IntersectionofTwoArrays.java

源码指纹：9529a69349b3c75634477fcd5e4fe59d4ff0143f65975a84b1e0aaa8ee54eac5

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/F4qJw6Pr2if4LkkJBjBc8aM3nXd（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：26 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0349-intersection-of-two-arrays/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
