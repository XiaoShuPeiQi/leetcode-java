# 583 · 两个字符串的删除操作 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0583-delete-operation-for-two-strings/

动态规划 · Delete Operation for Two Strings

## 题意

给定两个单词 `word1` 和 `word2` ，返回使得 `word1` 和  `word2` **相同**所需的**最小步数**。

**每步** 可以删除任意一个字符串中的一个字符。

**示例 1：**

```text
输入: word1 = "sea", word2 = "eat"
输出: 2
解释: 第一步将 "sea" 变为 "ea" ，第二步将 "eat "变为 "ea"
```

**示例  2:**

```text
输入：word1 = "leetcode", word2 = "etco"
输出：4
```

**提示：**

- `1 <= word1.length, word2.length <= 500`

- `word1` 和 `word2` 只包含小写英文字母

官方原题：https://leetcode.cn/problems/delete-operation-for-two-strings

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0500-0599/0583.Delete%20Operation%20for%20Two%20Strings/README.md

## 回放用例

输入：word1 = "sea", word2 = "eat"

预期结果：2

实际结果：2

测试来源：本章实例走过程；与本地main和网页回放输入一致。采用力扣公开示例。

## 状态含义

dp[i][j] 表示让word1前i个字符与word2前j个字符相同所需的最少删除次数。

## 状态变化

相等取dp[i-1][j-1]；不同取min(dp[i-1][j],dp[i][j-1])+1。同时删两边可以分成两次，无需单独写。

## Java 解法

```java
import java.util.*;

class Solution {
    public int minDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 0; i <= word1.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= word2.length(); j++) dp[0][j] = j;
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/dp/LC0583DeleteOperationforTwoStrings.java

源码指纹：a238652fd09a56096a911b673f796e59709dc8fcef9efe2fa41fee5383024b8c

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/TvWwwHEUBi8S2Gk6apqc9W9cnVe（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：40 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0583-delete-operation-for-two-strings/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
