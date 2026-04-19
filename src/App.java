public class App {
    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            System.out.println("Arguments:");
            for (String arg : args) {
                System.out.println(arg);
            }
        } else {
            System.out.println("No arguments provided.");
        }
        System.out.println("Hello, World!");
    }
}
