package learn.sfg.sfgrecipe.services;

import learn.sfg.sfgrecipe.commands.IngredientCommand;
import learn.sfg.sfgrecipe.commands.UnitOfMeasureCommand;
import learn.sfg.sfgrecipe.converters.IngredientToIngredientCommand;
import learn.sfg.sfgrecipe.domain.Ingredient;
import learn.sfg.sfgrecipe.domain.Recipe;
import learn.sfg.sfgrecipe.domain.UnitOfMeasure;
import learn.sfg.sfgrecipe.repositories.RecipeRepository;
import learn.sfg.sfgrecipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    IngredientToIngredientCommand ingredientConverter;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    @InjectMocks
    IngredientServiceImpl service;

    @DisplayName("Can find Ingredient by recipeId and ingredientId")
    @Test
    void testFindByRecipeIdAndIngredientId() {
        final Long recipeId = 12L;
        final Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.addIngredient(Ingredient.builder().id(121L).build());
        recipe.addIngredient(Ingredient.builder().id(122L).build());
        final long ingredientId = 123L;
        Ingredient ingredientToReturn = Ingredient.builder().id(ingredientId).build();
        recipe.addIngredient(ingredientToReturn);

        given(recipeRepository.findById(recipeId)).willReturn(Optional.of(recipe));
        IngredientCommand command = IngredientCommand.builder().id(ingredientId).recipeId(recipeId).build();
        given(ingredientConverter.convert(ingredientToReturn)).willReturn(command);

        IngredientCommand ingredientCommand = service.findByRecipeIdAndIngredientId(recipeId, ingredientId);

        assertEquals(recipeId, ingredientCommand.getRecipeId());
        assertEquals(ingredientId, ingredientCommand.getId());
        then(recipeRepository).should().findById(recipeId);
        then(ingredientConverter).should().convert(ingredientToReturn);
    }

    @DisplayName("Can update existing Ingredient")
    @Test
    void testSaveIngredientCommandWhnExisting() {
        final Long recipeId = 12L;
        final Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.addIngredient(Ingredient.builder().id(122L).build());
        final long ingredientId = 123L;
        UnitOfMeasure uom = new UnitOfMeasure();
        final long uomId = 1234L;
        uom.setId(uomId);
        Ingredient ingredientToReturn = Ingredient.builder()
                .id(ingredientId)
                .uom(uom)
                .build();
        recipe.addIngredient(ingredientToReturn);

        given(recipeRepository.findById(recipeId)).willReturn(Optional.of(recipe));
        given(recipeRepository.save(any())).willReturn(recipe);
        given(unitOfMeasureRepository.findById(uomId)).willReturn(Optional.of(uom));
        given(ingredientConverter.convert(any())).willReturn(new IngredientCommand());

        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId(uomId);
        IngredientCommand inputCommand = IngredientCommand.builder()
                .id(ingredientId)
                .recipeId(recipeId)
                .uom(uomCommand)
                .build();

        final IngredientCommand savedCommand = service.saveIngredientCommand(inputCommand);

        assertNotNull(savedCommand);
        then(recipeRepository).should().findById(recipeId);
        then(recipeRepository).should().save(any(Recipe.class));
        then(ingredientConverter).should().convert(any(Ingredient.class));
    }
}
