package learn.sfg.sfgrecipe.controllers;

import learn.sfg.sfgrecipe.domain.Recipe;
import learn.sfg.sfgrecipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"", "/", "index"})
    public String getIndexPage(Model model) {
        final Iterable<Recipe> recipes = recipeService.findAll();
        model.addAttribute("recipes", recipes);
        return "index";
    }
}
