import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.UUID;


public class GameCatalogGUI extends JFrame {
    private GameManager gameManager;
    private JTable gameTable;
    private JTextField titleField, genreField, developerField, releaseDateField, ratingField;
    private JLabel imageLabel;
    private String currentImagePath;


    public GameCatalogGUI() {
        gameManager = new GameManager();
        setupUI();
    }

    private void setupUI() {
        setTitle("Game Catalog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Create main panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());


        setupInputFields(inputPanel);


        JButton addButton = new JButton("Add Game");
        JButton updateButton = new JButton("Update Game");
        JButton deleteButton = new JButton("Delete Game");
        JButton clearButton = new JButton("Clear Fields");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);


        String[] columns = {"ID", "Title", "Genre", "Developer", "Release Date", "Rating"};
        gameTable = new JTable(new String[0][6], columns);
        JScrollPane scrollPane = new JScrollPane(gameTable);


        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addGame());
        updateButton.addActionListener(e -> updateGame());
        deleteButton.addActionListener(e -> deleteGame());
        clearButton.addActionListener(e -> clearFields());

        add(mainPanel);
        setLocationRelativeTo(null);
    }

    private void setupInputFields(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);


        titleField = new JTextField(20);
        genreField = new JTextField(20);
        developerField = new JTextField(20);
        releaseDateField = new JTextField(20);
        ratingField = new JTextField(20);


        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(100, 100));
        JButton selectImageButton = new JButton("Select Image");
        selectImageButton.addActionListener(e -> selectImage());


        addLabelAndField(panel, "Title:", titleField, gbc, 0);
        addLabelAndField(panel, "Genre:", genreField, gbc, 1);
        addLabelAndField(panel, "Developer:", developerField, gbc, 2);
        addLabelAndField(panel, "Release Date:", releaseDateField, gbc, 3);
        addLabelAndField(panel, "Rating:", ratingField, gbc, 4);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(selectImageButton, gbc);

        gbc.gridy = 6;
        panel.add(imageLabel, gbc);
    }

    private void addLabelAndField(JPanel panel, String labelText, JTextField field,
                                  GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void selectImage() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            currentImagePath = selectedFile.getAbsolutePath();
            ImageIcon icon = new ImageIcon(currentImagePath);
            Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(image));
        }
    }

    private void addGame() {
        try {
            GameData game = createGameFromInputs();
            gameManager.addGame(game);
            refreshTable();
            clearFields();
            JOptionPane.showMessageDialog(this, "Game added successfully!");
        } catch (GameException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateGame() {
        int selectedRow = gameTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a game to update");
            return;
        }

        try {
            String id = (String) gameTable.getValueAt(selectedRow, 0);
            GameData game = createGameFromInputs();
            game.setId(id);
            gameManager.updateGame(id, game);
            refreshTable();
            clearFields();
            JOptionPane.showMessageDialog(this, "Game updated successfully!");
        } catch (GameException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteGame() {
        int selectedRow = gameTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a game to delete");
            return;
        }

        try {
            String id = (String) gameTable.getValueAt(selectedRow, 0);
            gameManager.deleteGame(id);
            refreshTable();
            clearFields();
            JOptionPane.showMessageDialog(this, "Game deleted successfully!");
        } catch (GameException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private GameData createGameFromInputs() throws NumberFormatException {
        String id = UUID.randomUUID().toString();
        double rating = Double.parseDouble(ratingField.getText());

        return new GameData(
                id,
                titleField.getText(),
                genreField.getText(),
                developerField.getText(),
                releaseDateField.getText(),
                rating,
                currentImagePath
        );
    }

    private void refreshTable() {
        java.util.List<GameData> games = gameManager.getAllGames();
        String[][] data = new String[games.size()][7];

        for (int i = 0; i < games.size(); i++) {
            GameData game = games.get(i);
            data[i][0] = game.getId();
            data[i][1] = game.getTitle();
            data[i][2] = game.getGenre();
            data[i][3] = game.getDeveloper();
            data[i][4] = game.getReleaseDate();
            data[i][5] = String.valueOf(game.getRating());
            data[i][6] = game.getImagePath();
        }

        gameTable.setModel(new javax.swing.table.DefaultTableModel(
                data,
                new String[]{"ID", "Title", "Genre", "Developer", "Release Date", "Rating", "Image"}
        ));
        gameTable.setRowHeight(150);

        gameTable.getColumnModel().getColumn(6).setCellRenderer(new ImageTableRenderer() {
        });
    }

    private void clearFields() {
        titleField.setText("");
        genreField.setText("");
        developerField.setText("");
        releaseDateField.setText("");
        ratingField.setText("");
        currentImagePath = null;
        imageLabel.setIcon(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameCatalogGUI gui = new GameCatalogGUI();
            gui.setVisible(true);
        });
    }
}