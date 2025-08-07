/**
 * This class implements a node for a linked list where the data is an `Entry`.
 * This is used to build the "buckets" for the `HMap` class. When multiple keys
 * hash to the same index in the hash table, their corresponding `Entry` objects
 * are stored in a linked list at that index. This technique is known as
 * separate chaining.
 */
class EntryList {
    Entry head;      // The data payload, which is a key-value pair (Prefix -> WordList).
    EntryList next;  // The link to the next EntryList node in the bucket.
  
    /**
     * Constructs a new EntryList node.
     */
    EntryList(Entry head, EntryList next) {
      this.head = head;
      this.next = next;
    }
  }