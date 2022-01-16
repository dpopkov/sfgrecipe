package learn.sfg.sfgrecipe.controllers;

import learn.sfg.sfgrecipe.commands.RecipeCommand;
import learn.sfg.sfgrecipe.services.ImageService;
import learn.sfg.sfgrecipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public String showUploadForm(@PathVariable Long recipeId, Model model) {
        log.debug("showing image upload form for recipe id {}", recipeId);
        RecipeCommand recipeCommand = recipeService.findById(recipeId).orElseThrow();
        model.addAttribute("recipe", recipeCommand);
        return "recipe/imageuploadform";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable Long recipeId, @RequestParam("imagefile") MultipartFile file) {
        log.debug("received image file {} for recipe id {}", file.getOriginalFilename(), recipeId);
        imageService.saveImageFile(recipeId, file);
        return "redirect:/recipe/{recipeId}/show";
    }
}
