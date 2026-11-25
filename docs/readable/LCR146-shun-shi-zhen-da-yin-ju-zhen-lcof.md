# LCR146 · LCR 146. 螺旋遍历二维数组（原剑指 Offer 29） — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/LCR146-shun-shi-zhen-da-yin-ju-zhen-lcof/

数组 · Shun Shi Zhen Da Yin Ju Zhen Lcof

## 题意

输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。

**示例 1：**

```text
输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
输出：[1,2,3,6,9,8,7,4,5]
```

**示例 2：**

```text
输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
输出：[1,2,3,4,8,12,11,10,9,5,6,7]
```

**限制：**

- `0 <= matrix.length <= 100`

- `0 <= matrix[i].length <= 100`

注意：本题与主站 54 题相同：[https://leetcode.cn/problems/spiral-matrix/](<https://leetcode.cn/problems/spiral-matrix/>)

官方原题：https://leetcode.cn/problems/shun-shi-zhen-da-yin-ju-zhen-lcof/

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/lcof/%E9%9D%A2%E8%AF%95%E9%A2%9829.%20%E9%A1%BA%E6%97%B6%E9%92%88%E6%89%93%E5%8D%B0%E7%9F%A9%E9%98%B5/README.md

## 回放用例

输入：array = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]

预期结果：[1, 2, 3, 6, 9, 8, 7, 4, 5]

实际结果：[1, 2, 3, 6, 9, 8, 7, 4, 5]

测试来源：本次数组章节实例走过程；题目链接 https://leetcode.cn/problems/shun-shi-zhen-da-yin-ju-zhen-lcof/

## 状态含义

write是已经访问的元素数；top、bottom、left、right含义与54完全一致。

## 状态变化

复用54的边界收缩规则，改为把访问结果直接写进int[]。

## Java 解法

```java
import java.util.*;

class Solution {
    public int[] spiralArray(int[][] array) {
        if (array.length == 0 || array[0].length == 0) {
            return new int[0];
        }
        int[] result = new int[array.length * array[0].length];
        int write = 0;
        int top = 0;
        int bottom = array.length - 1;
        int left = 0;
        int right = array[0].length - 1;
        while (top <= bottom && left <= right) {
            for (int col = left; col <= right; col++) {
                result[write++] = array[top][col];
            }
            top++;
            for (int row = top; row <= bottom; row++) {
                result[write++] = array[row][right];
            }
            right--;
            if (top <= bottom) { // 防止只剩一行时重复读取
                for (int col = right; col >= left; col--) {
                    result[write++] = array[bottom][col];
                }
                bottom--;
            }
            if (left <= right) { // 防止只剩一列时重复读取
                for (int row = bottom; row >= top; row--) {
                    result[write++] = array[row][left];
                }
                left++;
            }
        }
        return result;
    }
}
```

## 来源与版本

Solution类体与数组笔记主解一致，只增加static、外层类和独立main。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LCR0146SpiralArray.java

源码指纹：67cca4f913d0aaefdbdb580e00a09aa2136a84c1562f744bd88275d495c77f66

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/SYdww8LbniCUBckZHRDcZ8QSnWb（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：47 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/LCR146-shun-shi-zhen-da-yin-ju-zhen-lcof/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
