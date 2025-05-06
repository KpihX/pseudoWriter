

class Node {
    String head;
    Node next;

    public static void main(String[] args) {
        Node foobar = new Node("foo", new Node("bar", new Node("baz", null)));
        // Test lengthRec
        // System.out.println("LengthRec of foobar: " + lengthRec(foobar));

        // // Test length
        // System.out.println("Length of foobar: " + length(foobar));

        // // Test of makeString
        // System.err.println("String version of foobar: " + makeString(foobar));

        // Test of addLast
        addLast("qux", foobar);
        System.err.println("foobar2: " + makeString(foobar));

        // // Test of copy
        // Node foobarCopy = copy(foobar);
        // foobarCopy.head = "kpihx";
        // System.err.println("foobar after copy: " + makeString(foobar));
        // System.err.println("foobarCopy: " + makeString(foobarCopy));

        // // Test insert
        // Node l = new Node("age", new Node("kiwi", new Node("yoyo", null)));
        // Node lI = insert("kpihx", l);
        // System.err.println("l: " + makeString(l));
        // System.err.println("l after insertion: " + makeString(lI));

        // Test insert
        Node l2 = new Node("kiwi", new Node("yoyo", new Node("age", null)));
        Node l2Sorted = insertionSort(l2);
        System.err.println("l2: " + makeString(l2));
        System.err.println("l2 sorted: " + makeString(l2Sorted));

    }
  
    Node(String head, Node next) {
      this.head = head;
      this.next = next;
    }

    static int lengthRec(Node l) {
        if (l == null) {
            return 0;
        }

        return 1 + lengthRec(l.next);
    }

    static int length(Node l) {
        int len = 0;
        for (Node cur = l; cur != null; cur = cur.next) {
            len++;
        }

        return len;
    }

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
        str.delete(str.length() - 2, str.length()); // We remove the last ", ", since it's useless at the end
        str.append("]");
        return str.toString();
    }

    static void addLast(String s, Node l) {
        // We suppose that l != null
        while (l.next != null) {
            l = l.next;
        }
        l.next = new Node(s, null);
    }

    static Node copy(Node l) {
        if (l == null) {
            // throw new Error("the given list mustn't be empty!");
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

    static Node insertionSort(Node l) {
        Node lSorted = null;
        for (Node cur = copy(l); cur != null; cur = cur.next) {
            lSorted = insert(cur.head, lSorted);
        }

        return lSorted;
    }
  }