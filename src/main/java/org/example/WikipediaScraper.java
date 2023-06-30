package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class WikipediaScraper {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a Wikipedia topic: ");
        String topic = scanner.nextLine();
        scanner.close();

        String url = "https://en.wikipedia.org/wiki/" + topic;

        try {
            // Fetch the Wikipedia page
            Document document = Jsoup.connect(url).get();

            // Extract the title
            String title = document.title();

            // Extract the main content
            Element contentElement = document.selectFirst("#mw-content-text");
            Elements paragraphs = contentElement.select("p");
            StringBuilder contentBuilder = new StringBuilder();
            for (Element paragraph : paragraphs) {
                contentBuilder.append(paragraph.text()).append("\n");
            }
            String content = contentBuilder.toString();

            // Save the content to a text file
            String fileName = topic.replaceAll("\\s+", "_") + ".txt";
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write("Title: " + title + "\n\n");
            fileWriter.write("Content:\n" + content);
            fileWriter.close();

            System.out.println("Scraping completed. The content has been saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}