package learn.sfg.sfgrecipe.controllers;

import learn.sfg.sfgrecipe.commands.IngredientCommand;
import learn.sfg.sfgrecipe.commands.RecipeCommand;
import learn.sfg.sfgrecipe.services.IngredientService;
import learn.sfg.sfgrecipe.services.RecipeService;
import learn.sfg.sfgrecipe.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable Long recipeId, Model model) {
        log.debug("listIngredients for ID = {}", recipeId);
        model.addAttribute("recipe", recipeService.findById(recipeId).orElseThrow());
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId, Model model) {
        log.debug("showIngredient for Recipe ID = {} and Ingredient ID = {}", recipeId, ingredientId);
        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String showUpdateIngredientForm(@PathVariable Long recipeId,
                                           @PathVariable Long ingredientId,
                                           Model model) {
        log.debug("showUpdateIngredientForm for Recipe ID = {} and Ingredient ID = {}", recipeId, ingredientId);
        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
        model.addAttribute("uomAll", unitOfMeasureService.findAll());
        return "recipe/ingredient/ingredientForm";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String showNewIngredientForm(@PathVariable Long recipeId, Model model) {
        RecipeCommand recipeCommand = recipeService.findById(recipeId).orElseThrow(); // todo: raise exception if not found
        IngredientCommand newIngredient = ingredientService.createEmptyIngredientForRecipe(recipeCommand);
        model.addAttribute("ingredient", newIngredient);
        model.addAttribute("uomAll", unitOfMeasureService.findAll());
        return "recipe/ingredient/ingredientForm";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdateIngredient(@PathVariable Long recipeId,
                                         @ModelAttribute IngredientCommand command) {
        IngredientCommand saved = ingredientService.saveIngredientCommand(command);
        log.debug("saved ingredient with recipe id={} and ingredient id={}", saved.getRecipeId(), saved.getId());
        return "redirect:/recipe/" + saved.getRecipeId() + "/ingredient/" + saved.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable Long recipeId,
                                   @PathVariable Long ingredientId) {
        log.debug("deleting ingredient with recipe id={} and ingredient id={}", recipeId, ingredientId);
        ingredientService.deleteById(recipeId, ingredientId);
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
