class PSegNode {
    long sum;
    PSegNode left, right;

    PSegNode(long sum) {
        this.sum = sum;
    }

    PSegNode(long sum, PSegNode left, PSegNode right) {
        this.sum = sum;
        this.left = left;
        this.right = right;
    }
}

public class co2 {

    // Build initial segment tree
    static PSegNode build(int[] a, int l, int r) {
        if (l == r) {
            return new PSegNode(a[l]);
        }

        int m = (l + r) / 2;
        PSegNode left = build(a, l, m);
        PSegNode right = build(a, m + 1, r);

        return new PSegNode(left.sum + right.sum, left, right);
    }

    // Persistent update
    static PSegNode update(PSegNode node, int l, int r, int idx, long val) {
        if (l == r) {
            return new PSegNode(val);
        }

        int m = (l + r) / 2;

        if (idx <= m) {
            PSegNode newLeft = update(node.left, l, m, idx, val);
            return new PSegNode(newLeft.sum + node.right.sum, newLeft, node.right);
        } else {
            PSegNode newRight = update(node.right, m + 1, r, idx, val);
            return new PSegNode(node.left.sum + newRight.sum, node.left, newRight);
        }
    }

    // Range sum query
    static long rangeSum(PSegNode node, int l, int r, int ql, int qr) {
        if (node == null || qr < l || r < ql) return 0;

        if (ql <= l && r <= qr) return node.sum;

        int m = (l + r) / 2;

        return rangeSum(node.left, l, m, ql, qr)
             + rangeSum(node.right, m + 1, r, ql, qr);
    }

    public static void main(String[] args) {

        int[] stock = {12, 7, 25, 18, 9, 14, 6, 30};

        PSegNode v0 = build(stock, 0, stock.length - 1);

        PSegNode v1 = update(v0, 0, 7, 2, 75);
        PSegNode v2 = update(v1, 0, 7, 5, 10);
        PSegNode v3 = update(v2, 0, 7, 2, 63);

        int l = 2, r = 5;

        System.out.println("Version v0 sum [3..6]: " + rangeSum(v0, 0, 7, l, r));
        System.out.println("Version v1 sum [3..6]: " + rangeSum(v1, 0, 7, l, r));
        System.out.println("Version v2 sum [3..6]: " + rangeSum(v2, 0, 7, l, r));
        System.out.println("Version v3 sum [3..6]: " + rangeSum(v3, 0, 7, l, r));
    }
}