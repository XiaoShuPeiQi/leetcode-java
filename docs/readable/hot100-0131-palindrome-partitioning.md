# Hot100 第 61 题 · 力扣 131 · 分割回文串 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0131-palindrome-partitioning/

Hot100 · 回溯 · Palindrome Partitioning

## 题意

在字符串 s 中切若干刀，使切出来的每段都是回文串。返回所有可行的切分。

官方原题：https://leetcode.cn/problems/palindrome-partitioning/

## 课堂示例

输入：s = "aab"

预期结果：[[a, a, b], [aa, b]]

实际结果：[[a, a, b], [aa, b]]

测试来源：飞书 Hot100 第 61 题「实例走过程」

## 状态含义

path 拼起来刚好是 startIndex 之前的前缀，里面每一段都已经通过回文检查。到达串尾时把 path 的副本放入 result，回到上一层再删掉刚选的那一段。

## 状态变化

从第一个没被切走的字符开始，枚举这一段的右端点，只沿回文段往下切。

## Java 解法

```java
import java.util.*;

class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        List<String> path = new ArrayList<>();
        backtracking(s, 0, path, result);
        return result;
    }

    private void backtracking(String s, int startIndex,
            List<String> path, List<List<String>> result) {
        //走到最后了
        if (startIndex == s.length()) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int end = startIndex; end < s.length(); end++) {
            // 当前段不是回文，换一个段尾。
            if (!isPalindrome(s, startIndex, end)) {
                continue;
            }
            String segment = s.substring(startIndex, end + 1);
            path.add(segment);
            // 从下一段起点继续。
            backtracking(s, end + 1, path, result);
            path.remove(path.size() - 1);
        }
    }
    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
```

## 来源与版本

算法学习总笔记（Java）原码。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0131PalindromePartitioning.java

源码指纹：4860750d40b805138267f2cf7b849e458652411af7004afb1cec00758f220749

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnK5ATXg4DkVMcRWZFhTzUQc（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：106 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0131-palindrome-partitioning/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
