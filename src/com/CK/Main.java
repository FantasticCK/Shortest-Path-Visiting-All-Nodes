package com.CK;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
        int[][] graph = {{1, 2, 3}, {0}, {0}, {0}};
        new Solution().shortestPathLength(graph);
    }
}

//BFS
class Solution {
    public int shortestPathLength(int[][] graph) {
        int N = graph.length;
        Queue<State> queue = new LinkedList();
        int[][] dist = new int[1 << N][N];
        for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);

        for (int x = 0; x < N; ++x) {
            queue.offer(new State(1 << x, x));
            dist[1 << x][x] = 0;
        }

        while (!queue.isEmpty()) {
            State node = queue.poll();
            int d = dist[node.cover][node.head];
            if (node.cover == (1 << N) - 1) return d;

            for (int child : graph[node.head]) {
                int cover2 = node.cover | (1 << child);
                if (d + 1 < dist[cover2][child]) {
                    dist[cover2][child] = d + 1;
                    queue.offer(new State(cover2, child));

                }
            }
        }

        throw null;
    }
}

class State {
    int cover, head;

    State(int c, int h) {
        cover = c;
        head = h;
    }
}

//DP
class Solution {
    public int shortestPathLength(int[][] graph) {
        int[][] dp = new int[graph.length][1<<graph.length];
        Queue<State> queue =  new LinkedList<>();
        for (int i = 0; i < graph.length; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
            dp[i][1<<i]=0;
            queue.offer(new State(1<<i, i));
        }

        while (!queue.isEmpty()) {
            State state = queue.poll();

            for (int next : graph[state.source]) {
                int nextMask = state.mask | 1 << next;
                if (dp[next][nextMask] > dp[state.source][state.mask]+1) {
                    dp[next][nextMask] = dp[state.source][state.mask]+1;
                    queue.offer(new State(nextMask, next));
                }
            }
        }

        int res = Integer.MAX_VALUE;
        for (int i = 0; i < graph.length; i++) {
            res = Math.min(res, dp[i][(1<<graph.length)-1]);
        }
        return res;
    }

    class State {
        public int mask, source;
        public State(int m, int s) {
            mask=m;
            source=s;
        }
    }
}