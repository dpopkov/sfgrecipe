package learn.sfg.sfgrecipe.services;

import learn.sfg.sfgrecipe.commands.IngredientCommand;
import learn.sfg.sfgrecipe.commands.UnitOfMeasureCommand;
import learn.sfg.sfgrecipe.converters.IngredientCommandToIngredient;
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

import java.math.BigDecimal;
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
    IngredientCommandToIngredient ingredientCommandConverter;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    @InjectMocks
    IngredientServiceImpl service;

    private static final Long RECIPE_ID = 12L;
    private static final long INGREDIENT_ID = 123L;
    private static final long UOM_ID = 1234L;

    @DisplayName("Can find Ingredient by recipeId and ingredientId")
    @Test
    void testFindByRecipeIdAndIngredientId() {
        final Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.addIngredient(Ingredient.builder().id(121L).build());
        recipe.addIngredient(Ingredient.builder().id(122L).build());
        Ingredient ingredientToReturn = Ingredient.builder().id(INGREDIENT_ID).build();
        recipe.addIngredient(ingredientToReturn);

        given(recipeRepository.findById(RECIPE_ID)).willReturn(Optional.of(recipe));
        IngredientCommand command = IngredientCommand.builder().id(INGREDIENT_ID).recipeId(RECIPE_ID).build();
        given(ingredientConverter.convert(ingredientToReturn)).willReturn(command);

        IngredientCommand ingredientCommand = service.findByRecipeIdAndIngredientId(RECIPE_ID, INGREDIENT_ID);

        assertEquals(RECIPE_ID, ingredientCommand.getRecipeId());
        assertEquals(INGREDIENT_ID, ingredientCommand.getId());
        then(recipeRepository).should().findById(RECIPE_ID);
        then(ingredientConverter).should().convert(ingredientToReturn);
    }

    @DisplayName("Can update existing Ingredient")
    @Test
    void testSaveIngredientCommandWhenExisting() {
        final Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.addIngredient(Ingredient.builder().id(122L).build());
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UOM_ID);
        Ingredient ingredientToReturn = Ingredient.builder()
                .id(INGREDIENT_ID)
                .uom(uom)
                .build();
        recipe.addIngredient(ingredientToReturn);

        given(recipeRepository.findById(RECIPE_ID)).willReturn(Optional.of(recipe));
        given(recipeRepository.save(any())).willReturn(recipe);
        given(unitOfMeasureRepository.findById(UOM_ID)).willReturn(Optional.of(uom));
        given(ingredientConverter.convert(any())).willReturn(new IngredientCommand());

        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId(UOM_ID);
        IngredientCommand inputCommand = IngredientCommand.builder()
                .id(INGREDIENT_ID)
                .recipeId(RECIPE_ID)
                .uom(uomCommand)
                .build();

        final IngredientCommand savedCommand = service.saveIngredientCommand(inputCommand);

        assertNotNull(savedCommand);
        then(recipeRepository).should().findById(RECIPE_ID);
        then(recipeRepository).should().save(any(Recipe.class));
        then(ingredientCommandConverter).shouldHaveNoInteractions();
        then(ingredientConverter).should().convert(any(Ingredient.class));
    }

    @DisplayName("Can save a new Ingredient")
    @Test
    void testSaveIngredientCommandWhenNew() {
        final Recipe savedRecipe = new Recipe();
        savedRecipe.setId(RECIPE_ID);
        savedRecipe.addIngredient(Ingredient.builder().id(122L).build());
        savedRecipe.addIngredient(Ingredient.builder().id(123L).build());
        given(recipeRepository.findById(RECIPE_ID)).willReturn(Optional.of(savedRecipe));
        given(recipeRepository.save(any())).willReturn(savedRecipe);
        final Ingredient savedIngredient = Ingredient.builder()
                .id(124L)
                .description("description")
                .amount(BigDecimal.ONE)
                .uom(UnitOfMeasure.builder().id(UOM_ID).build())
                .build();
        given(ingredientCommandConverter.convert(any())).willReturn(savedIngredient);
        given(ingredientConverter.convert(any())).willReturn(new IngredientCommand());

        final IngredientCommand inputCommand = IngredientCommand.builder()
                .recipeId(RECIPE_ID)
                .description("description")
                .amount(BigDecimal.ONE)
                .uom(UnitOfMeasureCommand.builder().id(UOM_ID).build())
                .build();

        final IngredientCommand savedCommand = service.saveIngredientCommand(inputCommand);

        assertNotNull(savedCommand);
        then(recipeRepository).should().findById(RECIPE_ID);
        then(recipeRepository).should().save(any(Recipe.class));
        then(ingredientCommandConverter).should().convert(any(IngredientCommand.class));
        then(ingredientConverter).should().convert(any(Ingredient.class));
    }

    @DisplayName("Can delete Ingredient by recipe ID and ingredient ID")
    @Test
    void testDeleteById() {
        final Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        Ingredient ingredient = Ingredient.builder().id(INGREDIENT_ID).build();
        recipe.addIngredient(ingredient);
        given(recipeRepository.findById(RECIPE_ID)).willReturn(Optional.of(recipe));

        service.deleteById(RECIPE_ID, INGREDIENT_ID);

        then(recipeRepository).should().findById(RECIPE_ID);
        then(recipeRepository).should().save(recipe);
    }
}
