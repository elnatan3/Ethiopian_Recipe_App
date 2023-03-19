import java.util.ArrayList;
import java.util.List;

class RecipeCategory {
	private String name;
	private List<Recipe> recipes;
	public RecipeCategory(String name) {
	    this.name = name;
	    recipes = new ArrayList<>();
	}
	
	public String getName() {
	    return name;
	}
	
	public List<Recipe> getRecipes() {
	    return recipes;
	}
	
	public void addRecipe(Recipe recipe) {
	    recipes.add(recipe);
	}
	
	public void removeRecipe(Recipe recipe) {
	    recipes.remove(recipe);
	}
}
