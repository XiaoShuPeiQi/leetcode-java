# 860 · 柠檬水找零 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0860-lemonade-change/

贪心 · Lemonade Change

## 题意

柠檬水售价 5 元，顾客依次用 5、10 或 20 元付款。开始时没有零钱，判断能否给每位顾客正确找零。

官方原题：https://leetcode.cn/problems/lemonade-change/

## 课堂示例

输入：bills = [5, 5, 5, 10, 20]

预期结果：true

实际结果：true

测试来源：飞书贪心章节实例走过程（revision 87）

## Java 解法

```java
import java.util.*;

class Solution {
    public boolean lemonadeChange(int[] bills) {
        int five = 0;
        int ten = 0;
        for (int bill : bills) {
            if (bill == 5) {
                five++;
            } else if (bill == 10) {
                if (five == 0) return false;
                five--;
                ten++;
            } else {
                if (ten > 0 && five > 0) {
                    ten--;
                    five--;
                } else if (five >= 3) {
                    five -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
```

## 来源与版本

录制代码与2026-10-14读取的飞书Solution类体一致，只增加static与外层运行适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/greedy/LC0860LemonadeChange.java

源码指纹：3fedee11e2b3060cf7f32548b8cae17d7d240a947ffd4649ab3a088462424f90

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：27 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0860-lemonade-change/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
