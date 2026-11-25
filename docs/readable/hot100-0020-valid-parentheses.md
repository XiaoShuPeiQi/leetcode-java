# Hot100 第 69 题 · 力扣 20 · 有效的括号 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0020-valid-parentheses/

Hot100 · 栈 · Valid Parentheses

## 题意

给定一个只包括 `'('`，`')'`，`'{'`，`'}'`，`'['`，`']'` 的字符串 `s` ，判断字符串是否有效。

有效字符串需满足：

1. 左括号必须用相同类型的右括号闭合。

2. 左括号必须以正确的顺序闭合。

3. 每个右括号都有一个对应的相同类型的左括号。

**示例 1：**

**输入：**s = "()"

**输出：**true

**示例 2：**

**输入：**s = "()[]{}"

**输出：**true

**示例 3：**

**输入：**s = "(]"

**输出：**false

**示例 4：**

**输入：**s = "([])"

**输出：**true

**示例 5：**

**输入：**s = "([)]"

**输出：**false

**提示：**

- `1 <= s.length <= 10^4`

- `s` 仅由括号 `'()[]{}'` 组成

官方原题：https://leetcode.cn/problems/valid-parentheses

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0020.Valid%20Parentheses/README.md

## 回放用例

输入：s = "{[]}"

预期结果：true

实际结果：true

测试来源：飞书 Hot100 第 69 题「实例走过程」

## 状态含义

栈中每一项都对应一个还没闭合的括号，栈顶是下一项必须出现的闭括号。匹配就弹栈，失败立即返回 false。处理结束时用 stack.isEmpty() 判断是否全部匹配。

## 状态变化

开括号入栈时存入它期待的闭括号，遇到闭括号就和栈顶核对。

## Java 解法

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (c == '(') {
                // 压入期待的右括号，栈顶表示下一步应匹配谁。
                stack.push(')');
            } else if (c == '[') {
                stack.push(']');
            } else if (c == '{') {
                stack.push('}');
            } else {
                // 右括号多余或类型不符，立即失败。
                if (stack.isEmpty() || stack.peek() != c) {
                    return false;
                }

                stack.pop();
            }
        }

        // 遍历结束还留有期待的括号，说明左括号多余。
        return stack.isEmpty();
    }
}
```

## 来源与版本

算法学习总笔记（Java）原码。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/stackqueue/LC0020ValidParentheses.java

源码指纹：217539a4eae131806f7e5f5632801f79c2ea030469e4c8a96a57e6b573810b8e

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnDyC2GBNM9dyZ4VdwV9JrOb（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：26 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0020-valid-parentheses/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
