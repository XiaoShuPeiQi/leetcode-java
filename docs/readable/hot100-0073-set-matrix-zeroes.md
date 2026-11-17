# 73 · 矩阵置零 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0073-set-matrix-zeroes/

Hot100 · 矩阵 · Set Matrix Zeroes

## 题意

若矩阵某格原本为 0，就把它所在行与列都设为 0。要求原地修改。

官方原题：https://leetcode.cn/problems/set-matrix-zeroes/

## 课堂示例

输入：matrix=[[1,1,1],[1,0,1],[1,1,1]]

预期结果：[[1,0,1],[0,0,0],[1,0,1]]

实际结果：[[1,0,1],[0,0,0],[1,0,1]]

测试来源：力扣公开示例（已读取题目与Java模板）

## 状态含义

firstRow、firstCol保存原边界状态；matrix[i][0]、matrix[0][j]是内部区域的置零标记。

## 状态变化

借第一行和第一列做标记，最后再处理它们自身。

## Java 解法

```java
import java.util.*;

class Solution {
    public void setZeroes(int[][] matrix) {
        // 结果直接写回matrix，先标记原有的0，再统一清零
        int m = matrix.length;
        int n = matrix[0].length;
        boolean firstRow = false;
        boolean firstCol = false;
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {
                firstRow = true;
            }
        }
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                firstCol = true;
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) { // 标记阶段只写第一行、第一列
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        if (firstRow) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }
        if (firstCol) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }
}
```

## 来源与版本

学习扩展：总笔记未检出本题完整题解，按已有章节的方法组织新解法。 真实JDI回放对应的Solution类体，算法中未加入调试输出。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0073SetMatrixZeroes.java

源码指纹：ae524c043c0aa843c49ac2770fe14be5b65a42d9af0de7d176020e5af196d426

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：54 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0073-set-matrix-zeroes/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
