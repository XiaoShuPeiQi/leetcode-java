# 3 · 无重复字符的最长子串 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0003-longest-substring-without-repeating-characters/

Hot100 · 滑动窗口 · Longest Substring Without Repeating Characters

## 题意

给定字符串 s，求不含重复字符的最长连续子串的长度。

官方原题：https://leetcode.cn/problems/longest-substring-without-repeating-characters/

## 课堂示例

输入：s="abcabcbb"

预期结果：3

实际结果：3

测试来源：力扣公开示例（已读取题目与Java模板）

## 状态含义

窗口是[left,right]；set与当前窗口包含相同字符，且没有重复；res是最长合法长度。

## 状态变化

右端加入字符，若重复就收缩左端，直到窗口重新无重复。

## Java 解法

```java
import java.util.*;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        int res = 0; // 保存最长无重复子串的长度
        Set<Character> set = new HashSet<>();
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            while (set.contains(c)) { // 一直缩到旧的同字符离开窗口
                set.remove(s.charAt(left));
                left++;
            }
            set.add(c);

            int length = right - left + 1; // 此时窗口已经没有重复字符
            if (length > res) {
                res = length;
            }
        }
        return res;
    }
}
```

## 来源与版本

学习扩展：总笔记未检出本题完整题解，按已有章节的方法组织新解法。 真实JDI回放对应的Solution类体，算法中未加入调试输出。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/string/LC0003LongestSubstringWithoutRepeatingCharacters.java

源码指纹：2c4f9cf3101967b9f062beaeb4432d523ad376e5f2b4c55f8ae9d6060ff55468

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：79 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0003-longest-substring-without-repeating-characters/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
