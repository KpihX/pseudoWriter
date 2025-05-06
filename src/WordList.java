class WordList {
    Node content;

    public static void main(String[] args) {
        WordList foobar = new WordList("foo", new Node("bar", new Node("baz", null)));
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
        if (content == null) {
            return null;
        } else {
            while (content.next != null) {
                content = content.next;
            }

            String lastString = content.head;
            content = null;
            return  lastString;
        }
    }

    void insert(String s) {
        Node.insert(s, content);
    }

    void insertionSort() {
        Node.insertionSort(content);
    }
}