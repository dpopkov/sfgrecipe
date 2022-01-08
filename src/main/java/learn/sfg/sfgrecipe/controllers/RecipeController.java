package learn.sfg.sfgrecipe.controllers;

import learn.sfg.sfgrecipe.commands.RecipeCommand;
import learn.sfg.sfgrecipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/show/{recipeId}")
    public String show(@PathVariable Long recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findById(recipeId).orElseThrow());
        return "/recipe/show";
    }

    @GetMapping("/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "/recipe/recipeform";
    }

    @PostMapping("/")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/show/" + savedCommand.getId();
    }
}
