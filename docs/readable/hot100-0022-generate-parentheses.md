# Hot100 第 59 题 · 力扣 22 · 括号生成 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0022-generate-parentheses/

Hot100 · 回溯 · Generate Parentheses

## 题意

数字 `n` 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 **有效的** 括号组合。

**示例 1：**

```text
输入：n = 3
输出：["((()))","(()())","(())()","()(())","()()()"]
```

**示例 2：**

```text
输入：n = 1
输出：["()"]
```

**提示：**

- `1 <= n <= 8`

官方原题：https://leetcode.cn/problems/generate-parentheses

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0022.Generate%20Parentheses/README.md

## 回放用例

输入：n=3

预期结果：[((())), (()()), (())(), ()(()), ()()()]

实际结果：[((())), (()()), (())(), ()(()), ()()()]

测试来源：飞书 Hot100 原有课堂示例

## 解法

按已放左右括号数剪枝

## 状态含义

每个递归前缀始终满足 `0 <= right <= left <= n`。长度到 `2*n` 时，左右两类的数量自然都等于 n。

## 状态变化

左括号还有配额就能放，右括号要等前面已有未匹配的左括号才能放。

## Java 解法

```java
import java.util.*;

class Solution {
    private List<String> result;

    public List<String> generateParenthesis(int n) {
        result = new ArrayList<>();
        backtracking(n, 0, 0, new StringBuilder());
        return result;
    }

    private void backtracking(int n, int left, int right, StringBuilder path) {
        if (path.length() == 2 * n) {
            result.add(path.toString());
            return;
        }
        if (left < n) {
            path.append('(');
            backtracking(n, left + 1, right, path);
            path.deleteCharAt(path.length() - 1);
        }
        if (right < left) {
            path.append(')');
            backtracking(n, left, right + 1, path);
            path.deleteCharAt(path.length() - 1);
        }
    }
}
```

## 来源与版本

算法流程来自代码随想录的 Java 解法，保留飞书原有课堂示例。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0022GenerateParentheses.java

源码指纹：b042774fd3b61679928b05286b40c678ae18841b7ec30378438ee9e23846a512

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnf12WyX7UMonViY1hS5xYeg（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0022.generate-parentheses.html#java

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：195 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0022-generate-parentheses/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
