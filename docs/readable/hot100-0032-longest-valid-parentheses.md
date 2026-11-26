# Hot100 第 90 题 · 力扣 32 · 最长有效括号 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0032-longest-valid-parentheses/

Hot100 · 动态规划 · Longest Valid Parentheses

## 题意

给你一个只包含 `'('` 和 `')'` 的字符串，找出最长有效（格式正确且连续）括号 子串 的长度。

左右括号匹配，即每个左括号都有对应的右括号将其闭合的字符串是格式正确的，比如 `"(()())"`。

**示例 1：**

**输入：**s = "(()"
**输出：**2
**解释：**最长有效括号子串是 "()"

**示例 2：**

**输入：**s = ")()())"
**输出：**4
**解释：**最长有效括号子串是 "()()"

**示例 3：**

**输入：**s = ""
**输出：**0

 
**提示：**

`0 <= s.length <= 3 * 104`
`s[i]` 为 `'('` 或 `')'`

官方原题：https://leetcode.cn/problems/longest-valid-parentheses/

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0032.Longest%20Valid%20Parentheses/README.md

## 回放用例

输入：s = ")()())"

预期结果：4

实际结果：4

测试来源：力扣官方示例，与本节推演一致。

## 解法

下标栈

## 状态含义

初始用 -1 表示串左边的边界。左括号下标入栈，右括号先弹栈。弹完为空时，当前右括号成为新边界。否则新栈顶在当前有效后缀的左边，用 i - stack.peek() 算长度。

## 状态变化

用栈找出有效后缀左边的边界，当前右括号下标减去这个边界就是长度。

## Java 解法

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int longestValidParentheses(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);
        int result = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    result = Math.max(result, i - stack.peek());
                }
            }
        }
        return result;
    }
}
```

## 来源与版本

依据代码随想录的解法思路整理 Java 版。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/stackqueue/LC0032LongestValidParentheses.java

源码指纹：4cdfaa23efa1a72424963f0f9bd663acfc80423008685f1f319e9ac9f14e00ae

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcniiEHwDSHip8LdmEGP0rVPf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0032.longest-valid-parentheses.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：33 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0032-longest-valid-parentheses/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
