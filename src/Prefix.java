public class Prefix {
    String[] t;

    final static String start = "<START>", end = "<END>", par = "<PAR>";

    Prefix(int n) {
        t = new String[n];
        for (int i=0; i < n; i++) {
            t[i] = start;
        }
    }

    private Prefix(String[] tab) {
        t = tab;
    }

    static boolean eq(Prefix p1, Prefix p2) {
        if (p1.t.length != p2.t.length) {
            return false;
        }

        for (int i = 0; i < p1.t.length; i++) {
            if (!p1.t[i].equals(p2.t[i])) {
                return false;
            }
        }

        return true;
    }

    Prefix addShift(String w) {
        int l = t.length;
        String[] tab = new String[l];

        for (int i = 0; i < l-1; i++) {
            tab[i] = t[i+1];
        }
        
        tab[l-1] = w;
        return new Prefix(tab);
    }

    public int hashCode() {
        int h = 0;
        for (int i = 0; i < t.length; i++) {
            h = 37*h + t[i].hashCode();
        }
        return h;
    }

    int hashCode(int n) {
        int h = hashCode() % n;
        if (h >= 0) return h;
        return h + n;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(t[0]);
        for (int i = 1; i < t.length; i++) {
            sb.append(", ");
            sb.append(t[i]);
        }

        return sb.toString();
    }
}
