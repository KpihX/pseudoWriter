# ✍️ PseudoWriter: A Markov Chain Text Generator

This project is distributed under the [ABU License](LICENSE).

PseudoWriter is a Java program that generates new text in the style of a source text. It uses a simple but powerful statistical model known as a Markov chain. By analyzing the sequence of words in a provided text, it can create a new, pseudo-random "chapter" that often captures the tone, vocabulary, and rhythm of the original author.

## 🧠 The Core Concept: Markov Chains

The principle behind PseudoWriter is to model language as a sequence of probabilistic events. Instead of looking at the entire text at once, a Markov model looks at a small, fixed-size window of words, called a **prefix**.

1. **📚 Learning Phase**: The program reads the source text and builds a statistical map. For every unique prefix of length `n` that it finds, it records all the words that immediately follow that prefix.

   * For example, with a prefix length of 2, if the text contains "the cat sat on the mat", the program learns:
     * After `("the", "cat")`, the word `"sat"` can appear.
     * After `("cat", "sat")`, the word `"on"` can appear.
     * ...
2. **✍️ Generation Phase**: To create new text, the program starts with an initial prefix (e.g., two "start" markers). It then:

   * Looks up this prefix in its statistical map.
   * Chooses one of the possible following words at random.
   * Prints the chosen word.
   * Updates the prefix by dropping the first word and adding the newly chosen one.
   * Repeats the process.

### ✨ The Magic of Memory (Prefix Length)

The **prefix length** (the `n` parameter) is the most important setting. It determines the "memory" of the generator:

* **Small `n` (e.g., 1 or 2)**: The generator has very little context. The text will be grammatically messy and nonsensical, but it will use the right vocabulary. It's fascinating how, at first glance, the generated text might appear coherent, but a closer look reveals its lack of true meaning or logical flow, especially with smaller `n` values.
* **Large `n` (e.g., 4 or 5)**: The generator has a much deeper memory. It can remember longer phrases and sentence structures, leading to text that is surprisingly coherent and stylistically similar to the original. It can even generate entire sentences copied directly from the source text if that sequence is the only one it knows for a given prefix.

## 🚀 How to Compile and Run

### Compilation

From the root directory (`pseudoWriter/`), compile all the Java source files into the `bin` directory:

```bash
javac -d bin src/*.java
```

### Running the Generator

To run the program, you need to provide two arguments from the command line:

1. `prefix_length`: An integer for the desired prefix length (memory). A value of **4** is a great starting point.
2. `source_directory`: The name of the folder inside `src/` that contains your source text files.

**Example (using the provided *Madame Bovary* text):**

```bash
java -cp .\bin\ Bovary 4 bovary
```

This command tells the program to:

* Use a prefix length of **4**.
* Read the source text files from the `src/bovary/` directory.

The program will then print the newly generated chapter to the console and also save it as a new file (e.g., `src/bovary/36.txt`) so it can be used in subsequent generations.

## ⚡️ Automatiser l'exécution dans Visual Studio Code

Pour lancer automatiquement le programme avec des arguments prédéfinis dans VS Code (par exemple via le bouton « Run »), il suffit d'ajouter ou de modifier la configuration dans le fichier `.vscode/launch.json` :

```jsonc
{
    "type": "java",
    "name": "Bovary",
    "request": "launch",
    "mainClass": "Bovary",
    "projectName": "pseudoWriter_xxxxxxxx",
    "args": "4 bovary"
}
```

* Remplacez `pseudoWriter_xxxxxxxx` par le nom exact de votre projet si besoin (visible dans les autres configurations du fichier).
* L’option `"args": "4 bovary"` permet de lancer automatiquement le programme avec ces paramètres à chaque exécution via le triangle « Run » de VS Code.

Ainsi, il n’est plus nécessaire de saisir les arguments à la main dans le terminal : l’exécution est immédiate et reproductible.

### 📜 Example Output (with `n=4`)

Here is an example of output generated from *Madame Bovary*. Notice how the sentences flow logically and maintain Flaubert's descriptive style, even though they are algorithmically generated:

> ```
> *** Le 36e chapitre de Mme Bovary ***
>
> Ce fut un dimanche de février, une après-midi qu'il neigeait.
> Ils étaient tous, M. et madame Bovary, Homais et M. Léon, partis voir, à une demi-lieue d'Yonville, dans la vallée, une filature de lin que l'on établissait. L'apothicaire avait amené avec lui Napoléon et Athalie, pour leur faire faire de l'exercice, et Justin les accompagnait, portant des parapluies sur son épaule.
> Rien pourtant n'était moins curieux que cette curiosité. Un grand espace de terrain vide, où se trouvaient pêle-mêle, entre des tas de sable et de cailloux, quelques roues d'engrenage déjà rouillées, entourait un long bâtiment quadrangulaire que perçaient quantité de petites fenêtres. Il n'était pas achevé d'être bâti, et l'on voyait le ciel à travers les lambourdes de la toiture. Attaché à la poutrelle du pignon, un bouquet de paille entremêlé d'épis faisait claquer au vent ses rubansu, le col de la chemise, un peu lâche, laissait voir la peau ; un bout d'oreille dépassait sous une mèche de cheveux, et son grand oeil bleu, levé vers les nuages, parut à Emma plus limpide et plus beau que ces lacs des montagnes où le ciel se mire...
> ```

## 🎨 Using Your Own Text

You can easily train the generator on any text you like.

1. **Create a new directory** inside the `src/` folder (e.g., `src/my_text/`).
2. **Prepare your source text**: It must be plain text (`.txt`). For best results, split your text into multiple smaller files (e.g., chapters or sections). The program will read all `.txt` files in the directory you provide.
3. **Formatting**: The program reads words based on whitespace. To mark a paragraph break, simply insert the special marker `<PAR>` on its own line or surrounded by spaces.
4. **Run the program**, pointing it to your new directory:

   ```bash
   java -cp .\bin\ Bovary 3 my_text
   ```
