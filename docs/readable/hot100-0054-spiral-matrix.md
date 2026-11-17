# 54 · 螺旋矩阵 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0054-spiral-matrix/

Hot100 · 矩阵 · Spiral Matrix

## 题意

从矩阵左上角出发，先向右，再按顺时针方向逐层向内遍历，返回读到的所有元素。

官方原题：https://leetcode.cn/problems/spiral-matrix/

## 课堂示例

输入：matrix=[[1,2,3],[4,5,6],[7,8,9]]

预期结果：[1, 2, 3, 6, 9, 8, 7, 4, 5]

实际结果：[1, 2, 3, 6, 9, 8, 7, 4, 5]

测试来源：总笔记同题实例

## 状态含义

top、bottom、left、right都是闭区间边界；res依次保存已经访问的元素。

## 状态变化

按上、右、下、左读一圈，每读完一条边就收缩对应边界。

## Java 解法

```java
import java.util.*;

class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>(); // 按螺旋访问顺序依次加入元素
        if (matrix.length == 0 || matrix[0].length == 0) {
            return res;
        }
        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;
        while (top <= bottom && left <= right) {
            for (int col = left; col <= right; col++) {
                res.add(matrix[top][col]);
            }
            top++;
            for (int row = top; row <= bottom; row++) {
                res.add(matrix[row][right]);
            }
            right--;
            if (top <= bottom) { // 仍有未读行，才走下边
                for (int col = right; col >= left; col--) {
                    res.add(matrix[bottom][col]);
                }
                bottom--;
            }
            if (left <= right) { // 仍有未读列，才走左边
                for (int row = bottom; row >= top; row--) {
                    res.add(matrix[row][left]);
                }
                left++;
            }
        }
        return res;
    }
}
```

## 来源与版本

沿用总笔记中本题的算法思路，代码按结果先定义、逐步填入的方式展开。 真实JDI回放对应的Solution类体，算法中未加入调试输出。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0054SpiralMatrix.java

源码指纹：4637a7a7f267914dc6cd8d730abcc092648628f0291f2b8565164ee6da808a5d

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：46 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0054-spiral-matrix/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
