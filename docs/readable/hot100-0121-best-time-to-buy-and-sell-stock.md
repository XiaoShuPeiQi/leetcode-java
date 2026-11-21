# Hot100 第 77 题 · 力扣 121 · 买卖股票的最佳时机 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0121-best-time-to-buy-and-sell-stock/

Hot100 · 贪心算法 · Best Time To Buy And Sell Stock

## 题意

prices[i] 表示第 i 天的股价。最多做一次先买后卖的交易，求可得的最大利润。可以不交易。

官方原题：https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/

## 课堂示例

输入：prices = [7, 1, 5, 3, 6, 4]

预期结果：5

实际结果：5

测试来源：飞书 Hot100 第 77 题「实例走过程」

## 状态含义

每轮结束后，low 是已扫描前缀中最小的价格，result 是这个前缀内最多完成一次买卖的最大收益。只要收益为正，最低买价就一定来自今天之前。收益为 0 时可以不交易。

## 状态变化

把今天当成卖出日，记录此前的最低买价，并用今天的利润更新历史最好答案。

## Java 解法

```java
import java.util.*;

class Solution {
    public int maxProfit(int[] prices) {
        int low = Integer.MAX_VALUE, result = 0;
        for (int price : prices) {
            low = Math.min(low, price);
            result = Math.max(result, price - low);
        }
        return result;
    }
}
```

## 来源与版本

算法学习总笔记（Java）原码 · 最低买价版。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0121BestTimetoBuyandSellStock.java

源码指纹：1bacbf9987ea76a22c7a735ef749bfac7f247dc1945c8c0e79712c45cac60c2e

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnhgM7rmdN0deUQYgdK7Ifde（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：23 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0121-best-time-to-buy-and-sell-stock/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
