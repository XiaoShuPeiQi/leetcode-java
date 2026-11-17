# 438 · 找到字符串中所有字母异位词 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0438-find-all-anagrams-in-a-string/

Hot100 · 滑动窗口 · Find All Anagrams in a String

## 题意

找出 s 中所有与 p 字符及次数相同的连续子串，返回它们的起始下标。

官方原题：https://leetcode.cn/problems/find-all-anagrams-in-a-string/

## 课堂示例

输入：s="cbaebabacd"; p="abc"

预期结果：[0,6]

实际结果：[0,6]

测试来源：力扣公开示例（已读取题目与Java模板）

## 状态含义

need记录p的频次；window记录当前窗口频次；left是当前左端；窗口长度最多为p.length()。

## 状态变化

维护长度等于p的窗口，比较26个字母的频次。

## Java 解法

```java
import java.util.*;

class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>(); // 保存每个匹配窗口的起始下标
        int[] need = new int[26];
        int[] window = new int[26];
        for (int i = 0; i < p.length(); i++) {
            need[p.charAt(i) - 'a']++;
        }
        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            window[s.charAt(right) - 'a']++;
            if (right - left + 1 > p.length()) { // 固定窗口只移走一个最左字符
                window[s.charAt(left) - 'a']--;
                left++;
            }
            if (right - left + 1 == p.length() && Arrays.equals(need, window)) {
                res.add(left); // 当前窗口的左端就是一个答案下标
            }
        }
        return res;
    }
}
```

## 来源与版本

学习扩展：总笔记未检出本题完整题解，按已有章节的方法组织新解法。 真实JDI回放对应的Solution类体，算法中未加入调试输出。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/string/LC0438FindAllAnagramsInAString.java

源码指纹：1df3202117f0bce81b43cf18a2fd2204e66ccf50dccf1c3ae7158b1f6aaeefa8

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：71 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0438-find-all-anagrams-in-a-string/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
