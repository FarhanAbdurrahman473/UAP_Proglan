package org.example.promla;

import com.example.UAPsmt3.Game;
import javax.swing.*;
import java.awt.*;
import com.google.gson.Gson;

public class GameDialog extends JDialog{
    private JTextField titleField;
    private JTextField genreField;
    private JCheckBox finishedCheckBox;
    private JTextField coverPathField;
    private Game game;

    public GameDialog(Frame owner, String title, Game game) {
        super(owner, title, true);
        this.game = game;

        setLayout(new GridLayout(5, 2));

        add(new JLabel("Title:"));
        titleField = new JTextField();
        add(titleField);

        add(new JLabel("Genre:"));
        genreField = new JTextField();
        add(genreField);

        add(new JLabel("Finished:"));
        finishedCheckBox = new JCheckBox();
        add(finishedCheckBox);

        add(new JLabel("Cover Path:"));
        coverPathField = new JTextField();
        add(coverPathField);

        final Game[] gameRef = new Game[1];
        gameRef[0] = this.game;

        JButton okButton = createOkButton();
        okButton.addActionListener(e -> {
            if (gameRef[0] == null) {
                gameRef[0] = new Game();
            }
            gameRef[0].setTitle(titleField.getText());
            gameRef[0].setGenre(genreField.getText());
            gameRef[0].setFinished(finishedCheckBox.isSelected());
            gameRef[0].setCoverPath(coverPathField.getText());

            System.out.println("Game to be added: " + new Gson().toJson(gameRef[0]));
            dispose();
        });
        add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);

        if (game != null) {
            titleField.setText(game.getTitle());
            genreField.setText(game.getGenre());
            finishedCheckBox.setSelected(game.isFinished());
            coverPathField.setText(game.getCoverPath());
        }

        pack();
    }

    public Game getGame() {
        return game;
    }

    private JButton createOkButton() {
        JButton okButton = new JButton("ok");

        return okButton;
    }
}
