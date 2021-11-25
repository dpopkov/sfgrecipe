package learn.sfg.sfgrecipe.services;

import learn.sfg.sfgrecipe.domain.Recipe;

public interface RecipeService {

    Iterable<Recipe> findAll();
}
