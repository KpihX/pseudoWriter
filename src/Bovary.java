import java.util.Random;

public class Bovary {
    public static void main(String[] args) {
		if (args.length == 0) {
            System.err.println("You have to give the number caracterising the fake generation!");
            return;
        }

        try {
            int n = Integer.parseInt(args[0]);
            int nbFiles = 35;
            String parent = "src/bovary/";

            String[] files = new String[nbFiles];
            for (int i = 1; i <= nbFiles; i++) {
                if (i < 10) {
                    files[i-1] = parent + "0" + i + ".txt";
                } else {
                    files[i-1] = parent + i + ".txt";
                }
            }

            HMap hmap = buildTable(files, n);
            // System.out.println(hmap.find(new Prefix(n)).toString());
            // hmap.print();
            System.out.println("*** Le 36e chapitre de Mme Bovary ***\n");
            generate(hmap, n);
        } catch (NumberFormatException e) {
            System.err.println("You have to provide a non null positive integer value for the number pseudo generation!");
        }
    }

    static HMap buildTable(String[] files, int n) {
        WordReader wr;
        Prefix pf;
        HMap hmap = new HMap();
        for (int i = 0; i < files.length; i++) {
            wr = new WordReader(files[i]);
            pf = new Prefix(n);
            for (String w = wr.read(); w != null; w = wr.read()) {
                hmap.add(pf, w);
                // System.out.println("* " + w);
                // System.out.println("** " + pf);
                pf = pf.addShift(w);
                // System.out.println("*** " + pf);
            }
            hmap.add(pf, Prefix.end);
            // System.out.println("**** " + pf);
        }

        return hmap;
    }

    static void generate(HMap t, int n) {
        Prefix pf = new Prefix(n);
        Random random = new Random();
        int randIndex;
        WordList wl;
        String wd;
        do { 
            wl = t.find(pf);
            randIndex = random.nextInt(wl.length());
            wd = wl.toArray()[randIndex];
            // wd = wl.find(randIndex);
            if (wd.equals(Prefix.end)) {
                break;
            } else if (wd.equals(Prefix.par)) {
                System.out.println();
            } else {
                System.out.print(wd + " ");
            }
            
            pf = pf.addShift(wd);
        } while (true);
        System.out.println();
    }
}
