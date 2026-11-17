# 49 · 字母异位词分组 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0049-group-anagrams/

Hot100 · 哈希 · Group Anagrams

## 题意

把字符串按字符及其出现次数分组。两个字符串只要重排后能相同，就放在同一组。组内和组间顺序任意。

官方原题：https://leetcode.cn/problems/group-anagrams/

## 课堂示例

输入：strs=["eat","tea","tan","ate","nat","bat"]

预期结果：[["ate","eat","tea"],["bat"],["nat","tan"]]

实际结果：[["ate","eat","tea"],["bat"],["nat","tan"]]

测试来源：力扣公开示例（已读取题目与Java模板）

## 状态含义

res保存最终的分组列表；key是排序后的字符序列；map[key]保存拥有相同key的一组原字符串。先创建组、再加入单词，最后把各组放入res。

## 状态变化

排序后的字符相同，就把单词放进同一组。

## Java 解法

```java
import java.util.*;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<>(); // 保存最终的各组单词
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);

            if (!map.containsKey(key)) { // 第一次遇到这个key，先创建一组
                List<String> group = new ArrayList<>();
                map.put(key, group);
            }
            map.get(key).add(str); // 把原单词放入对应分组
        }

        for (List<String> group : map.values()) {
            res.add(group);
        }
        return res;
    }
}
```

## 来源与版本

学习扩展：总笔记未检出本题完整题解，按已有章节的方法组织新解法。 真实JDI回放对应的Solution类体，算法中未加入调试输出。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/hashtable/LC0049GroupAnagrams.java

源码指纹：79c44841bdb358dfdf3dd635441bdb5563e0dbb3af20810df1f6586ccc535fd1

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：55 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0049-group-anagrams/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
