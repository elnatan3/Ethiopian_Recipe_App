import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecipeDetailsPanel extends JPanel {

    private Recipe currentRecipe;

    private JTextArea descriptionArea;
    private JCheckBox favoriteCheckBox;

    public RecipeDetailsPanel() {
        setLayout(new BorderLayout());

        JPanel fieldsPanel = new JPanel(new GridLayout(0, 1));
        descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setPreferredSize(new Dimension(200, 100));
        favoriteCheckBox = new JCheckBox("Favorite");
        favoriteCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentRecipe.setFavorite(favoriteCheckBox.isSelected());
            }
        });
        fieldsPanel.add(new JLabel("Description:"));
        fieldsPanel.add(scrollPane);
        fieldsPanel.add(favoriteCheckBox);
        add(fieldsPanel, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentRecipe != null) {
                    currentRecipe.setDescription(descriptionArea.getText());
                }
            }
        });
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(saveButton);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void setRecipe(Recipe recipe) {
        currentRecipe = recipe;
        if (recipe != null) {
            descriptionArea.setText(recipe.getDescription());
            favoriteCheckBox.setSelected(recipe.isFavorite());
        } else {
            descriptionArea.setText("");
            favoriteCheckBox.setSelected(false);
        }
    }
}
