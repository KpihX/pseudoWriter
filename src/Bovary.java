import java.util.Random;

/**
 * This class is the main driver for the pseudo-writer program.
 * It orchestrates the process of building a statistical model from a source text
 * (Gustave Flaubert's "Madame Bovary") and then generating a new, random text
 * that mimics the style of the original. The core idea is based on a Markov chain,
 * where the probability of the next word depends on a fixed number of preceding words (the "prefix").
 */
public class Bovary {
    /**
     * The entry point of the program. It parses the command-line arguments,
     * builds the word-sequence model, and then starts the text generation.
     * The main argument it expects is an integer 'n', which defines the length
     * of the prefix used for the Markov chain.
     */
    public static void main(String[] args) {
		if (args.length == 0) {
            System.err.println("You have to give the number caracterising the fake generation!");
            return;
        }

        try {
            // The length of the prefix (number of words to look back).
            int n = Integer.parseInt(args[0]);
            int nbFiles = 35; // The Bovary text is split into 35 chapter files.
            String parent = "src/bovary/";

            // Assemble the list of file paths for all chapters.
            String[] files = new String[nbFiles];
            for (int i = 1; i <= nbFiles; i++) {
                if (i < 10) {
                    files[i-1] = parent + "0" + i + ".txt";
                } else {
                    files[i-1] = parent + i + ".txt";
                }
            }

            // Build the hash map which stores the statistical model of the text.
            HMap hmap = buildTable(files, n);
            
            System.out.println("*** Le 36e chapitre de Mme Bovary ***\n");
            
            // Generate and print the new text.
            generate(hmap, n);
        } catch (NumberFormatException e) {
            System.err.println("You have to provide a non null positive integer value for the number pseudo generation!");
        }
    }

    /**
     * Reads through all the provided text files and builds a hash map that represents
     * the Markov chain. The map's keys are prefixes (sequences of 'n' words) and
     * the values are lists of words that have been observed to follow that prefix in the text.
     */
    static HMap buildTable(String[] files, int n) {
        WordReader wr;
        Prefix pf;
        HMap hmap = new HMap();
        // Iterate over each chapter file.
        for (int i = 0; i < files.length; i++) {
            wr = new WordReader(files[i]);
            pf = new Prefix(n); // Start with a fresh prefix for each file.
            
            // Read the file word by word.
            for (String w = wr.read(); w != null; w = wr.read()) {
                // For the current prefix, record that 'w' is a possible next word.
                hmap.add(pf, w);
                // Shift the prefix to include the new word for the next iteration.
                pf = pf.addShift(w);
            }
            // At the end of each file, mark that the sequence can end here.
            hmap.add(pf, Prefix.end);
        }

        return hmap;
    }

    /**
     * Generates a new text based on the statistical model stored in the hash map.
     * It starts with an initial empty prefix, randomly chooses a successor word,
     * prints it, and then updates the prefix with the chosen word. This process
     * continues until an "end" marker is chosen, signifying the end of the text.
     */
    static void generate(HMap t, int n) {
        Prefix pf = new Prefix(n); // Start with an empty prefix.
        Random random = new Random();
        int randIndex;
        WordList wl;
        String wd;
        do { 
            // Find all possible words that can follow the current prefix.
            wl = t.find(pf);
            
            // Choose one of these words at random.
            randIndex = random.nextInt(wl.length());
            wd = wl.toArray()[randIndex];
            
            // If the chosen word is the end marker, we stop.
            if (wd.equals(Prefix.end)) {
                break;
            } else if (wd.equals(Prefix.par)) {
                // If it's a paragraph marker, print a new line.
                System.out.println();
            } else {
                System.out.print(wd + " ");
            }
            
            // Update the prefix for the next step.
            pf = pf.addShift(wd);
        } while (true);
        System.out.println();
    }
}
