import java.util.*;

class AVLNode {
    long ts;
    String trackId;
    AVLNode left, right;
    int height = 1;

    AVLNode(long ts, String tid) {
        this.ts = ts;
        this.trackId = tid;
    }
}

public class co1 {

    // Height
    static int height(AVLNode n) {
        return (n == null) ? 0 : n.height;
    }

    // Balance Factor
    static int getBalance(AVLNode n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    // Update Height
    static void updateHeight(AVLNode n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    // Right Rotation
    static AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // Left Rotation
    static AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // Insert (Descending order)
    static AVLNode insert(AVLNode node, long ts, String tid) {
        if (node == null)
            return new AVLNode(ts, tid);

        // Descending order: bigger → left
        if (ts > node.ts)
            node.left = insert(node.left, ts, tid);
        else
            node.right = insert(node.right, ts, tid);

        updateHeight(node);

        int balance = getBalance(node);

        // LL
        if (balance > 1 && ts > node.left.ts)
            return rotateRight(node);

        // RR
        if (balance < -1 && ts < node.right.ts)
            return rotateLeft(node);

        // LR
        if (balance > 1 && ts < node.left.ts) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // RL
        if (balance < -1 && ts > node.right.ts) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // Top-K helper
    static void topKHelper(AVLNode node, int k, List<String> res) {
        if (node == null || res.size() == k)
            return;

        // Left → Root → Right (descending)
        topKHelper(node.left, k, res);

        if (res.size() < k)
            res.add(node.trackId + " (" + node.ts + ")");

        topKHelper(node.right, k, res);
    }

    // Top-K function
    static List<String> topKDescending(AVLNode root, int k) {
        List<String> result = new ArrayList<>();
        topKHelper(root, k, result);
        return result;
    }

    // Main (Test)
    public static void main(String[] args) {

        AVLNode root = null;

        long[] timestamps = {
            32400, 28800, 36000, 25200, 39600,
            21600, 43200, 18000, 46800, 14400, 50400
        };

        // Insert data
        for (int i = 0; i < timestamps.length; i++) {
            root = insert(root, timestamps[i], "Track" + (i + 1));
        }

        // Get Top 5 recent tracks
        List<String> topTracks = topKDescending(root, 5);

        System.out.println("Top 5 Recent Tracks:");
        for (String t : topTracks) {
            System.out.println(t);
        }
    }
}