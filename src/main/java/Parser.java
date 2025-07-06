import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Parser {
    private static List<Game> games = new ArrayList<>();

    public static List<Game> getGames() {
        return new ArrayList<>(games);
    }

    public static void clearGames() {
        games.clear();
    }

    public List<Game> sortByName() {
        List<Game> sortedByName = new ArrayList<>(games);
        sortedByName.sort(Comparator.comparing(Game::getName));
        return sortedByName;
    }

    public List<Game> sortByRating() {
        List<Game> sortedByRating = new ArrayList<>(games);
        sortedByRating.sort(Comparator.comparing(Game::getRating).reversed());
        return sortedByRating;
    }

    public List<Game> sortByPrice() {
        List<Game> sortedByPrice = new ArrayList<>(games);
        sortedByPrice.sort(Comparator.comparing(Game::getPrice).reversed());
        return sortedByPrice;
    }

    public void setUp() throws IOException {
        File input = new File("src/resources/Video_Games.html");
        Document doc = Jsoup.parse(input, "UTF-8");

        Elements gameElements = doc.select("div.col-md-4.game");

        for (Element gameElement : gameElements) {
            String name = gameElement.selectFirst("h3.game-name").text();

            String ratingText = gameElement.selectFirst("span.game-rating").text();
            double rating = Double.parseDouble(ratingText.split("/")[0]);

            String priceText = gameElement.selectFirst("span.game-price").text();
            double price = Double.parseDouble(priceText.replace("€", "").trim());

            games.add(new Game(name, rating, price));
        }
    }

    public static void main(String[] args) {
        Parser parser = new Parser();
        try {
            parser.setUp();

            System.out.println(" Sorted by Name:");
            for (Game g : parser.sortByName()) {
                System.out.println(g);
            }

            System.out.println("\n Sorted by Rating:");
            for (Game g : parser.sortByRating()) {
                System.out.println(g);
            }

            System.out.println("\n Sorted by Price:");
            for (Game g : parser.sortByPrice()) {
                System.out.println(g);
            }

        } catch (IOException e) {
            System.out.println(" Error reading HTML file: " + e.getMessage());
        }
    }
}
