# Hot100 第 71 题 · 力扣 394 · 字符串解码 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0394-decode-string/

Hot100 · 栈 · Decode String

## 题意

按 k[片段] 表示重复 k 次的规则解码给定字符串。编码可以嵌套，重复次数可以有多位。

官方原题：https://leetcode.cn/problems/decode-string/

## 课堂示例

输入：s = "3[a2[c]]"

预期结果：accaccacc

实际结果：accaccacc

测试来源：飞书 Hot100 第 71 题「实例走过程」

## 解法

遇到左方括号保存外层前缀和重复次数，遇到右方括号就解开当前层并拼回外层。

## 状态含义

两个栈的对应位置属于同一层。左括号之后要清零 num 并新建当前层缓冲区。右括号用 temp 固定这一层要重复的字符串，再构造新的 curStr。扫描完返回 curStr.toString()。

## 状态变化

遇到左方括号保存外层前缀和重复次数，遇到右方括号就解开当前层并拼回外层。

## Java 解法

```java
import java.util.Stack;

class Solution {
    public String decodeString(String s) {
        Stack<Integer> numStack = new Stack<>();    // 数字栈
        Stack<String> strStack = new Stack<>();     // 字符串栈
        StringBuilder curStr = new StringBuilder(); // 当前累积的字符串
        int num = 0;                                // 当前数字

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                // 累积数字（可能是多位数）
                num = num * 10 + (c - '0');
            } else if (c == '[') {
                // 遇到 '[', 入栈，开始新的一层
                numStack.push(num);
                strStack.push(curStr.toString());
                num = 0;
                curStr = new StringBuilder();
            } else if (c == ']') {
                // 遇到 ']', 出栈，解码当前层
                int repeatTimes = numStack.pop();
                String prevStr = strStack.pop();

                // 当前字符串重复 repeatTimes 次，拼接到上一层
                String temp = curStr.toString();
                curStr = new StringBuilder(prevStr);
                for (int i = 0; i < repeatTimes; i++) {
                    curStr.append(temp);
                }
            } else {
                // 字母，累积到当前字符串
                curStr.append(c);
            }
        }

        return curStr.toString();
    }
}
```

## 来源与版本

代码随想录 · Java（补齐 import）。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/stackqueue/LC0394DecodeString.java

源码指纹：cd261dfa432085d7fe089dbc0420279a0e39457a51a76a8c991d976783aa5c17

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnaBJgYOjCkYPxcxNTnncOyf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0394.decode-string.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：68 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0394-decode-string/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
