/**
 * This class provides a custom implementation of a hash map.
 * It is specifically designed to map a `Prefix` (a sequence of words) to a `WordList`
 * (a list of words that can follow that prefix).
 * The implementation uses an array of linked lists (separate chaining) to handle hash collisions.
 * It also automatically resizes itself (rehashes) when it becomes too full, in order to
 * maintain good performance for insertions and lookups.
 */
public class HMap {
    EntryList[] t; // The array of linked lists, which is the core of the hash table.
    int nbEntries; // The total number of key-value pairs stored in the map.

    static void main(String[] args) {
        // Tests on Hmap
    }

    /**
     * Constructs a hash map with a specified initial capacity.
     */
    HMap(int n) {
        t = new EntryList[n];
        nbEntries = 0;
    }

    /**
     * Constructs a hash map with a default initial capacity of 20.
     */
    HMap() {
        t = new EntryList[20];
        nbEntries = 0;
    }

    /**
     * Finds the list of words associated with a given prefix.
     * It computes the hash of the prefix to find the correct "bucket" (index in the array),
     * and then traverses the linked list at that position to find the matching key.
     */
    WordList find(Prefix key) {
        int h = key.hashCode(t.length);
        // Traverse the linked list at the calculated hash index.
        for (EntryList cur = t[h]; cur != null; cur = cur.next) {
            if (Prefix.eq(key, cur.head.key)) {
                return cur.head.value; // Found the matching prefix, return its word list.
            }
        }
        return null; // No entry found for this prefix.
    }

    /**
     * A simple, internal method to add a new word to the list for a given prefix.
     * It doesn't handle rehashing. It finds the correct bucket; if the prefix already
     * exists, it adds the word to its list. If not, it creates a new entry for the prefix.
     */
    void addSimple(Prefix key, String w) {
        int h = key.hashCode(t.length);
        if (t[h] == null) {
            // This bucket is empty, so create a new list with a new entry.
            t[h] = new EntryList(new Entry(key, new WordList(w, null)), null);
            nbEntries++;
        } else {
            // This bucket is not empty, search the list for the key.
            for (EntryList cur = t[h]; cur != null; cur = cur.next) {
                if (Prefix.eq(key, cur.head.key)) {
                    // Found the key, just add the new word to the existing list.
                    cur.head.value.addLast(w);
                    return;
                }
            }
            // If we reach here, the key was not in the list. Add a new entry at the beginning.
            t[h].next = new EntryList(t[h].head, t[h].next);
            t[h].head = new Entry(key, new WordList(w, null));
            nbEntries++;
        }
    }

    /**
     * A utility method to print the contents of the hash map.
     * Useful for debugging to see the internal state of the table, including
     * which keys are in which buckets.
     */
    void print() {
        for (int i = 0; i < t.length; i++) {
            System.out.println(i + ":");
            for (EntryList cur = t[i]; cur != null; cur = cur.next) {
                System.out.println("\t" + cur.head.key + ":");
                System.out.println("\t\t" + cur.head.value + ":");
            }
        }
    }

    /**
     * Resizes the hash table to a new capacity 'n'.
     * This is a crucial operation to keep the hash map efficient. It creates a new, larger
     * array and re-inserts every existing entry into this new array, calculating a new
     * hash for each entry based on the new size.
     */
    void rehash(int n) {
        EntryList[] tNew = new EntryList[n];
        int h;
        // Iterate through all the buckets of the old table.
        for (int i = 0; i < t.length; i++) {
            // Iterate through all the entries in the linked list of each bucket.
            for (EntryList cur = t[i]; cur != null; cur = cur.next) {
                // Calculate the new hash based on the new table size.
                h = cur.head.key.hashCode(n);

                // Insert the entry into the new table.
                if (tNew[h] == null) {
                    tNew[h] = new EntryList(new Entry(cur.head.key, cur.head.value), null);
                } else {
                    tNew[h].next = new EntryList(tNew[h].head, tNew[h].next);
                    tNew[h].head = new Entry(cur.head.key, cur.head.value);
                }
            }
        }
        // Replace the old table with the new, larger one.
        t = tNew;
    }

    /**
     * The main method for adding a key-value pair to the map.
     * It first checks if the map is getting too full (the load factor is > 0.75).
     * If it is, it triggers a `rehash` to double the size of the table.
     * Then, it calls `addSimple` to perform the actual insertion.
     */
    void add(Prefix key, String w) {
        // Check if the load factor exceeds 3/4. If so, expand the table.
        if ((nbEntries + 1) > 3 * t.length / 4) {
            rehash(2 * t.length);
        }
        addSimple(key, w);
    }
}
