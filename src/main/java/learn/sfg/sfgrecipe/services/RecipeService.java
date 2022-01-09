package learn.sfg.sfgrecipe.services;

import learn.sfg.sfgrecipe.commands.RecipeCommand;
import learn.sfg.sfgrecipe.domain.Recipe;

import java.util.Optional;

public interface RecipeService {

    Iterable<Recipe> findAll();

    Optional<RecipeCommand> findById(Long recipeId);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    void deleteById(Long recipeId);
}
