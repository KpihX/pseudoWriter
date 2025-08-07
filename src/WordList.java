/**
 * Represents a list of words, implemented as a wrapper around a linked list of `Node` objects.
 * This class provides various methods for manipulating the list, such as adding, removing,
 * sorting, and converting the list to an array. It serves as the "value" in our main hash map,
 * holding all possible words that can follow a specific prefix.
 */
class WordList {
    Node content; // The head of the linked list.
    static WordList foobar = new WordList("foo", new Node("bar", new Node("baz", null)));

    public static void main(String[] args) {
        // Main method for testing purposes.
    }

    /**
     * Constructs an empty WordList.
     */
    WordList() {
        content = null;
    }

    /**
     * Constructs a WordList from an existing Node, making it the head of the new list.
     */
    WordList(Node content) {
        this.content = content;
    }
  
    /**
     * Constructs a WordList with a single initial word and a reference to the rest of the list.
     */
    WordList(String head, Node next) {
        content = new Node(head, next);
    }

    /**
     * Constructs a WordList from an array of strings, converting the array into a linked list.
     */
    WordList(String[] t) {
        if (t.length == 0) {
            content = null;
            return;
        }

        content = new Node(t[0], null);
        Node cur = content;
        for (int i = 1; i < t.length; i++) {
            cur.next = new Node(t[i], null);
            cur = cur.next;
        }
    }

    /**
     * Returns the number of words in the list.
     */
    int length() {
        return Node.length(content);
    }

    /**
     * Returns a string representation of the list.
     */
    public String toString() {
        return Node.makeString(content);
    }

    /**
     * Adds a word to the beginning of the list.
     */
    void addFirst(String w) {
        if (content == null) {
            content = new Node(w, null);
        } else {
            content.next = new Node(content.head, content.next);
            content.head = w;
        }
    }

    /**
     * Adds a word to the end of the list.
     */
    void addLast(String w) {
        if (content == null) {
            content = new Node(w, null);
        } else {
            Node.addLast(w, content);
        }
    }

    /**
     * Removes and returns the first word from the list.
     */
    String removeFirst() {
        if (content == null) {
            return null;
        } else {
            String firstString = content.head;
            content = content.next;
            return firstString;
        }
    }

    /**
     * Removes and returns the last word from the list.
     */
    String removeLast() {
        Node contentMod = content;
        if (contentMod == null) {
            return null;
        } else if (contentMod.next == null) {
            String lastString = contentMod.head;
            content = null;
            return lastString;
        } else {
            while (contentMod.next.next != null) {
                contentMod = contentMod.next;
            }

            String lastString = contentMod.next.head;
            contentMod.next = null;
            return  lastString;
        }
    }

    /**
     * Inserts a string into the list, maintaining sorted order.
     */
    void insert(String s) {
        content = Node.insert(s, content);
    }

    /**
     * Sorts the list using the insertion sort algorithm.
     */
    void insertionSort() {
        content = Node.insertionSort(content);
    }

    /**
     * Converts the linked list into a String array.
     * This is particularly useful for the text generation part, where we need to
     * randomly select a word by its index.
     */
    String[] toArray() {
        String[] tab = new String[Node.length(content)];
        int k = 0;
        for (Node cur = content; cur != null; cur = cur.next) {
            tab[k] = cur.head;
            k++;
        }

        return tab;
    }

    /**
     * Finds and returns the word at a specific index in the list.
     */
    String find(int index) {
        int curIndex = 0;
        Node cur = content;

        while ( curIndex < index) {
            cur = cur.next;
            curIndex++; 
        }
        
        return cur.head;
    }

    /**
     * Sorts the list using the merge sort algorithm.
     * This is a recursive, divide-and-conquer algorithm that is generally more
     * efficient than insertion sort for larger lists.
     */
    void mergeSort() {
        int l = Node.length(content);
        if (l < 2) {
            return; // A list with 0 or 1 elements is already sorted.
        }

        int i = 0;
        // Create two empty sublists.
        WordList l1 = new WordList(new Node("", null)), l2 = new WordList(new Node("", null));
        Node cur1 = l1.content, cur2 = l2.content;
        
        // Split the current list into two halves.
        for (Node cur=content; cur != null; cur = cur.next) {
            if (2 * i < l) {
                cur1.next = new Node(cur.head, null);
                cur1 = cur1.next;
            } else {
                cur2.next = new Node(cur.head, null);
                cur2 = cur2.next;
            }
            i++;
        }

        // Clean up the dummy head nodes.
        l1.content = l1.content.next;
        l2.content = l2.content.next;
        
        // Recursively sort the sublists.
        l1.mergeSort();
        l2.mergeSort();
        
        // Merge the two sorted sublists back together.
        content = Node.merge(l1.content, l2.content);
    }
}