
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
                scrollPane.setPreferredSize(new Dimension(200, 100));
                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Category:"));
                panel.add(categoryComboBox);
                panel.add(new JLabel("Name:"));
                panel.add(nameField);
                panel.add(new JLabel("Description:"));
                panel.add(scrollPane);
                
                int result = JOptionPane.showConfirmDialog(null, panel, "Add Recipe",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String categoryName = (String) categoryComboBox.getSelectedItem();
                    RecipeCategory category = getRecipeCategoryByName(categoryName);
                    String name = nameField.getText();
                    String description = descriptionArea.getText();
                    Recipe recipe = new Recipe(name, description);
                    category.addRecipe(recipe);
                    updateRecipesList();
                    currentRecipe = recipe;
                    detailsPanel.setRecipe(currentRecipe);
                    buttonsPanel.setVisible(true);
                }
            }
        });
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
        JPanel leftPanel = new JPanel(new BorderLayout());
        recipesList = new JList<>();
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
        JPanel rightPanel = new JPanel(new BorderLayout());
        detailsPanel = new RecipeDetailsPanel();
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

    public void editRecipe(Recipe recipe) {
    	String name = JOptionPane.showInputDialog(this, "Enter the new name:", recipe.getName());
    	String description = JOptionPane.showInputDialog(this, "Enter the new description:", recipe.getDescription());
    	if (name != null && description != null) {
    	recipe.editRecipe(name, description);
    	detailsPanel.setRecipe(recipe);
    	updateRecipesList();
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
        appetizers.addRecipe(new Recipe("Sambusa", "Deep-fried triangular pastry filled with spiced minced meat."));
        appetizers.addRecipe(new Recipe("Dabo Kolo", "Small, crunchy, slightly sweet snacks made from flour, spices, and water."));
        appetizers.addRecipe(new Recipe("Fit-fit", "A dish made with injera and spices"));
        appetizers.addRecipe(new Recipe("Injera with Honey", "Injera is a sourdough flatbread that is popular in Ethiopia. It's traditionally made with teff flour, but you can also use a mix of teff and wheat flour. Drizzle honey over the injera for a sweet breakfast treat!"));
        appetizers.addRecipe(new Recipe("Ful Medames", "A popular Egyptian dish that is also eaten in Ethiopia. It's a hearty breakfast dish made with fava beans, garlic, lemon juice, and olive oil."));
        appetizers.addRecipe(new Recipe("Kitcha Fitfit", "A traditional Ethiopian breakfast dish made with shredded injera, spices, and clarified butter. It's often served with yogurt or cheese."));

        app.addRecipeCategory(appetizers);

        RecipeCategory entrees = new RecipeCategory("Entrees");
        entrees.addRecipe(new Recipe("Doro Wat", "Spicy chicken stew served with injera."));
        entrees.addRecipe(new Recipe("Kitfo", "Raw minced beef seasoned with spices and served with injera."));
        entrees.addRecipe(new Recipe("Injera with Tibs", "A dish made with grilled meat and injera"));
        entrees.addRecipe(new Recipe("Shiro", "A thick stew made with ground chickpeas or lentils"));
        entrees.addRecipe(new Recipe("Misir Wot", "A spicy lentil stew that is a staple of Ethiopian cuisine. It's made with red lentils, onions, garlic, and berbere spice blend."));
        entrees.addRecipe(new Recipe("Gomen", "A dish made with collard greens, onions, and spices. It's often served with injera and other Ethiopian side dishes."));

        app.addRecipeCategory(entrees);

        app.setVisible(true);
    }

}

