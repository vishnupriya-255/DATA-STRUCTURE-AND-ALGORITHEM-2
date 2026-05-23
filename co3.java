import java.util.*;

public class co3 {

    static class Edge {
        int u, v, w;
        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    // Simple DSU
    static class DSU {
        int[] parent;

        DSU(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }

        boolean union(int a, int b) {
            int ra = find(a);
            int rb = find(b);
            if (ra == rb) return false;
            parent[ra] = rb;
            return true;
        }
    }

    public static void main(String[] args) {

        int n = 7; // M, K, W, S, E, Y, H

        // edges: {u, v, weight}
        List<Edge> edges = Arrays.asList(
            new Edge(0, 1, 8),   // M-K
            new Edge(0, 2, 12),  // M-W
            new Edge(0, 3, 10),  // M-S
            new Edge(1, 2, 7),   // K-W
            new Edge(1, 4, 9),   // K-E
            new Edge(2, 4, 11),  // W-E
            new Edge(3, 4, 5),   // S-E
            new Edge(3, 5, 6),   // S-Y
            new Edge(4, 5, 4),   // E-Y
            new Edge(5, 6, 8),   // Y-H
            new Edge(1, 6, 9),   // K-H
            new Edge(0, 6, 14)   // M-H
        );

        // sort edges by weight
        edges.sort(Comparator.comparingInt(e -> e.w));

        DSU dsu = new DSU(n);

        int totalCost = 0;

        System.out.println("MST Edges:");

        for (Edge e : edges) {
            if (dsu.union(e.u, e.v)) {
                System.out.println(e.u + " - " + e.v +":" + e.w);
                totalCost += e.w;
            }
        }

        System.out.println("\nTotal MST Cost = " + totalCost );
    }
}
