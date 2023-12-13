package pl.pacinho.adventofcode2023.challange.day11.tools;

import pl.pacinho.adventofcode2023.model.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BFS {

    private final int v;
    private final ArrayList<ArrayList<Integer>> adj;

    public BFS(int v, List<Pair<Integer, Integer>> lines) {
        this.v = v;
        adj = new ArrayList<>(v);
        initAdj(v, lines);
    }

    private void initAdj(int v, List<Pair<Integer, Integer>> lines) {
        for (int i = 0; i < v; i++) {
            adj.add(new ArrayList<>());
        }

        lines.forEach(p -> addEdge(adj, p.key(), p.value()));
    }

    private void addEdge(ArrayList<ArrayList<Integer>> adj, int i, int j) {
        adj.get(i).add(j);
    }

    // function to print the shortest distance and path
    // between source vertex and destination vertex
    public List<Integer> printShortestDistance(int s, int dest) {
        // predecessor[i] array stores predecessor of
        // i and distance array stores distance of i
        // from s
        int pred[] = new int[v];
        int dist[] = new int[v];

        if (!BFS(adj, s, dest, v, pred, dist)) {
//            System.out.println("Given source and destination are not connected");
            return Collections.emptyList();
        }

        // LinkedList to store path
        LinkedList<Integer> path = new LinkedList<>();
        int crawl = dest;
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }

        // Print distance
//        System.out.println("Shortest path length is: " + dist[dest]);

        // Print path
        List<Integer> out = new ArrayList<>();
        //System.out.print("Path is ::");
        for (int i = path.size() - 1; i >= 0; i--) {
            Integer idx = path.get(i);
            out.add(idx);
            //System.out.print(idx + " ");
        }
        //System.out.println();

        return  out;
//        return dist[dest];
    }

    // a modified version of BFS that stores predecessor
    // of each vertex in array pred
    // and its distance from source in array dist
    private boolean BFS(ArrayList<ArrayList<Integer>> adj, int src, int dest, int v, int pred[], int dist[]) {
        // a queue to maintain queue of vertices whose
        // adjacency list is to be scanned as per normal
        // BFS algorithm using LinkedList of Integer type
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // boolean array visited[] which stores the
        // information whether ith vertex is reached
        // at least once in the Breadth first search
        boolean visited[] = new boolean[v];

        // initially all vertices are unvisited
        // so v[i] for all i is false
        // and as no path is yet constructed
        // dist[i] for all i set to infinity
        for (int i = 0; i < v; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        // now source is first to be visited and
        // distance from source to itself should be 0
        visited[src] = true;
        dist[src] = 0;
        queue.add(src);

        // bfs Algorithm
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < adj.get(u).size(); i++) {
                if (visited[adj.get(u).get(i)] == false) {
                    visited[adj.get(u).get(i)] = true;
                    dist[adj.get(u).get(i)] = dist[u] + 1;
                    pred[adj.get(u).get(i)] = u;
                    queue.add(adj.get(u).get(i));

                    // stopping condition (when we find
                    // our destination)
                    if (adj.get(u).get(i) == dest)
                        return true;
                }
            }
        }
        return false;
    }

}
