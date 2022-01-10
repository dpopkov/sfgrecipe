package learn.sfg.sfgrecipe.controllers;

import learn.sfg.sfgrecipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable Long recipeId, Model model) {
        log.debug("listIngredients for ID = {}", recipeId);
        model.addAttribute("recipe", recipeService.findById(recipeId).orElseThrow());
        return "recipe/ingredient/list";
    }
}
