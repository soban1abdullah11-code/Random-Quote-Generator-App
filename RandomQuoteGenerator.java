import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.*;

public class RandomQuoteGenerator extends JFrame {

    // ================= MODEL =================
    static class Quote {
        String text;
        String author;
        String category;

        Quote(String text, String author, String category) {
            this.text = text;
            this.author = author;
            this.category = category;
        }
    }

    private final java.util.List<Quote> quotes = new ArrayList<>();
    private final java.util.List<Quote> history = new ArrayList<>();
    private final Set<String> favorites = new HashSet<>();

    private Quote currentQuote;
    private final Random random = new Random();

    // ================= UI =================
    private JPanel rootPanel;
    private JPanel cardPanel;

    private JLabel quoteLabel;
    private JLabel authorLabel;
    private JLabel categoryLabel;
    private JLabel statusLabel;

    private boolean darkMode = false;

    // ================= CONSTRUCTOR =================
    public RandomQuoteGenerator() {

        setTitle("Random Quote Generator");
        setSize(500, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        loadQuotes();
        initializeUI();

        showRandomQuote();
    }

    // ================= QUOTES =================
    private void loadQuotes() {

        quotes.add(new Quote(
                "Stay hungry, stay foolish.",
                "Steve Jobs",
                "Motivation"));

        quotes.add(new Quote(
                "Success is not final, failure is not fatal.",
                "Winston Churchill",
                "Success"));

        quotes.add(new Quote(
                "Believe you can and you're halfway there.",
                "Theodore Roosevelt",
                "Motivation"));

        quotes.add(new Quote(
                "Life is really simple, but we insist on making it complicated.",
                "Confucius",
                "Life"));

        quotes.add(new Quote(
                "Knowledge is power.",
                "Francis Bacon",
                "Education"));

        quotes.add(new Quote(
                "Dream big and dare to fail.",
                "Norman Vaughan",
                "Success"));

        quotes.add(new Quote(
                "The future depends on what you do today.",
                "Mahatma Gandhi",
                "Inspiration"));

        quotes.add(new Quote(
                "Do what you can, with what you have, where you are.",
                "Theodore Roosevelt",
                "Motivation"));

        quotes.add(new Quote(
                "The best way to predict the future is to create it.",
                "Peter Drucker",
                "Leadership"));

        quotes.add(new Quote(
                "Learning never exhausts the mind.",
                "Leonardo da Vinci",
                "Education"));
    }

    // ================= UI =================
    private void initializeUI() {

        rootPanel = new JPanel(new BorderLayout(10, 10));

        // ===== TOP BAR =====
        JPanel topBar = new JPanel();

        JLabel title = new JLabel("💡 RANDOM QUOTE GENERATOR");
        title.setFont(new Font("Arial", Font.BOLD, 20));

        topBar.add(title);

        // ===== CARD =====
        cardPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        cardPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        40, 25, 40, 25));

        quoteLabel = new JLabel("", SwingConstants.CENTER);
        quoteLabel.setFont(
                new Font("Serif", Font.ITALIC, 22));

        authorLabel = new JLabel("", SwingConstants.CENTER);
        authorLabel.setFont(
                new Font("Arial", Font.BOLD, 16));

        categoryLabel = new JLabel("", SwingConstants.CENTER);
        categoryLabel.setFont(
                new Font("Arial", Font.PLAIN, 13));

        cardPanel.add(quoteLabel);
        cardPanel.add(authorLabel);
        cardPanel.add(categoryLabel);

        // ===== BUTTONS =====
        JPanel buttonPanel = new JPanel(
                new GridLayout(2, 3, 8, 8));

        JButton newQuoteBtn = createButton("New Quote");
        JButton favoriteBtn = createButton("Favorite");
        JButton copyBtn = createButton("Copy");
        JButton historyBtn = createButton("History");
        JButton themeBtn = createButton("Theme");
        JButton exitBtn = createButton("Exit");

