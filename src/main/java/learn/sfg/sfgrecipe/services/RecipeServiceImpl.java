package learn.sfg.sfgrecipe.services;

import learn.sfg.sfgrecipe.commands.RecipeCommand;
import learn.sfg.sfgrecipe.converters.RecipeCommandToRecipe;
import learn.sfg.sfgrecipe.converters.RecipeToRecipeCommand;
import learn.sfg.sfgrecipe.domain.Recipe;
import learn.sfg.sfgrecipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Iterable<Recipe> findAll() {
        log.debug("RecipeServiceImpl.findAll()");
        return recipeRepository.findAll();
    }

    @Override
    public Optional<RecipeCommand> findById(Long recipeId) {
        final Optional<Recipe> byId = recipeRepository.findById(recipeId);
        return byId.map(recipeToRecipeCommand::convert);
    }

    @Transactional
    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe newRecipe = recipeCommandToRecipe.convert(command);
        Recipe savedRecipe = recipeRepository.save(newRecipe);
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteById(Long recipeId) {
        recipeRepository.deleteById(recipeId);
    }
}
