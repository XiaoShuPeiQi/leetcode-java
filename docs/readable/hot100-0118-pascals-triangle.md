# Hot100 第 82 题 · 力扣 118 · 杨辉三角 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0118-pascals-triangle/

Hot100 · 动态规划 · Pascal's Triangle

## 题意

给定一个非负整数 `numRows`。

返回「杨辉三角」的前 `numRows` 行。

在**「杨辉三角」**中，每个数是它左上方和右上方的数的和。

**示例 1:**

```text
输入: numRows = 5
输出: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
```

**示例 2:**

```text
输入: numRows = 1
输出: [[1]]
```

**提示:**

- `1 <= numRows <= 30`

官方原题：https://leetcode.cn/problems/pascals-triangle/

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0100-0199/0118.Pascal%27s%20Triangle/README.md

## 回放用例

输入：numRows = 5

预期结果：[[1], [1, 1], [1, 2, 1], [1, 3, 3, 1], [1, 4, 6, 4, 1]]

实际结果：[[1], [1, 1], [1, 2, 1], [1, 3, 3, 1], [1, 4, 6, 4, 1]]

测试来源：力扣官方示例，与本节推演一致。

## 解法

逐行递推

## 状态含义

result 保存已完成的行，row 保存当前正在填的第 i 行。第 i 行下标是 0 到 i，中间的第 j 项取上一行的第 j-1 项和第 j 项之和。

## 状态变化

从上到下构造每一行，两端放 1，中间加上一行相邻的两个数。

## Java 解法

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    row.add(result.get(i - 1).get(j - 1) + result.get(i - 1).get(j));
                }
            }
            result.add(row);
        }
        return result;
    }
}
```

## 来源与版本

依据代码随想录的解法思路整理 Java 版。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0118PascalsTriangle.java

源码指纹：5fdaf1a06f0c3fc671845d97ab76d70ec966d607e5329b749c72dfa433cb0341

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnmy5ZXLCF7vmYm9gy8MrR7f（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0118.pascals-triangle.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：70 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0118-pascals-triangle/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