        newQuoteBtn.addActionListener(e -> showRandomQuote());
        favoriteBtn.addActionListener(e -> toggleFavorite());
        copyBtn.addActionListener(e -> copyQuote());
        historyBtn.addActionListener(e -> showHistory());
        themeBtn.addActionListener(e -> toggleTheme());
        exitBtn.addActionListener(e -> System.exit(0));

        buttonPanel.add(newQuoteBtn);
        buttonPanel.add(favoriteBtn);
        buttonPanel.add(copyBtn);
        buttonPanel.add(historyBtn);
        buttonPanel.add(themeBtn);
        buttonPanel.add(exitBtn);

        // ===== STATUS BAR =====
        statusLabel = new JLabel(
                "Ready",
                SwingConstants.CENTER);

        // ===== BOTTOM =====
        JPanel bottomPanel = new JPanel(
                new BorderLayout());

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        rootPanel.add(topBar, BorderLayout.NORTH);
        rootPanel.add(cardPanel, BorderLayout.CENTER);
        rootPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(rootPanel);

        applyTheme();
    }

    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Arial", Font.BOLD, 12));

        return button;
    }

    // ================= RANDOM QUOTE =================
    private void showRandomQuote() {

        if (quotes.isEmpty()) {
            return;
        }

        Quote previous = currentQuote;

        do {
            currentQuote =
                    quotes.get(
                            random.nextInt(quotes.size()));
        }
        while (quotes.size() > 1
                && currentQuote == previous);

        quoteLabel.setText(
                "<html><center><b>\""
                        + currentQuote.text
                        + "\"</b></center></html>");

        authorLabel.setText(
                "— " + currentQuote.author);

        categoryLabel.setText(
                "[" + currentQuote.category + "]");

        history.add(currentQuote);

        statusLabel.setText(
                "Quote Loaded Successfully");
    }

    // ================= FAVORITES =================
    private void toggleFavorite() {

        if (currentQuote == null)
            return;

        if (favorites.contains(currentQuote.text)) {

            favorites.remove(currentQuote.text);

            statusLabel.setText(
                    "Removed From Favorites");

        } else {

            favorites.add(currentQuote.text);

            statusLabel.setText(
                    "Added To Favorites");
        }
    }

    // ================= COPY =================
    private void copyQuote() {

        if (currentQuote == null)
            return;

        String text =
                currentQuote.text
                        + " - "
                        + currentQuote.author;

        StringSelection selection =
                new StringSelection(text);

        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(selection, null);

        statusLabel.setText("Quote Copied");
    }

    // ================= HISTORY =================
    private void showHistory() {

        JTextArea area = new JTextArea();
        area.setEditable(false);

        for (Quote q : history) {

            area.append(
                    q.text
                            + " - "
                            + q.author
                            + "\n\n");
        }

        JScrollPane scroll =
                new JScrollPane(area);

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "Quote History",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // ================= THEME =================
    private void toggleTheme() {

        darkMode = !darkMode;

        applyTheme();

        statusLabel.setText(
                darkMode
                        ? "Dark Mode Enabled"
                        : "Light Mode Enabled");
    }

    private void applyTheme() {

        Color bg =
                darkMode
                        ? new Color(25, 25, 25)
                        : Color.WHITE;

        Color cardBg =
                darkMode
                        ? new Color(40, 40, 40)
                        : new Color(240, 240, 240);

        Color fg =
                darkMode
                        ? Color.WHITE
                        : Color.BLACK;

        rootPanel.setBackground(bg);
        cardPanel.setBackground(cardBg);

        quoteLabel.setForeground(fg);
        authorLabel.setForeground(fg);
        categoryLabel.setForeground(fg);
        statusLabel.setForeground(fg);

        repaint();
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            }
            catch (Exception ignored) {
            }

            new RandomQuoteGenerator()
                    .setVisible(true);
        });
    }
}