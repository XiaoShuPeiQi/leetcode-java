# 48 · 旋转图像 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0048-rotate-image/

Hot100 · 矩阵 · Rotate Image

## 题意

把 n×n 矩阵顺时针旋转 90 度。直接修改输入矩阵，不能另开矩阵存放旋转结果。

官方原题：https://leetcode.cn/problems/rotate-image/

## 课堂示例

输入：matrix=[[1,2,3],[4,5,6],[7,8,9]]

预期结果：[[7,4,1],[8,5,2],[9,6,3]]

实际结果：[[7,4,1],[8,5,2],[9,6,3]]

测试来源：力扣公开示例（已读取题目与Java模板）

## 状态含义

i、j是转置位置且j>i；row是待反转行；left、right围住本行尚未反转部分。

## 状态变化

先沿主对角线转置，再左右反转每一行。

## Java 解法

```java
import java.util.*;

class Solution {
    public void rotate(int[][] matrix) {
        // 结果直接写回matrix：先转置，再左右反转每一行
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) { // 每对对称位置只交换一次
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        // 转置完成后，逐行交换左右两端
        for (int row = 0; row < n; row++) {
            int left = 0;
            int right = n - 1;
            while (left < right) {
                int temp = matrix[row][left];
                matrix[row][left] = matrix[row][right];
                matrix[row][right] = temp;
                left++;
                right--;
            }
        }
    }
}
```

## 来源与版本

学习扩展：总笔记未检出本题完整题解，按已有章节的方法组织新解法。 真实JDI回放对应的Solution类体，算法中未加入调试输出。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0048RotateImage.java

源码指纹：b5142354b354d7f0c98791abcb314f4baa8ab43583634a5b976fbe9b3c55ac6c

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：57 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0048-rotate-image/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
