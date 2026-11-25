# Hot100 第 64 题 · 力扣 74 · 搜索二维矩阵 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0074-search-a-2d-matrix/

Hot100 · 二分查找 · Search A 2D Matrix

## 题意

给你一个满足下述两条属性的 `m x n` 整数矩阵：

- 每行中的整数从左到右按非严格递增顺序排列。

- 每行的第一个整数大于前一行的最后一个整数。

给你一个整数 `target` ，如果 `target` 在矩阵中，返回 `true` ；否则，返回 `false` 。

你必须编写一个时间复杂度为 `O(log(m * n))` 的解决方案。

**示例 1：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0074.Search%20a%202D%20Matrix/images/mat.jpg)

```text
输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
输出：true
```

**示例 2：**

![题目配图](https://raw.githubusercontent.com/doocs/leetcode/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0074.Search%20a%202D%20Matrix/images/mat2.jpg)

```text
输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
输出：false
```

**提示：**

- `m == matrix.length`

- `n == matrix[i].length`

- `1 <= m, n <= 100`

- `-10^4 <= matrix[i][j], target <= 10^4`

官方原题：https://leetcode.cn/problems/search-a-2d-matrix

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0074.Search%20a%202D%20Matrix/README.md

## 回放用例

输入：matrix = [[1, 3, 5], [7, 9, 11]], target = 9

预期结果：true

实际结果：true

测试来源：飞书 Hot100 第 64 题「实例走过程」

## 解法

把逐行连续有序的矩阵看作一条有序序列，对虚拟下标做二分。

## 状态含义

mid / cols 是跨过的完整行数，mid % cols 是在当前行内的偏移。比较 value 后用 mid ± 1 缩闭区间，命中返回 true，区间为空返回 false。

## 状态变化

把逐行连续有序的矩阵看作一条有序序列，对虚拟下标做二分。

## Java 解法

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int left = 0, right = rows * cols - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int row = mid / cols;
            int col = mid % cols;
            int value = matrix[row][col];

            if (value < target) {
                left = mid + 1;
            } else if (value > target) {
                right = mid - 1;
            } else {
                return true;
            }
        }
        return false;
    }
}
```

## 来源与版本

代码随想录 · Java。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0074SearchA2dMatrix.java

源码指纹：04054f50dae5351273e92b8911a405864018746d6f87ebae25b8cf0e6bf4e87c

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcn9QhV4ogmw8KcxLuxCWfbFc（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0074.search-a-2d-matrix.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：21 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0074-search-a-2d-matrix/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
