package learn.sfg.sfgrecipe.services;

import learn.sfg.sfgrecipe.commands.IngredientCommand;
import learn.sfg.sfgrecipe.converters.IngredientCommandToIngredient;
import learn.sfg.sfgrecipe.converters.IngredientToIngredientCommand;
import learn.sfg.sfgrecipe.domain.Ingredient;
import learn.sfg.sfgrecipe.domain.Recipe;
import learn.sfg.sfgrecipe.domain.UnitOfMeasure;
import learn.sfg.sfgrecipe.repositories.RecipeRepository;
import learn.sfg.sfgrecipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientConverter;
    private final IngredientCommandToIngredient ingredientCommandConverter;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientConverter,
                                 IngredientCommandToIngredient ingredientCommandConverter,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientConverter = ingredientConverter;
        this.ingredientCommandConverter = ingredientCommandConverter;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Recipe recipe = recipeRepository
                .findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found by id " + recipeId));
        Ingredient ingredient = recipe.getIngredients()
                .stream()
                .filter(i -> i.getId().equals(ingredientId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ingredient not found by id " + ingredientId));
        return ingredientConverter.convert(ingredient);
    }

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        // Find Recipe
        Optional<Recipe> recipeOpt = recipeRepository.findById(command.getRecipeId());
        if (recipeOpt.isEmpty()) {
            // todo: throw exception if not found
            log.error("Recipe not found for id={}", command.getRecipeId());
            return new IngredientCommand();
        }

        // Find Ingredient, then update or save it
        final Recipe existingRecipe = recipeOpt.get();
        Optional<Ingredient> ingredientOpt = existingRecipe.getIngredients()
                .stream()
                .filter(i -> i.getId().equals(command.getId()))
                .findFirst();
        if (ingredientOpt.isPresent()) {
            updateExistingIngredient(command, ingredientOpt.get());
        } else {
            addNewIngredient(command, existingRecipe);
            // todo: Test for adding new Ingredient
        }
        Recipe savedRecipe = recipeRepository.save(existingRecipe);

        // Return command object for updated/saved Ingredient
        final Ingredient ingredient = savedRecipe.getIngredients()
                .stream()
                .filter(i -> i.getId().equals(command.getId()))
                .findFirst()
                .orElseThrow();
        return ingredientConverter.convert(ingredient);
    }

    private void updateExistingIngredient(IngredientCommand command, Ingredient existingIngredient) {
        existingIngredient.setDescription(command.getDescription());
        existingIngredient.setAmount(command.getAmount());
        existingIngredient.setUom(getUnitOfMeasureFrom(command));
    }

    private void addNewIngredient(IngredientCommand command, Recipe existingRecipe) {
        existingRecipe.addIngredient(ingredientCommandConverter.convert(command));
    }

    private UnitOfMeasure getUnitOfMeasureFrom(IngredientCommand command) {
        Long uomId = command.getUom().getId();
        return unitOfMeasureRepository.findById(uomId)
                .orElseThrow(() -> new RuntimeException("Cannot find UnitOfMeasure by id=" + uomId)); // todo: handle this
    }
}
