# 131 · 分割回文串 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0131-palindrome-partitioning/

回溯 · Palindrome Partitioning

## 题意

在字符串 s 中切若干刀，使切出来的每段都是回文串。返回所有可行的切分。

官方原题：https://leetcode.cn/problems/palindrome-partitioning/

## 课堂示例

输入：s = aab

预期结果：[[a, a, b], [aa, b]]

实际结果：[[a, a, b], [aa, b]]

测试来源：飞书回溯笔记 3.6 的执行过程示例

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

最新飞书算法与本地导入版本一致；注释可能不同。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/backtracking/LC0131PalindromePartitioning.java

源码指纹：4860750d40b805138267f2cf7b849e458652411af7004afb1cec00758f220749

飞书来源笔记：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：106 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0131-palindrome-partitioning/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
