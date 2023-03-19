class Recipe {
private String name;
private String description;
private boolean isFavorite;
public Recipe(String name, String description) {
    this.name = name;
    this.description = description;
    this.isFavorite = false;
}

public String getName() {
    return name;
}

public String getDescription() {
    return description;
}


public void editRecipe(String name, String description) {
	// TODO Auto-generated method stub
	this.name = name;
	this.description = description;
	
}

public boolean isFavorite() {
	return isFavorite;
}

public void setFavorite(boolean favorite) {
	isFavorite = favorite;
}

public void setDescription(String text) {
	// TODO Auto-generated method stub
	description = text;
	
}
}