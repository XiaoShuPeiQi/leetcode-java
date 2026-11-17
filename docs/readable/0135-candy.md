# 135 · 分发糖果 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0135-candy/

贪心 · Candy

## 题意

给排成一排的孩子分糖。每人至少一颗；相邻时评分更高的孩子要拿更多。求最少需要多少颗糖。

官方原题：https://leetcode.cn/problems/candy/

## 课堂示例

输入：ratings = [1, 0, 2]

预期结果：5

实际结果：5

测试来源：飞书贪心章节实例走过程（revision 87）

## Java 解法

```java
import java.util.*;

class Solution {
    public int candy(int[] ratings) {
        int[] candy = new int[ratings.length];
        Arrays.fill(candy, 1);
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) candy[i] = candy[i - 1] + 1;
        }
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candy[i] = Math.max(candy[i], candy[i + 1] + 1);
            }
        }
        int result = 0;
        for (int count : candy) result += count;
        return result;
    }
}
```

## 来源与版本

录制代码与2026-10-14读取的飞书Solution类体一致，只增加static与外层运行适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/greedy/LC0135Candy.java

源码指纹：81ab02c321c889d0721824f65744bbadcbbf695cc941380f7d54ba8497b4eb15

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：18 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0135-candy/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
