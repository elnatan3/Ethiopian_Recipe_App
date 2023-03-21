import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EthiopianRecipeApp extends JFrame {
    private List<RecipeCategory> recipeCategories;
    private RecipeCategory currentCategory;
    private Recipe currentRecipe;
    private JPanel mainPanel;
    private JComboBox<String> categoriesComboBox;
    private JList<String> recipesList;
    private RecipeDetailsPanel detailsPanel;
    private ButtonsPanel buttonsPanel;
    private JTextField searchField;
    private JButton searchButton;


    public EthiopianRecipeApp() {
    	// Show login dialog
		LoginDialog loginDialog = new LoginDialog(this);
		loginDialog.setVisible(true);
		if (!loginDialog.isLoginSuccessful()) {
			System.exit(0);
		}
        recipeCategories = new ArrayList<>();
        currentCategory = null;
        currentRecipe = null;

        setTitle("Ethiopian Recipe App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // top panel with categories combo box
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        categoriesComboBox = new JComboBox<>();
        categoriesComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        categoriesComboBox.setForeground(Color.BLUE);
        categoriesComboBox.setBackground(Color.WHITE);
        // ... add JLabels, buttons, and text fields with similar font and color properties ...
        categoriesComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String categoryName = (String) categoriesComboBox.getSelectedItem();
                currentCategory = getRecipeCategoryByName(categoryName);
                updateRecipesList();
                currentRecipe = null;
                detailsPanel.setRecipe(null);
                buttonsPanel.setVisible(false);
            }
        });
        
    
        topPanel.add(new JLabel("Category:"));
        topPanel.add(categoriesComboBox);
     // add recipe button
        JButton addRecipeButton = new JButton("Add Recipe");
        addRecipeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] categories = new String[recipeCategories.size()];
                for (int i = 0; i < recipeCategories.size(); i++) {
                    categories[i] = recipeCategories.get(i).getName();
                }
                JComboBox<String> categoryComboBox = new JComboBox<>(categories);
                JTextField nameField = new JTextField();
                JTextArea descriptionArea = new JTextArea();
                descriptionArea.setLineWrap(true);
                descriptionArea.setWrapStyleWord(true);
                JScrollPane scrollPane = new JScrollPane(descriptionArea);
                scrollPane.setPreferredSize(new Dimension(200, 40));
                JTextArea instructionsArea = new JTextArea();
                instructionsArea.setLineWrap(true);
                instructionsArea.setWrapStyleWord(true);
                JScrollPane instructionsScrollPane = new JScrollPane(instructionsArea);
                instructionsScrollPane.setPreferredSize(new Dimension(200, 80));
                
                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.setBackground(Color.WHITE); // set background color
                panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // add padding
                panel.add(new JLabel("Category:"));
                panel.add(categoryComboBox);
                JLabel nameLabel = new JLabel("Name:");
                nameLabel.setForeground(Color.BLUE); // set label color
                nameLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // set font
                panel.add(nameLabel);
                nameField.setPreferredSize(new Dimension(200, 20)); // set field size
                panel.add(nameField);
                JLabel descLabel = new JLabel("Description:");
                descLabel.setForeground(Color.RED); // set label color
                descLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // set font
                panel.add(descLabel);
                scrollPane.setPreferredSize(new Dimension(200, 40)); // set field size
                panel.add(scrollPane);
                JLabel instLabel = new JLabel("Instructions:");
                instLabel.setForeground(Color.GREEN); // set label color
                instLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // set font
                instructionsScrollPane.setPreferredSize(new Dimension(200, 80)); // set field size
                panel.add(instLabel);
                panel.add(instructionsScrollPane);

                int result = JOptionPane.showConfirmDialog(null, panel, "Add Recipe",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String categoryName = (String) categoryComboBox.getSelectedItem();
                    RecipeCategory category = getRecipeCategoryByName(categoryName);
                    String name = nameField.getText();
                    String description = descriptionArea.getText();
                    String instruction = instructionsArea.getText();
                    Recipe recipe = new Recipe(name, description, instruction);
                    category.addRecipe(recipe);
                    updateRecipesList();
                    currentRecipe = recipe;
                    detailsPanel.setRecipe(currentRecipe);
                    buttonsPanel.setVisible(true);
                }
            }
        });
        addRecipeButton.setForeground(Color.WHITE); // set button text color
        addRecipeButton.setBackground(Color.BLUE); // set button background color
        topPanel.add(addRecipeButton);

        addRecipeButton.setForeground(Color.WHITE); // set button text color
        addRecipeButton.setBackground(Color.BLUE); // set button background color
        topPanel.add(addRecipeButton);


        topPanel.add(addRecipeButton);
        searchField = new JTextField(20);
        topPanel.add(searchField);
        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRecipesList();
            }
        });
        
        topPanel.add(searchButton);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // left panel with recipes list
     // add font and color to the left panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        recipesList = new JList<>();
        recipesList.setFont(new Font("Arial", Font.PLAIN, 14));
        recipesList.setForeground(Color.DARK_GRAY);
        recipesList.setBackground(Color.LIGHT_GRAY);
        recipesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recipesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String recipeName = recipesList.getSelectedValue();
                currentRecipe = getRecipeByName(recipeName);
                detailsPanel.setRecipe(currentRecipe);
                buttonsPanel.setVisible(true);
            }
        });
        JScrollPane scrollPane = new JScrollPane(recipesList);
        scrollPane.setPreferredSize(new Dimension(200, 0));
        leftPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(leftPanel, BorderLayout.WEST);

        // right panel with recipe details and buttons
     // add font and color to the right panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        detailsPanel = new RecipeDetailsPanel();
        detailsPanel.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsPanel.setForeground(Color.DARK_GRAY);
        detailsPanel.setBackground(Color.LIGHT_GRAY);
        rightPanel.add(detailsPanel, BorderLayout.CENTER);
        buttonsPanel = new ButtonsPanel();
        buttonsPanel.setVisible(false);
        buttonsPanel.getEditButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editRecipe(currentRecipe);
            }
        });
        buttonsPanel.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRecipe(currentRecipe);
            }
        });
        rightPanel.add(buttonsPanel, BorderLayout.SOUTH);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
    }



    public void addRecipeCategory(RecipeCategory recipeCategory) {
        recipeCategories.add(recipeCategory);
        updateCategoriesComboBox();
    }

    public void removeRecipeCategory(RecipeCategory recipeCategory) {
        recipeCategories.remove(recipeCategory);
        updateCategoriesComboBox();
        if (currentCategory == recipeCategory) {
            currentCategory = null;
            updateRecipesList();
            currentRecipe = null;
            detailsPanel.setRecipe(null);
            buttonsPanel.setVisible(false);
        }
    }

    private void editRecipe(Recipe recipe) {
        JTextField nameField = new JTextField(recipe.getName());
        JTextArea descriptionArea = new JTextArea(recipe.getDescription());
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setPreferredSize(new Dimension(200, 100));
        JTextArea instructionArea = new JTextArea(recipe.getInstructions());
        instructionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane instructionScrollPane = new JScrollPane(instructionArea);
        instructionScrollPane.setPreferredSize(new Dimension(300, 100));
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        panel.add(scrollPane);
        panel.add(new JLabel("Instruction:"));
        panel.add(instructionScrollPane);
        

        int result = JOptionPane.showConfirmDialog(null, panel, "Edit Recipe",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String description = descriptionArea.getText();
            String instruction = instructionArea.getText();
            recipe.setName(name);
            recipe.setDescription(description);
            recipe.setInstructions(instruction);
            recipe.editRecipe(name, description, instruction);
            detailsPanel.setRecipe(recipe);            
        }
        
    }




    public void deleteRecipe(Recipe recipe) {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this recipe?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
        	currentCategory.removeRecipe(recipe);
        	updateRecipesList();
        	currentRecipe = null;
        	detailsPanel.setRecipe(null);
        	buttonsPanel.setVisible(false);
        	}
        }
    private void updateCategoriesComboBox() {
        categoriesComboBox.removeAllItems();
        for (RecipeCategory category : recipeCategories) {
            categoriesComboBox.addItem(category.getName());
        }
    }

    private RecipeCategory getRecipeCategoryByName(String name) {
        for (RecipeCategory category : recipeCategories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    private Recipe getRecipeByName(String name) {
        if (currentCategory == null) {
            return null;
        }
        for (Recipe recipe : currentCategory.getRecipes()) {
            if (recipe.getName().equals(name)) {
                return recipe;
            }
        }
        return null;
    }

    public void updateRecipesList() {
        if (currentCategory != null) {
            List<Recipe> recipes = currentCategory.getRecipes();
            if (recipes != null) {
                String searchTerm = searchField.getText().trim().toLowerCase();
                if (!searchTerm.isEmpty()) {
                    List<Recipe> filteredRecipes = new ArrayList<>();
                    for (Recipe recipe : recipes) {
                        if (recipe.getName().toLowerCase().contains(searchTerm)) {
                            filteredRecipes.add(recipe);
                        }
                    }
                    recipesList.setListData(filteredRecipes.stream().map(Recipe::getName).toArray(String[]::new));
                } else {
                    recipesList.setListData(recipes.stream().map(Recipe::getName).toArray(String[]::new));
                }
            } else {
                recipesList.setListData(new String[]{});
            }
        }
        

    }


    public static void main(String[] args) {
        EthiopianRecipeApp app = new EthiopianRecipeApp();

        RecipeCategory appetizers = new RecipeCategory("Appetizers");
        appetizers.addRecipe(new Recipe("Sambusa", "Deep-fried triangular pastry filled with spiced minced meat.", "Deep-fry triangular pastry filled with spiced minced meat."));
        appetizers.addRecipe(new Recipe("Dabo Kolo", "Small, crunchy, slightly sweet snacks made from flour, spices, and water.", "Combine flour, spices, and water to make a dough. Roll the dough into small pieces and deep-fry until golden brown."));
        appetizers.addRecipe(new Recipe("Fit-fit", "A dish made with injera and spices", "Cut injera into small pieces and mix with spices, onion, tomato, and jalapeño. Saute in a pan with butter or oil until heated through."));
        appetizers.addRecipe(new Recipe("Injera with Honey", "Injera is a sourdough flatbread that is popular in Ethiopia. It's traditionally made with teff flour, but you can also use a mix of teff and wheat flour.", "Drizzle honey over the injera"));
        appetizers.addRecipe(new Recipe("Ful Medames", "A popular Egyptian dish that is also eaten in Ethiopia. It's a hearty breakfast dish made with fava beans, garlic, lemon juice, and olive oil.", "Soak fava beans overnight. Drain and rinse the beans, then cook in a pot with garlic, lemon juice, and olive oil until tender. Serve with injera."));
        appetizers.addRecipe(new Recipe("Kitcha Fitfit", "A traditional Ethiopian breakfast dish made with shredded injera, spices, and clarified butter. It's often served with yogurt or cheese.", "Shred injera into small pieces and mix with spices and clarified butter. Serve with yogurt or cheese."));
        appetizers.addRecipe(new Recipe("Genfo", "A hot cereal made from roasted barley flour, often eaten for breakfast.", "Roast barley flour in a pan until fragrant. Mix with water and bring to a boil, stirring constantly. Serve hot."));
        appetizers.addRecipe(new Recipe("Chechebsa", "A traditional Ethiopian breakfast dish made with lightly fried injera bread and spices.", "Tear injera into small pieces and lightly fry in a pan with spices. Serve hot."));
        app.addRecipeCategory(appetizers);

        RecipeCategory entrees = new RecipeCategory("Entrees");
        entrees.addRecipe(new Recipe("Doro Wat", "Spicy chicken stew served with injera.", "In a large pot, sauté onion, garlic, and ginger until fragrant. Add chicken and berbere spice blend, then simmer until the chicken is cooked through. Serve with injera."));
        entrees.addRecipe(new Recipe("Kitfo", "Raw minced beef seasoned with spices and served with injera.", "Mix raw minced beef with spices and serve with injera."));
        entrees.addRecipe(new Recipe("Injera with Tibs", "A dish made with grilled meat and injera", "Grill meat and serve with injera."));
        entrees.addRecipe(new Recipe("Shiro", "A thick stew made with ground chickpeas or lentils", "In a pot, sauté onion, garlic, and ginger until fragrant. Add ground chickpeas or lentils and water, then simmer until the mixture thickens. Serve with injera."));
        entrees.addRecipe(new Recipe("Misir Wot", "A spicy lentil stew that is a staple of Ethiopian cuisine. It's made with red lentils, onions, garlic, and berbere spice blend.", "In a pot, sauté onion and garlic until fragrant. Add red lentils, water, and berbere spice blend, then simmer until the lentils are cooked through. Serve with injera."));
        entrees.addRecipe(new Recipe("Gomen", "A dish made with collard greens, onions, and spices. It's often served with injera and other Ethiopian side dishes.", "Blanch collard greens, then sauté with onion, garlic, and spices. Serve with injera."));
        app.addRecipeCategory(entrees);

        app.setVisible(true);
    }

}

