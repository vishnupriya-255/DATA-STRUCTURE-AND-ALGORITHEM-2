import java.util.*;

class Edge {
    int u, v, weight;

    Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }
}

public class co4 {

    static void bellmanFord(int V, List<Edge> edges, int source) {

        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[source] = 0;

        // V-1 relaxations
        for (int i = 1; i <= V - 1; i++) {
            for (Edge e : edges) {
                if (dist[e.u] != Integer.MAX_VALUE &&
                    dist[e.u] + e.weight < dist[e.v]) {

                    dist[e.v] = dist[e.u] + e.weight;
                }
            }
        }

        // Negative cycle detection
        for (Edge e : edges) {
            if (dist[e.u] != Integer.MAX_VALUE &&
                dist[e.u] + e.weight < dist[e.v]) {

                System.out.println("Negative Cycle Detected");
                return;
            }
        }

        String[] hubs = {
            "MJC", "KEM", "JAY",
            "KOR", "WHF", "HBR", "MRT"
        };

        System.out.println("Shortest Travel Times from MJC:");

        for (int i = 0; i < V; i++) {
            System.out.println(hubs[i] + " = " + dist[i] + " min");
        }
    }

    public static void main(String[] args) {

        int V = 7;

        List<Edge> edges = new ArrayList<>();

        edges.add(new Edge(0, 1, 8));    // MJC -> KEM
        edges.add(new Edge(0, 2, 5));    // MJC -> JAY
        edges.add(new Edge(1, 3, 12));   // KEM -> KOR
        edges.add(new Edge(1, 5, 7));    // KEM -> HBR
        edges.add(new Edge(2, 3, 10));   // JAY -> KOR
        edges.add(new Edge(2, 4, 4));    // JAY -> WHF
        edges.add(new Edge(3, 5, 9));    // KOR -> HBR
        edges.add(new Edge(3, 6, 6));    // KOR -> MRT
        edges.add(new Edge(5, 6, 3));    // HBR -> MRT
        edges.add(new Edge(4, 6, -3));   // WHF -> MRT
        edges.add(new Edge(4, 5, 11));   // WHF -> HBR

        bellmanFord(V, edges, 0); // Source = MJC
    }
}
