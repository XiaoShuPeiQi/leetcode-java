# 844 · 比较含退格的字符串 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0844-backspace-string-compare/

数组 · Backspace String Compare

## 题意

分别处理两个字符串中的 #，每个 # 删除此前仍然保留的最后一个字符，空串时忽略。判断处理后的两个字符串是否相同。

官方原题：https://leetcode.cn/problems/backspace-string-compare/

## 课堂示例

输入：s = "ab#c", t = "ad#c"

预期结果：true

实际结果：true

测试来源：本次数组章节实例走过程；题目链接 https://leetcode.cn/problems/backspace-string-compare/

## 状态含义

i、j是倒序读位置；skipS、skipT是各自尚未抵消的退格次数。

## 状态变化

从右往左统计待删除字符，跳过后再比较真正留下的字符。

## Java 解法

```java
import java.util.*;

class Solution {
    public boolean backspaceCompare(String s, String t) {
        int i = s.length() - 1;
        int j = t.length() - 1;
        int skipS = 0;
        int skipT = 0;
        while (i >= 0 || j >= 0) {
            while (i >= 0) { // 找s中最后一个未被退格删除的字符
                if (s.charAt(i) == '#') {
                    skipS++;
                    i--;
                } else if (skipS > 0) {
                    skipS--;
                    i--;
                } else {
                    break;
                }
            }
            while (j >= 0) { // t独立计算自己的退格次数
                if (t.charAt(j) == '#') {
                    skipT++;
                    j--;
                } else if (skipT > 0) {
                    skipT--;
                    j--;
                } else {
                    break;
                }
            }
            if (i < 0 || j < 0) {
                return i == j; // 都耗尽才相等
            }
            if (s.charAt(i) != t.charAt(j)) {
                return false;
            }
            i--;
            j--;
        }
        return true;
    }
}
```

## 来源与版本

Solution类体与数组笔记主解一致，只增加static、外层类和独立main。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0844BackspaceStringCompare.java

源码指纹：aa8d7e29dd570f5b491cb2d39e9d1c5bf8275d9dbbd9b3f530dad5684c9a9efe

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/SYdww8LbniCUBckZHRDcZ8QSnWb（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：48 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0844-backspace-string-compare/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
