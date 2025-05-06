public class App {
    public static void main(String[] args) {
		if (args.length == 0) {
            System.err.println("You have to give the number caracterising the fake generation!");
            return;
        }

        try {
            int n = Integer.parseInt(args[0]);

            long start = System.currentTimeMillis();
            long end = System.currentTimeMillis();
            System.out.println("Hi");
        } catch (NumberFormatException e) {
            System.err.println("You have to provide a non null positive integer value for the number pseudo generation!");
        }
    }
}
