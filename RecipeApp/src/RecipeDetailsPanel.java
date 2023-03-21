import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecipeDetailsPanel extends JPanel {

    private Recipe currentRecipe;

    private JTextArea descriptionArea;
    private JCheckBox favoriteCheckBox;
    private JLabel nameLabel;
    private JTextArea instructionArea;

    public RecipeDetailsPanel() {
    	setLayout(new BorderLayout());

        nameLabel = new JLabel();
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(nameLabel, BorderLayout.NORTH);

        JPanel fieldsPanel = new JPanel(new GridLayout(0, 1));
        descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        
        
        instructionArea = new JTextArea();
        instructionArea.setEditable(false);
        instructionArea.setLineWrap(true);
        instructionArea.setWrapStyleWord(true);
        JScrollPane instructionsScrollPane = new JScrollPane(instructionArea);

        
        
    
        favoriteCheckBox = new JCheckBox("Favorite");
        favoriteCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (currentRecipe != null) {
            		currentRecipe.setFavorite(favoriteCheckBox.isSelected());
            
            	}
            }
        });
        fieldsPanel.add(new JLabel("Description:"));
        fieldsPanel.add(descriptionScrollPane);
        fieldsPanel.add(new JLabel("Instructions:"));
        fieldsPanel.add(instructionsScrollPane);
        
        add(fieldsPanel, BorderLayout.CENTER);
        fieldsPanel.add(favoriteCheckBox);
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
            nameLabel.setText(recipe.getName());
            descriptionArea.setText(recipe.getDescription());
            instructionArea.setText(recipe.getInstructions());
            favoriteCheckBox.setSelected(recipe.isFavorite());
        } else {
            descriptionArea.setText("");
            instructionArea.setText("");
            favoriteCheckBox.setSelected(false);
        }
    }
}
