package leetcode.graph;

import java.util.*;
import common.TreeSupport;


public class LC0207CourseSchedule {
    public static void main(String[] args) {
        TreeSupport.check("Solution", "numCourses=4, prerequisites=[[1,0],[2,0],[3,1],[3,2]]",
                "true", String.valueOf(runSolution()));
    }

    // 测试来源：力扣示例与 Hot100 笔记。
    static Object runSolution() {
        int[][] prerequisites = {{1,0},{2,0},{3,1},{3,2}};
        int numCourses = 4;
        return new Solution().canFinish(numCourses, prerequisites);
    }

    static class Solution {
        public boolean canFinish(int numCourses, int[][] prerequisites) {
            List<List<Integer>> graph = new ArrayList<>();
            for (int i = 0; i < numCourses; i++) {
                graph.add(new ArrayList<>());
            }
            int[] inDegree = new int[numCourses];
            for (int[] edge : prerequisites) {
                graph.get(edge[1]).add(edge[0]);
                inDegree[edge[0]]++;
            }

            Queue<Integer> queue = new ArrayDeque<>();
            for (int i = 0; i < numCourses; i++) {
                if (inDegree[i] == 0) {
                    queue.offer(i);
                }
            }

            int learned = 0;
            while (!queue.isEmpty()) {
                int current = queue.poll();
                learned++;
                for (int next : graph.get(current)) {
                    if (--inDegree[next] == 0) {
                        queue.offer(next);
                    }
                }
            }
            return learned == numCourses;
        }
    }
}
