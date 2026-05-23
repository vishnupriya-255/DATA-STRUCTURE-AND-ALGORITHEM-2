class PSegNode {
    long sum;
    PSegNode left, right;

    PSegNode(long sum, PSegNode left, PSegNode right) {
        this.sum = sum;
        this.left = left;
        this.right = right;
    }
}

public class co2 {

    // Build initial segment tree (v0)
    static PSegNode build(int[] arr, int lo, int hi) {
        if (lo == hi) {
            return new PSegNode(arr[lo], null, null);
        }
        int mid = (lo + hi) / 2;
        PSegNode left = build(arr, lo, mid);
        PSegNode right = build(arr, mid + 1, hi);
        return new PSegNode(left.sum + right.sum, left, right);
    }

    // Persistent update (creates new version)
    static PSegNode pointUpdate(PSegNode node, int lo, int hi, int idx, long newVal) {
        if (lo == hi) {
            return new PSegNode(newVal, null, null);
        }

        int mid = (lo + hi) / 2;

        if (idx <= mid) {
            PSegNode newLeft = pointUpdate(node.left, lo, mid, idx, newVal);
            return new PSegNode(newLeft.sum + node.right.sum, newLeft, node.right);
        } else {
            PSegNode newRight = pointUpdate(node.right, mid + 1, hi, idx, newVal);
            return new PSegNode(node.left.sum + newRight.sum, node.left, newRight);
        }
    }

    // Range sum query
    static long rangeSum(PSegNode node, int lo, int hi, int l, int r) {
        if (node == null || r < lo || hi < l) return 0;

        if (l <= lo && hi <= r) return node.sum;

        int mid = (lo + hi) / 2;
        return rangeSum(node.left, lo, mid, l, r)
             + rangeSum(node.right, mid + 1, hi, l, r);
    }

    public static void main(String[] args) {

        int[] stock = {12, 7, 25, 18, 9, 14, 6, 30};

        PSegNode v0 = build(stock, 0, stock.length - 1);

        PSegNode v1 = pointUpdate(v0, 0, 7, 2, 75);
        PSegNode v2 = pointUpdate(v1, 0, 7, 5, 10);
        PSegNode v3 = pointUpdate(v2, 0, 7, 2, 63);

        System.out.println("Version v0 sum [3..6]: " + rangeSum(v0, 0, 7, 2, 5));
        System.out.println("Version v1 sum [3..6]: " + rangeSum(v1, 0, 7, 2, 5));
        System.out.println("Version v2 sum [3..6]: " + rangeSum(v2, 0, 7, 2, 5));
        System.out.println("Version v3 sum [3..6]: " + rangeSum(v3, 0, 7, 2, 5));
    }
}