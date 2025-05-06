public class HMap {
    EntryList[] t;
    int nbEntries;

    static void main(String[] args) {
        // Tests on Hmap
    }

    HMap(int n) {
        t = new EntryList[n];
        nbEntries = 0;
    }

    HMap() {
        t = new EntryList[20];
        nbEntries = 0;
    }

    WordList find(Prefix key) {
        // if (t.length == 0) {
        //     return null;
        // }
        int h = key.hashCode(t.length);
        for (EntryList cur = t[h]; cur != null; cur = cur.next) {
            if (Prefix.eq(key, cur.head.key)) {
                return cur.head.value;
            }
        }

        return null;
    }

    void addSimple(Prefix key, String w) {
        int h = key.hashCode(t.length);
        if (t[h] == null) {
            t[h] = new EntryList(new Entry(key, new WordList(w, null)), null);
            nbEntries++;
        } else {
            for (EntryList cur = t[h]; cur != null; cur = cur.next) {
                if (Prefix.eq(key, cur.head.key)) {
                    cur.head.value.addLast(w);
                    return;
                }
            }
            t[h].next = new EntryList(t[h].head, t[h].next);
            t[h].head = new Entry(key, new WordList(w, null));
            nbEntries++;
        }
    }

    void print() {
        for (int i = 0; i < t.length; i++) {
            System.out.println(i + ":");
            for (EntryList cur = t[i]; cur != null; cur = cur.next) {
                System.out.println("\t" + cur.head.key + ":");
                System.out.println("\t\t" + cur.head.value + ":");
            }
        }
    }

    void rehash(int n) {
        EntryList[] tNew = new EntryList[n];
        int h;
        for (int i = 0; i < t.length; i++) {
            for (EntryList cur = t[i]; cur != null; cur = cur.next) {
                h = cur.head.key.hashCode(n);

                if (tNew[h] == null) {
                    tNew[h] = new EntryList(new Entry(cur.head.key, cur.head.value), null);
                } else {
                    tNew[h].next = new EntryList(tNew[h].head, tNew[h].next);
                    tNew[h].head = new Entry(cur.head.key, cur.head.value);
                }
            }
        }

        t = tNew;
    }

    void add(Prefix key, String w) {
        // addSimple(key, w);
        // if (nbEntries > 3 * t.length / 4) {
        //     rehash(2 * t.length);
        // }

        if ((nbEntries + 1) > 3 * t.length / 4) {
            rehash(2 * t.length);
        }
        addSimple(key, w);
    }
}
