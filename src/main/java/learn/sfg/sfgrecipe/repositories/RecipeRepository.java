package learn.sfg.sfgrecipe.repositories;

import learn.sfg.sfgrecipe.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
