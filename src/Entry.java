/**
 * A simple container class that represents a key-value pair for our hash map.
 * It links a `Prefix` (the key) to a `WordList` (the value), where the WordList
 * contains all the words that can follow the given prefix.
 * This is essentially a node in the hash map's data structure.
 */
class Entry {
    Prefix key;
    WordList value;
  
    Entry (Prefix key, WordList value) {
      this.key = key;
      this.value = value;
    }
  }