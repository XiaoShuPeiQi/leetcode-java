# 76 · 最小覆盖子串 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0076-minimum-window-substring/

Hot100 · 子串 · Minimum Window Substring

## 题意

给定两个字符串 `s` 和 `t`，长度分别是 `m` 和 `n`，返回 s 中的 **最短窗口 子串**，使得该子串包含 `t` 中的每一个字符（**包括重复字符**）。如果没有这样的子串，返回空字符串 `""`。

测试用例保证答案唯一。

**示例 1：**

```text
输入：s = "ADOBECODEBANC", t = "ABC"
输出："BANC"
解释：最小覆盖子串 "BANC" 包含来自字符串 t 的 'A'、'B' 和 'C'。
```

**示例 2：**

```text
输入：s = "a", t = "a"
输出："a"
解释：整个字符串 s 是最小覆盖子串。
```

**示例 3:**

```text
输入: s = "a", t = "aa"
输出: ""
解释: t 中两个字符 'a' 均应包含在 s 的子串中，
因此没有符合条件的子字符串，返回空字符串。
```

**提示：**

- `m == s.length`

- `n == t.length`

- `1 <= m, n <= 10^5`

- `s` 和 `t` 由英文字母组成

**进阶：**你能设计一个在 `O(m + n)` 时间内解决此问题的算法吗？

官方原题：https://leetcode.cn/problems/minimum-window-substring

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0000-0099/0076.Minimum%20Window%20Substring/README.md

## 回放用例

输入：s="ADOBECODEBANC"; t="ABC"

预期结果：BANC

实际结果：BANC

测试来源：总笔记同题实例

## 状态含义

left 和 right 标记窗口两端，直接读取这两个位置的字符来更新欠账。右端字符加入前若仍欠账，missing 减一。左端字符移出后若重新欠账，missing 加一。bestStart 和 bestLength 保存目前最短的有效窗口。

## 状态变化

右端还清目标字符欠账后，连续缩左端，寻找最短有效窗口。右指针负责扩大窗口，直到满足要求；左指针负责缩小窗口，直到再缩就不满足要求。

## Java 解法

```java
import java.util.*;

class Solution {
    public String minWindow(String s, String t) {
        String res = ""; // 没有找到覆盖窗口时，答案就是空字符串
        if (t.length() == 0 || s.length() < t.length()) {
            return res;
        }

        int[] need = new int[128];
        for (int i = 0; i < t.length(); i++) {
            need[t.charAt(i)]++;
        }
        int missing = t.length();
        int left = 0;
        int bestStart = 0;
        int bestLength = Integer.MAX_VALUE;

        for (int right = 0; right < s.length(); right++) {
            if (need[s.charAt(right)] > 0) { // 加入前仍欠这个字符，才补掉一个缺口
                missing--;
            }
            need[s.charAt(right)]--;

            while (missing == 0) {
                int length = right - left + 1;
                if (length < bestLength) {
                    bestStart = left;
                    bestLength = length;
                }

                need[s.charAt(left)]++;
                if (need[s.charAt(left)] > 0) { // 移走后重新欠字符，窗口变为无效
                    missing++;
                }
                left++;
            }
        }

        if (bestLength != Integer.MAX_VALUE) {
            res = s.substring(bestStart, bestStart + bestLength); // substring不包含右端位置
        }
        return res;
    }
}
```

## 来源与版本

沿用总笔记中本题的算法思路，代码按结果先定义、逐步填入的方式展开。 真实JDI回放对应的Solution类体，算法中未加入调试输出。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/array/LC0076MinimumWindowSubstring.java

源码指纹：0b455f5415e7e9ab38eef78b60757d88ad8d06490917318f91f459f2d7f99a99

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：156 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0076-minimum-window-substring/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
