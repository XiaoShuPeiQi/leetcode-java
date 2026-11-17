# 455 · 分发饼干 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/0455-assign-cookies/

贪心 · Assign Cookies

## 题意

每个孩子有胃口值，每块饼干有尺寸。每人最多拿一块，每块最多给一人，尺寸至少等于胃口才满足。求最多能满足多少孩子。

官方原题：https://leetcode.cn/problems/assign-cookies/

## 课堂示例

输入：g = [1, 2, 3], s = [1, 1, 3]

预期结果：2

实际结果：2

测试来源：飞书贪心章节实例走过程（revision 87）

## Java 解法

```java
import java.util.*;

class Solution {
    public int findContentChildren(int[] g, int[] s) {
        g = g.clone();
        s = s.clone();
        Arrays.sort(g);
        Arrays.sort(s);
        int child = 0;
        for (int cookie = 0; cookie < s.length && child < g.length; cookie++) {
            if (s[cookie] >= g[child]) {
                child++; // 当前饼干满足最小的待满足胃口
            }
        }
        return child;
    }
}
```

## 来源与版本

录制代码与2026-10-14读取的飞书Solution类体一致，只增加static与外层运行适配。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/greedy/LC0455AssignCookies.java

源码指纹：a2bc23e0a438dec4456e3f10f9d1aea98fb8b74739a5db583e09318a24ca8372

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/QHnaw0xWDiNBTfkmaG3cbydHnYf（外部文档可能需要登录，本文仅包含本页已发布的内容。）

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：17 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/0455-assign-cookies/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
