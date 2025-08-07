

/**
 * This class defines the fundamental building block of our linked lists.
 * A Node contains a piece of data (a `String` called `head`) and a reference
 * to the next Node in the sequence (`next`). The class also includes a suite of static
 * utility methods for operating on these linked lists, such as calculating length,
 * sorting, and merging.
 */
class Node {
    String head; // The data payload of the node.
    Node next;   // The link to the next node in the list.

    public static void main(String[] args) {
        // Main method for testing purposes.
    }
  
    /**
     * Constructs a new Node with given data and a link to the next node.
     */
    Node(String head, Node next) {
      this.head = head;
      this.next = next;
    }

    /**
     * A recursive method to calculate the length of a list.
     */
    static int lengthRec(Node l) {
        if (l == null) {
            return 0;
        }
        return 1 + lengthRec(l.next);
    }

    /**
     * An iterative method to calculate the length of a list.
     * This is generally preferred over the recursive version to avoid stack overflow on very long lists.
     */
    static int length(Node l) {
        int len = 0;
        for (Node cur = l; cur != null; cur = cur.next) {
            len++;
        }
        return len;
    }

    /**
     * Creates a human-readable string representation of a list, e.g., "[foo, bar, baz]".
     */
    static String makeString(Node l) {
        if (l == null) {
            return "[]";
        }
        StringBuilder str = new StringBuilder();
        str.append("[");
        while (l != null) {
            str.append(l.head);
            str.append(", ");
            l = l.next;
        }
        str.delete(str.length() - 2, str.length()); // Remove the trailing ", "
        str.append("]");
        return str.toString();
    }

    /**
     * Adds a new node with string 's' to the very end of the list.
     * Note: This method assumes the list 'l' is not null.
     */
    static void addLast(String s, Node l) {
        while (l.next != null) {
            l = l.next;
        }
        l.next = new Node(s, null);
    }

    /**
     * Creates a deep copy of a linked list.
     * This is important to ensure that modifications to the copied list do not affect the original.
     */
    static Node copy(Node l) {
        if (l == null) {
            return null;
        }
        Node lCopy = new Node(l.head, null);
        Node iter = lCopy;
        for (Node cur = l.next; cur != null; cur = cur.next) {
            iter.next = new Node(cur.head, null);
            iter = iter.next;
        }
        return lCopy;
    }

    /**
     * Inserts a string 's' into a sorted list 'l' while maintaining the sorted order.
     * Note: This implementation has some complexity and might not be the most efficient.
     */
    static Node insert(String s, Node l) {
        Node lInit = l;
        if (l == null) {
            return new Node(s, null); 
        }

        while (l.next != null && l.head.compareTo(s) < 0) {
            l = l.next;
        }

        if (l.next == null) {
            if (l.head.compareTo(s) <= 0) {
                l.next = new Node(s, null);
            } else {
                l.next = new Node(l.head, null);
                l.head = s;
            }
        } else {
            l.next = new Node(l.head, l.next);
            l.head = s;
        }

        return copy(lInit);
    }

    /**
     * Sorts a list using the insertion sort algorithm.
     * It builds the sorted list one element at a time by repeatedly calling the `insert` method.
     */
    static Node insertionSort(Node l) {
        Node lSorted = null;
        for (Node cur = copy(l); cur != null; cur = cur.next) {
            lSorted = insert(cur.head, lSorted);
        }
        return lSorted;
    }

    /**
     * Merges two already sorted lists, `l1` and `l2`, into a single sorted list.
     * This is a key helper function for the merge sort algorithm.
     */
    static Node merge(Node l1, Node l2) {
        Node l = new Node("", null); // Dummy head node to simplify insertion.
        Node lCur = l;
        Node cur1 = l1;
        Node cur2 = l2;
        // Traverse both lists, picking the smaller element at each step.
        while (cur1 != null && cur2 != null) {
            lCur.next = new Node("", null);
            if (cur1.head.compareTo(cur2.head) < 0) {
                lCur.next.head = cur1.head;
                cur1 = cur1.next;
            } else {
                lCur.next.head = cur2.head;
                cur2 = cur2.next;
            }
            lCur = lCur.next;
        }
        // If one list is exhausted, append the remainder of the other list.
        if (cur1 == null) {
            lCur.next = Node.copy(cur2);
        } else { // cur2 == null
            lCur.next = Node.copy(cur1);
        }
        return l.next; // Return the list starting from after the dummy head.
    }
  }