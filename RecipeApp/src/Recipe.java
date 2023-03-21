class Recipe {
private String name;
private String description;
private boolean isFavorite;
private String instructions;
	public Recipe(String name, String description, String instructions) {
	    this.name = name;
	    this.description = description;
	    this.isFavorite = false;
	    this.instructions = instructions;
	}

	public String getName() {
	    return name;
	}

	public String getDescription() {
	    return description;
	}


	public void editRecipe(String name, String description, String instruction) {
		// TODO Auto-generated method stub
		this.name = name;
		this.description = description;
		this.instructions = instruction;

	}

	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean favorite) {
		isFavorite = favorite;
	}

	public void setDescription(String text) {
		// TODO Auto-generated method stub
		this.description = text;
	}
	public String getInstructions() {
	    return instructions;
	}



	public void setName(String name) {
		this.name = name;
		// TODO Auto-generated method stub

	}

	public void setInstructions(String instruction) {
		this.instructions = instruction;
		// TODO Auto-generated method stub

	}
}
