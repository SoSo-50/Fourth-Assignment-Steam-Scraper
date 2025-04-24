import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class GameUI {
    private final Parser parser = new Parser();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        try {
            parser.setUp();
            System.out.println(" Games loaded successfully \n");
        } catch (IOException e) {
            System.out.println(" Error loading HTML file: " + e.getMessage());
            return;
        }

        boolean running = true;
        while (running) {
            printMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1 -> displayGames(Parser.getGames());
                case 2 -> displayGames(parser.sortByName());
                case 3 -> displayGames(parser.sortByRating());
                case 4 -> displayGames(parser.sortByPrice());
                case 5 -> {
                    running = false;
                    System.out.println(" Exiting program.");
                }
                default -> System.out.println(" Invalid option.");
            }
        }
    }

    private void printMenu() {
        System.out.println("""
                * Game Menu *
                1. Show all games
                2. Sort by name
                3. Sort by rating(High to Low)
                4. Sort by price(High to Low)
                5. Exit
                """);
        System.out.print("Your choice: ");
    }

    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void displayGames(List<Game> games) {
        System.out.println("\n Game List:");
        for (Game game : games) {
            System.out.println(game);
        }
        System.out.println("──────────────────────────────\n");
    }

    public static void main(String[] args) {
        new GameUI().start();
    }
}
