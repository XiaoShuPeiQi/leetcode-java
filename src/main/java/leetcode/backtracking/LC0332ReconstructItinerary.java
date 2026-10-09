package leetcode.backtracking;

import java.util.*;

public class LC0332ReconstructItinerary {

    public static void main(String[] args) {
        // 测试用例：飞书回溯笔记 3.13 的执行过程示例
        // 来源：https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g
        List<List<String>> tickets = List.of(
                List.of("JFK", "KUL"), List.of("JFK", "NRT"), List.of("NRT", "JFK"));
        List<String> expected = List.of("JFK", "NRT", "JFK", "KUL");

        System.out.println("LeetCode 332 - 重新安排行程");
        System.out.println("Input:");
        System.out.println("tickets = " + tickets);

        Solution solution = new Solution();
        List<String> actual = solution.findItinerary(tickets);

        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Actual:");
        System.out.println(actual);
        // 行程的机场先后顺序必须一致。
        boolean passed = actual.equals(expected);
        System.out.println("Result: " + (passed ? "PASS" : "FAIL"));
        if (!passed) {
            throw new AssertionError("重新安排行程：实际结果与预期结果不一致");
        }
    }



    // 飞书原始代码：只增加 static 包装与一层缩进，算法保持原样。
    static class Solution {
        public List<String> findItinerary(List<List<String>> tickets) {
            Map<String, PriorityQueue<String>> graph = new HashMap<>();
            for (List<String> ticket : tickets) {
                String from = ticket.get(0);
                String to = ticket.get(1);
                if (!graph.containsKey(from)) {
                    graph.put(from, new PriorityQueue<>());
                }
                PriorityQueue<String> destinations = graph.get(from);
                destinations.offer(to);
            }
            List<String> result = new ArrayList<>();
            traverse("JFK", graph, result);
            Collections.reverse(result);
            return result;
        }

        private void traverse(String airport, Map<String, PriorityQueue<String>> graph,
                List<String> result) {
            PriorityQueue<String> destinations = graph.get(airport);
            while (destinations != null && !destinations.isEmpty()) {
                // 先取字典序最小的剩余机票。
                String nextAirport = destinations.poll();
                traverse(nextAirport, graph, result);
            }
            // 无出边时收集，最后反转。
            result.add(airport);
        }
    }
}
