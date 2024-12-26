package org.example.promla;

import com.example.UAPsmt3.Game;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class GameCatalog extends JFrame{
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> gameJList = new JList<>(listModel);

    public GameCatalog() {
        setTitle("Game Catalog Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        gameJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(gameJList), BorderLayout.CENTER);

        JButton addButton = new JButton("Add Game");
        JButton editButton = new JButton("Edit Game");
        JButton deleteButton = new JButton("Delete Game");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addGame());
        editButton.addActionListener(e -> editGame());
        deleteButton.addActionListener(e -> deleteGame());

        add(panel); // Load games from API

        try {
            List<Game> games = ApiClient.getGames();
            for (Game game : games) {
                listModel.addElement(game.getTitle());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setVisible(true);
    }

    private void addGame() {
        GameDialog dialog = new GameDialog(this, "Add Game", null);
        dialog.setVisible(true);
        Game game = dialog.getGame();
        if (game != null) {
            try {
                ApiClient.addGame(game);
                listModel.addElement(game.getTitle());
                System.out.println("Game added to list: " + game.getTitle());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void editGame() {
        int selectedIndex = gameJList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "No game selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedTitle = listModel.get(selectedIndex);
        try {
            Game game = ApiClient.getGameByTitle(selectedTitle);
            // Assume this method exists
            GameDialog dialog = new GameDialog(this, "Edit Game", game);
            dialog.setVisible(true);
            Game editedGame = dialog.getGame();
            if (editedGame != null) {
                ApiClient.updateGame(editedGame);
                listModel.set(selectedIndex, editedGame.getTitle());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteGame() {
        int selectedIndex = gameJList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "No game selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String selectedTitle = listModel.get(selectedIndex);
            Game game = ApiClient.getGameByTitle(selectedTitle);
            ApiClient.deleteGame(game.getId());
            listModel.remove(selectedIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameCatalog::new);
    }
}
