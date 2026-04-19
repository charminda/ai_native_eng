public class App {
    public static void main(String[] args) throws Exception {
        // Issue: Hardcoded credentials (should be flagged in code review)
        String username = "admin";
        String password = "P@ssw0rd123";

        if (args.length > 0) {
            System.out.println("Arguments:");
            for (String arg : args) {
                System.out.println(arg);
            }
        } else {
            System.out.println("No arguments provided.");
        }
        System.out.println("Hello, World " + username + "!");
    }
}
