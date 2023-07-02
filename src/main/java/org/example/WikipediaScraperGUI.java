package org.example;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class WikipediaScraperGUI extends JFrame {

    private final JTextField topicTextField;
    private final JTextArea contentTextArea;

    public WikipediaScraperGUI() {
        setTitle("Wikipedia Scraper");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel for input components
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel topicLabel = new JLabel("Enter a Wikipedia topic:");
        topicTextField = new JTextField(20);
        JButton scrapeButton = new JButton("Scrape");

        inputPanel.add(topicLabel);
        inputPanel.add(topicTextField);
        inputPanel.add(scrapeButton);

        // Panel for output component
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());

        JLabel contentLabel = new JLabel("Content:");
        contentTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(contentTextArea);

        outputPanel.add(contentLabel, BorderLayout.NORTH);
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        // Add panels to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);

        // Scrape button action
        scrapeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String topic = topicTextField.getText();
                String url = "https://en.wikipedia.org/wiki/" + topic;

                try {
                    // Fetch the Wikipedia page
                    Document document = Jsoup.connect(url).get();

                    // Extract the main content
                    Element contentElement = document.selectFirst("#mw-content-text");
                    Elements paragraphs = contentElement.select("p");
                    StringBuilder contentBuilder = new StringBuilder();
                    for (Element paragraph : paragraphs) {
                        contentBuilder.append(paragraph.text()).append("\n");
                    }
                    String content = contentBuilder.toString();

                    contentTextArea.setText(content);
                    System.out.println("Scraping completed.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                WikipediaScraperGUI gui = new WikipediaScraperGUI();
                gui.setVisible(true);
            }
        });
    }
}
