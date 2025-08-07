import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * This class is the main driver for the pseudo-writer program.
 * It orchestrates the process of building a statistical model from a source text
 * and then generating a new, random text that mimics the style of the original.
 * The core idea is based on a Markov chain, where the probability of the next word
 * depends on a fixed number of preceding words (the "prefix").
 */
public class Bovary {
    /**
     * The entry point of the program. It parses command-line arguments,
     * builds the word-sequence model, and then starts the text generation.
     * It now accepts two arguments:
     * 1. An integer 'n' for the prefix length (the "memory" of the model).
     * 2. The name of the directory inside 'src/' containing the source text files.
     */
    public static void main(String[] args) {
		if (args.length < 2) {
            System.err.println("Usage: java Bovary <prefix_length> <source_directory_name>");
            System.err.println("Example: java Bovary 4 bovary");
            return;
        }

        try {
            // The length of the prefix (number of words to look back).
            int n = Integer.parseInt(args[0]);
            String sourceDirName = args[1];
            File sourceDir = new File("src/" + sourceDirName);

            if (!sourceDir.exists() || !sourceDir.isDirectory()) {
                System.err.println("Error: The specified source directory does not exist: " + sourceDir.getPath());
                return;
            }

            File[] chapterFiles = sourceDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
            if (chapterFiles == null || chapterFiles.length == 0) {
                System.err.println("Error: No .txt files found in the specified directory.");
                return;
            }

            String[] filePaths = new String[chapterFiles.length];
            for (int i = 0; i < chapterFiles.length; i++) {
                filePaths[i] = chapterFiles[i].getPath();
            }

            // Build the hash map which stores the statistical model of the text.
            HMap hmap = buildTable(filePaths, n);
            
            String generatedText = generate(hmap, n);

            // Save the generated text to a new file.
            String newFileName = String.format("%d.txt", chapterFiles.length);
            String newFilePath = sourceDir.getPath() + File.separator + newFileName;
            
            System.out.println("*** Generating new chapter (Chapter " + (chapterFiles.length) + ")... ***\n");
            System.out.println(generatedText);

            try (FileWriter writer = new FileWriter(newFilePath)) {
                writer.write(generatedText);
                System.out.println("\n*** Successfully saved generated text to " + newFilePath + " ***");
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }

        } catch (NumberFormatException e) {
            System.err.println("Error: The first argument (prefix_length) must be an integer.");
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
        for (String file : files) {
            wr = new WordReader(file);
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
     * and appends it to a string builder. This process continues until an "end" marker
     * is chosen, signifying the end of the text.
     * @return The complete generated text as a single string.
     */
    static String generate(HMap t, int n) {
        StringBuilder output = new StringBuilder();
        Prefix pf = new Prefix(n); // Start with an empty prefix.
        Random random = new Random();
        
        while (true) { 
            WordList wl = t.find(pf);
            if (wl == null) { // Safety check if a prefix leads nowhere.
                break;
            }

            int randIndex = random.nextInt(wl.length());
            String wd = wl.toArray()[randIndex];
            
            if (wd.equals(Prefix.end)) {
                break; // End of generation.
            } else if (wd.equals(Prefix.par)) {
                output.append("\n").append(Prefix.par).append("\n");
            } else {
                output.append(wd).append(" ");
            }
            
            pf = pf.addShift(wd);
        }
        return output.toString().trim();
    }
}
