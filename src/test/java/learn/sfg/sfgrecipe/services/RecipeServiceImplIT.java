package learn.sfg.sfgrecipe.services;

import learn.sfg.sfgrecipe.commands.RecipeCommand;
import learn.sfg.sfgrecipe.converters.RecipeCommandToRecipe;
import learn.sfg.sfgrecipe.converters.RecipeToRecipeCommand;
import learn.sfg.sfgrecipe.domain.Recipe;
import learn.sfg.sfgrecipe.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RecipeServiceImplIT {
    private static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Autowired
    RecipeServiceImpl service;

    @Transactional
    @Test
    void saveRecipeCommand() {
        // Given
        Iterable<Recipe> all = recipeRepository.findAll();
        Recipe testRecipe = all.iterator().next();
        RecipeCommand testCommand = recipeToRecipeCommand.convert(testRecipe);
        assertNotNull(testCommand);
        testCommand.setDescription(NEW_DESCRIPTION);
        // When
        RecipeCommand savedCommand = service.saveRecipeCommand(testCommand);
        // Then
        assertEquals(NEW_DESCRIPTION, savedCommand.getDescription());
        assertEquals(testRecipe.getId(), savedCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedCommand.getCategoryCommands().size());
        assertEquals(testRecipe.getIngredients().size(), savedCommand.getIngredientCommands().size());
    }
}
