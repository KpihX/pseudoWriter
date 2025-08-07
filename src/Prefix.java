/**
 * Represents a sequence of words, which serves as the key in our Markov chain model.
 * A prefix is a fixed-size window of the last 'n' words encountered in the text.
 * This class is immutable; operations like `addShift` create a new Prefix object
 * instead of modifying the existing one. This is a good practice for keys in a hash map.
 */
public class Prefix {
    String[] t;

    // Special tokens to mark the beginning, end, or a paragraph break in the text.
    final static String start = "<START>", end = "<END>", par = "<PAR>";

    /**
     * Creates a new initial prefix of length 'n'.
     * An initial prefix is filled with "start" tokens, representing the state
     * before any words from the source text have been read.
     */
    Prefix(int n) {
        t = new String[n];
        for (int i=0; i < n; i++) {
            t[i] = start;
        }
    }

    /**
     * A private constructor to create a prefix from an existing array of strings.
     * This is used internally by the `addShift` method.
     */
    private Prefix(String[] tab) {
        t = tab;
    }

    /**
     * A static utility method to compare two prefixes for equality.
     * Two prefixes are considered equal if they have the same length and all
     * their corresponding words are equal.
     */
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

    /**
     * Creates a new prefix by "shifting" the current one.
     * It discards the oldest word, moves all other words one position to the left,
     * and adds the new word 'w' at the end.
     * For example, if prefix is ("the", "quick", "brown") and w is "fox",
     * the new prefix will be ("quick", "brown", "fox").
     */
    Prefix addShift(String w) {
        int l = t.length;
        String[] tab = new String[l];

        // Shift all words one position to the left.
        for (int i = 0; i < l-1; i++) {
            tab[i] = t[i+1];
        }
        
        // Add the new word at the end.
        tab[l-1] = w;
        return new Prefix(tab);
    }

    /**
     * Calculates the hash code for this prefix.
     * The calculation is based on a polynomial hashing formula, treating the sequence
     * of word hashes as digits in a base-37 number. This is a standard and effective
     * way to generate a hash code from a sequence of items, as it is sensitive to
     * the order of the items.
     */
    public int hashCode() {
        int h = 0;
        for (int i = 0; i < t.length; i++) {
            h = 37*h + t[i].hashCode();
        }
        return h;
    }

    /**
     * A convenience method to compute the hash code and then map it to a valid
     * index for a hash table of size 'n'. It ensures the result is non-negative.
     */
    int hashCode(int n) {
        int h = hashCode() % n;
        if (h >= 0) return h;
        return h + n;
    }

    /**
     * Provides a human-readable string representation of the prefix,
     * showing the sequence of words it contains.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(t[0]);
        for (int i = 1; i < t.length; i++) {
            sb.append(", ");
            sb.append(t[i]);
        }

        return sb.toString();
    }
}

