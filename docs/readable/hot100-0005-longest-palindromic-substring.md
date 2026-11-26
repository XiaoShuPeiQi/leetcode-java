# Hot100 第 93 题 · 力扣 5 · 最长回文子串 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0005-longest-palindromic-substring/

Hot100 · 多维动态规划 · Longest Palindromic Substring

## 题意

给你一个字符串 `s`，找到 `s` 中最长的 回文 子串。

**示例 1：**

```text
输入：s = "babad"
输出："bab"
解释："aba" 同样是符合题意的答案。
```

**示例 2：**

```text
输入：s = "cbbd"
输出："bb"
```

**提示：**

- `1 <= s.length <= 1000`
- `s` 仅由数字和英文字母组成

官方原题：https://leetcode.cn/problems/longest-palindromic-substring/

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0005.Longest%20Palindromic%20Substring/README.md

## 回放用例

输入：s = "cbbd"

预期结果：bb

实际结果：bb

测试来源：力扣官方示例，与本节推演一致。

## 解法

中心扩展

## 状态含义

expand 返回两个指针失配或越界前得到的回文长度 right-left-1。每个 i 都尝试 (i,i) 和 (i,i+1)，分别处理奇数和偶数长度。取得更长回文时，再更新 start 和 bestLength。

## 状态变化

枚举回文的中心，左右相等就一起向外扩，记录最长的一段。

## Java 解法

```java
class Solution {
    public String longestPalindrome(String s) {
        int start = 0;
        int bestLength = 0;
        for (int i = 0; i < s.length(); i++) {
            int length = Math.max(expand(s, i, i), expand(s, i, i + 1));
            if (length > bestLength) {
                start = i - (length - 1) / 2;
                bestLength = length;
            }
        }
        return s.substring(start, start + bestLength);
    }

    private int expand(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }
}
```

## 来源与版本

依据代码随想录的解法思路整理 Java 版。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0005LongestPalindromicSubstring.java

源码指纹：91351de5c68f484795e5896d4c8ad119dd724a64c131cff645d7729069a1a82e

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcnPFlddxowGjcWuPY2BBQOpe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0005.longest-palindromic-substring.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：69 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0005-longest-palindromic-substring/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
