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
        return new Solution().canFinish(4, prerequisites);
    }

    static class Solution {
        public boolean canFinish(int numCourses, int[][] prerequisites) {
            List<List<Integer>> graph = new ArrayList<>();
            int[] indegree = new int[numCourses];
            for (int i = 0; i < numCourses; i++) {
                graph.add(new ArrayList<>());
            }
            for (int[] pair : prerequisites) {
                int course = pair[0];
                int before = pair[1];
                graph.get(before).add(course);
                indegree[course]++;
            }
            Queue<Integer> queue = new ArrayDeque<>();
            for (int i = 0; i < numCourses; i++) {
                if (indegree[i] == 0) {
                    queue.offer(i);
                }
            }
            int finished = 0;
            while (!queue.isEmpty()) {
                int course = queue.poll();
                finished++;
                for (int next : graph.get(course)) {
                    indegree[next]--;
                    if (indegree[next] == 0) {
                        queue.offer(next);
                    }
                }
            }
            return finished == numCourses;
        }
    }
}
