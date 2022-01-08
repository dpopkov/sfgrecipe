package learn.sfg.sfgrecipe.converters;

import learn.sfg.sfgrecipe.commands.CategoryCommand;
import learn.sfg.sfgrecipe.commands.IngredientCommand;
import learn.sfg.sfgrecipe.commands.RecipeCommand;
import learn.sfg.sfgrecipe.domain.Difficulty;
import learn.sfg.sfgrecipe.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final CategoryCommandToCategory categoryCommandToCategory;
    private final NotesCommandToNotes notesCommandToNotes;

    public RecipeCommandToRecipe(IngredientCommandToIngredient ingredientCommandToIngredient, CategoryCommandToCategory categoryCommandToCategory, NotesCommandToNotes notesCommandToNotes) {
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.notesCommandToNotes = notesCommandToNotes;
    }

    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }
        Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setCookTime(source.getCookTime());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setDirections(source.getDirections());
        if (source.getDifficulty() != null) {
            recipe.setDifficulty(Difficulty.valueOf(source.getDifficulty().toUpperCase()));
        }
        recipe.setNotes(notesCommandToNotes.convert(source.getNotes()));
        for (IngredientCommand ingredientCommand : source.getIngredients()) {
            recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
        }
        for (CategoryCommand categoryCommand : source.getCategories()) {
            recipe.addCategory(categoryCommandToCategory.convert(categoryCommand));
        }
        return recipe;
    }
}
