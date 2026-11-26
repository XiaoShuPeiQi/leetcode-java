# Hot100 第 71 题 · 力扣 394 · 字符串解码 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0394-decode-string/

Hot100 · 栈 · Decode String

## 题意

给定一个经过编码的字符串，返回它解码后的字符串。

编码规则为: `k[encoded_string]`，表示其中方括号内部的 `encoded_string` 正好重复 `k` 次。注意 `k` 保证为正整数。

你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。

此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 `k` ，例如不会出现像 `3a` 或 `2[4]` 的输入。

测试用例保证输出的长度不会超过 `10^5`。

**示例 1：**

```text
输入：s = "3[a]2[bc]"
输出："aaabcbc"
```

**示例 2：**

```text
输入：s = "3[a2[c]]"
输出："accaccacc"
```

**示例 3：**

```text
输入：s = "2[abc]3[cd]ef"
输出："abcabccdcdcdef"
```

**示例 4：**

```text
输入：s = "abc3[cd]xyz"
输出："abccdcdcdxyz"
```

**提示：**

- `1 <= s.length <= 30`

- `s` 由小写英文字母、数字和方括号 `'[]'` 组成

- `s` 保证是一个 **有效** 的输入。

- `s` 中所有整数的取值范围为 `[1, 300]`

官方原题：https://leetcode.cn/problems/decode-string

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0300-0399/0394.Decode%20String/README.md

## 回放用例

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
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public String decodeString(String s) {
        Deque<Integer> numStack = new ArrayDeque<>();    // 数字栈
        Deque<String> strStack = new ArrayDeque<>();     // 字符串栈
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

源自代码随想录 Java 解法，飞书笔记将两个栈统一为 Deque 和 ArrayDeque。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/stackqueue/LC0394DecodeString.java

源码指纹：eb07c69b6a3859e241debe9d9f9e154e94893f795c7ac020ef9cf084463ea4e0

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnaBJgYOjCkYPxcxNTnncOyf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0394.decode-string.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：68 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0394-decode-string/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
