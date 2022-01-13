package learn.sfg.sfgrecipe.services;

import learn.sfg.sfgrecipe.commands.IngredientCommand;
import learn.sfg.sfgrecipe.commands.RecipeCommand;
import learn.sfg.sfgrecipe.commands.UnitOfMeasureCommand;
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
        }
        Recipe savedRecipe = recipeRepository.save(existingRecipe);
        Optional<Ingredient> savedIngredientOpt = findUpdatedIngredientById(savedRecipe, command);
        // On a new Ingredient we don't have an id value
        if (savedIngredientOpt.isEmpty()) {
            savedIngredientOpt = findNewIngredientByContent(savedRecipe, command);
        }
        final Ingredient ingredient = savedIngredientOpt.orElseThrow();
        return ingredientConverter.convert(ingredient);
    }

    @Override
    public IngredientCommand createEmptyIngredientForRecipe(RecipeCommand recipeCommand) {
        IngredientCommand newIngredient = new IngredientCommand();
        newIngredient.setRecipeId(recipeCommand.getId());
        newIngredient.setUom(new UnitOfMeasureCommand());
        return newIngredient;
    }

    private Optional<Ingredient> findUpdatedIngredientById(Recipe savedRecipe, IngredientCommand command) {
        return savedRecipe.getIngredients()
                .stream()
                .filter(i -> i.getId().equals(command.getId()))
                .findFirst();
    }

    private Optional<Ingredient> findNewIngredientByContent(Recipe savedRecipe, IngredientCommand command) {
        return savedRecipe.getIngredients()
                .stream()
                .filter(i -> i.getDescription() != null && i.getDescription().equals(command.getDescription())
                        && i.getAmount() != null && i.getAmount().equals(command.getAmount())
                        && i.getUom() != null && command.getUom() != null
                        && i.getUom().getId().equals(command.getUom().getId()))
                .findFirst();
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
