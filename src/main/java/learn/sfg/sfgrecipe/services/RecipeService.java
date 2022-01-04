package learn.sfg.sfgrecipe.services;

import learn.sfg.sfgrecipe.domain.Recipe;

import java.util.Optional;

public interface RecipeService {

    Iterable<Recipe> findAll();

    Optional<Recipe> findById(Long recipeId);
}
