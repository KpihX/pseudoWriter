class WordList {
    Node content;
    static WordList foobar = new WordList("foo", new Node("bar", new Node("baz", null)));

    public static void main(String[] args) {
        // // Tests addAndRemove
        // System.out.println(foobar);
        // foobar.addFirst("kpihx");
        // System.out.println(foobar);
        // foobar.addLast("shadow");
        // System.out.println(foobar);
        // System.out.println(foobar.removeFirst());
        // System.out.println(foobar);
        // System.out.println(foobar.removeLast());
        // System.out.println(foobar);

        // Test of insert
        // WordList wl = new WordList("age", new Node("route", new Node("yoyo", null)));
        // wl.insert("kpihx");
        // System.out.println(wl);

        // Test of WordList(String[] t)
        // WordList wl2 = new WordList(new String[] {});
        // System.out.println(wl2);

        // // Test of String[] toArray()
        // String[] tab = foobar.toArray();
        // for (int i=0; i < tab.length; i++) {
        //     System.out.print(tab[i] + " ");
        // }

        // // Test of mergeSort()
        // foobar.mergeSort();
        // System.out.println("foobar sorted: " + foobar);
    }

    WordList() {
        content = null;
    }

    WordList(Node content) {
        this.content = content;
    }
  
    WordList(String head, Node next) {
        content = new Node(head, next);
    }

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

    int length() {
        return Node.length(content);
    }

    public String toString() {
        return Node.makeString(content);
    }

    void addFirst(String w) {
        if (content == null) {
            content = new Node(w, null);
        } else {
            content.next = new Node(content.head, content.next);
            content.head = w;
        }
    }

    void addLast(String w) {
        if (content == null) {
            content = new Node(w, null);
        } else {
            Node.addLast(w, content);
        }
    }

    String removeFirst() {
        if (content == null) {
            return null;
        } else {
            String firstString = content.head;
            content = content.next;
            return firstString;
        }
    }

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

    void insert(String s) {
        content = Node.insert(s, content);
    }

    void insertionSort() {
        content = Node.insertionSort(content);
    }

    String[] toArray() {
        String[] tab = new String[Node.length(content)];
        int k = 0;
        for (Node cur = content; cur != null; cur = cur.next) {
            tab[k] = cur.head;
            k++;
        }

        return tab;
    }

    String find(int index) {
        int curIndex = 0;
        Node cur = content;

        while ( curIndex < index) {
            cur = cur.next;
        }
        
        return cur.head;
    }

    void mergeSort() {
        int l = Node.length(content);
        if (l < 2) {
            return;
        }

        int i = 0;
        WordList l1 = new WordList(new Node("", null)), l2 = new WordList(new Node("", null));
        Node cur1 = l1.content, cur2 = l2.content;
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

        l1.content = l1.content.next; // We no more consider the first node of ""; added at the beginning
        l2.content = l2.content.next;
        l1.mergeSort();
        l2.mergeSort();
        content = Node.merge(l1.content, l2.content);
    }
}