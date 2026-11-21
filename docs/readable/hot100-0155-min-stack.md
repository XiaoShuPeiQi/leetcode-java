# Hot100 第 70 题 · 力扣 155 · 最小栈 — 展老师小课堂

网页：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0155-min-stack/

Hot100 · 栈 · Min Stack

## 题意

实现最小栈，支持入栈、出栈、查看栈顶和查询当前最小值。每个操作都应在常数时间内完成。

官方原题：https://leetcode.cn/problems/min-stack/

## 课堂示例

输入：push(3), push(1), push(1), getMin(), pop(), getMin(), pop(), top(), getMin()

预期结果：[1, 1, 3, 3]

实际结果：[1, 1, 3, 3]

测试来源：飞书 Hot100 第 70 题「实例走过程」

## 解法

每次入栈同时保存这一刻的最小值，弹栈时把这个最小值快照也一起弹出。

## 状态含义

minStack 的每一层存数据栈从底到该层的最小值。 push 压入 val 并记录最小值，pop 同步弹出。top 读数据栈顶，getMin 读最小值栈顶。

## 状态变化

每次入栈同时保存这一刻的最小值，弹栈时把这个最小值快照也一起弹出。

## Java 解法

```java
import java.util.ArrayDeque;
import java.util.Deque;

class MinStack {
    private final Deque<Integer> stack;
    private final Deque<Integer> minStack;

    public MinStack() {
        stack = new ArrayDeque<>();
        minStack = new ArrayDeque<>();
    }

    public void push(int val) {
        stack.push(val);

        // 记录数据栈处于当前状态时的最小值
        if (minStack.isEmpty()) {
            minStack.push(val);
        } else {
            minStack.push(Math.min(val, minStack.peek()));
        }
    }

    public void pop() {
        // 两个栈同步弹出，恢复到上一个状态
        stack.pop();
        minStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
```

## 来源与版本

代码随想录 · Java。 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。

Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/src/main/java/leetcode/stackqueue/LC0155MinStack.java

源码指纹：58b1081069d63c973a0571b4e8dc4cdabc532dc93b21e3a8fd337f95610b4797

飞书来源笔记：https://acn581cm6pdd.feishu.cn/wiki/G5bqwOe1eicQRekOAjMcPpwlnWf#doxcn30qo2pl4fsFJF6HCbGuT3b（外部文档可能需要登录，本文仅包含本页已发布的内容。）

代码随想录 Java 解法：https://programmercarl.com/hot100/0155.min-stack.html

## 执行回放

本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。

原始暂停事件：40 个。

完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。

完整轨迹：https://xiaoshupeiqi.github.io/leetcode-java/problems/hot100-0155-min-stack/trace.json

本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。
