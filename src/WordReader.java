import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A simple utility class for reading a file word by word.
 * It wraps a `java.util.Scanner` to provide a clean, token-based iteration
 * over the contents of a text file. It treats any sequence of non-whitespace
 * characters as a single word.
 */
class WordReader {
  private Scanner scanner;

  /**
   * Creates a WordReader that reads from a file specified by its path.
   * It sets up a scanner to read the file using the UTF-8 character set.
   */
  public WordReader(String filename) {
    try {
      this.scanner = new Scanner(new File(filename), "UTF-8");
    } catch (FileNotFoundException e) {
      // If the file doesn't exist, wrap the exception in a RuntimeException
      // to avoid forcing the caller to handle a checked exception.
      throw new RuntimeException(e);
    }
  }

  /**
   * An alternative constructor that can read from any `Readable` source,
   * making the class more flexible for testing or other uses (e.g., reading from a String).
   */
  public WordReader(Readable in) {
    this.scanner = new Scanner(in);
  }

  /**
   * Reads and returns the next word from the file.
   * A word is defined by the default delimiter of the `Scanner`, which is whitespace.
   * @return The next word as a String, or `null` if the end of the file is reached.
   */
  public String read() {
    if (this.scanner == null)
      return null;

    if (this.scanner.hasNext())
      return scanner.next();

    // Return null to signal that there are no more words to read.
    return null;
  }

  /**
   * A simple main method for testing the WordReader.
   * It takes a filename as a command-line argument, reads all the words,
   * prints each one on a new line, and finally prints the total word count.
   */
  public static void main(String[] arg) {
    final WordReader wr = new WordReader(arg[0]);
    int counter = 0;

    for (String w = wr.read(); w != null; w = wr.read()) {
      System.out.println("[" + w + "]");
      counter++;
    }
    System.out.println(counter);
  }
}
