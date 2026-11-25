# 242 · 有效的字母异位词 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0242-valid-anagram/

哈希表 · Valid Anagram

## 题意

给定两个字符串 `s` 和 `t` ，编写一个函数来判断 `t` 是否是 `s` 的 字母异位词。

**示例 1:**

```text
输入: s = "anagram", t = "nagaram"
输出: true
```

**示例 2:**

```text
输入: s = "rat", t = "car"
输出: false
```

**提示:**

- `1 <= s.length, t.length <= 5 * 10^4`

- `s` 和 `t` 仅包含小写字母

**进阶:** 如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？

官方原题：https://leetcode.cn/problems/valid-anagram

GitHub 题面来源：https://github.com/doocs/leetcode/blob/7bc7a924fbb7a00df01f90c1568415732b3dd8bd/solution/0200-0299/0242.Valid%20Anagram/README.md

## 回放用例

输入：s = "aab", t = "aba"

预期结果：true

实际结果：true

测试来源：与飞书本题“实例走过程”使用相同输入。

## 状态含义

主解选数组计数：count[i]表示 s 中第 i 种字母的数量减去 t 中的数量。数组下标就是字符的编码差，例如 b - a = 1。Set只记有没有，会丢掉重复次数。

## 状态变化

这道题本质是：比较两串字符的频率，而不是比较字符的位置。

## Java 解法

```java
import java.util.*;

class Solution {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            count[t.charAt(i) - 'a']--;
        }

        // 每个格子都归零，才是每种字母数量相同。
        for (int value : count) {
            if (value != 0) {
                return false;
            }
        }
        return true;
    }
}
```

## 来源与版本

算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/hashtable/LC0242ValidAnagram.java

源码指纹：25e62c6583f80b80ca4c47dd65f5c327eca983be12f965e6179e98ef8c120593

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/F4qJw6Pr2if4LkkJBjBc8aM3nXd（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：72 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0242-valid-anagram/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
