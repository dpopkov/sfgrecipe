package learn.sfg.sfgrecipe.bootstrap;

import learn.sfg.sfgrecipe.domain.*;
import learn.sfg.sfgrecipe.repositories.CategoryRepository;
import learn.sfg.sfgrecipe.repositories.RecipeRepository;
import learn.sfg.sfgrecipe.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository,
                      UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void run(String... args) {
        final List<Recipe> recipes = createRecipes();
        recipeRepository.saveAll(recipes);
        System.out.println("Recipes loaded....");
    }

    private List<Recipe> createRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        // Units of measure
        final UnitOfMeasure piece = unitOfMeasureRepository.findByDescription("Piece").orElseThrow();
        final UnitOfMeasure pinch = unitOfMeasureRepository.findByDescription("Pinch").orElseThrow();
        final UnitOfMeasure teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon").orElseThrow();
        final UnitOfMeasure tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon").orElseThrow();
        // Categories
        final Category mexican = categoryRepository.findByDescription("Mexican").orElseThrow();

        // Ingredients for Guacamole
        Ingredient avocados = new Ingredient("Avocado", BigDecimal.valueOf(2), piece);
        Ingredient salt = new Ingredient("Salt", BigDecimal.valueOf(3), pinch);

        // Create Guacamole recipe
        Recipe guacamole = new Recipe();
        guacamole.setDescription("Perfect Guacamole");
        guacamole.setPrepTime(10);
        guacamole.setCookTime(0);
        guacamole.setServings(3);
        guacamole.setSource("https://www.simplyrecipes.com");
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.setDirections("To slice open an avocado, cut it in half lengthwise with a sharp chef's knife and twist apart.");
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setNotes(new Notes("Never tried it"));
        guacamole.addIngredient(avocados);
        guacamole.addIngredient(salt);
        guacamole.addCategory(mexican);
        recipes.add(guacamole);

        // Ingredients for Tacos
        Ingredient driedOregano = new Ingredient("Dried Oregano", BigDecimal.ONE, teaspoon);
        Ingredient sugar = new Ingredient("Sugar", BigDecimal.ONE, teaspoon);
        Ingredient orangeZest = new Ingredient("Orange Zest", BigDecimal.ONE, tablespoon);
        Ingredient oliveOil  = new Ingredient("Olive Oil", BigDecimal.valueOf(2), tablespoon);
        Ingredient chickenThighs  = new Ingredient("Chicken Thighs", BigDecimal.valueOf(6), piece);

        // Create Tacos recipe
        Recipe tacos = new Recipe();
        tacos.setDescription("Spicy Grilled Chicken Tacos");
        tacos.setPrepTime(20);
        tacos.setCookTime(15);
        tacos.setServings(6);
        tacos.setSource("https://www.simplyrecipes.com/");
        tacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacos.setDirections("In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest.");
        tacos.setDifficulty(Difficulty.MODERATE);
        tacos.setNotes(new Notes("Never tried it yet"));
        tacos.addIngredient(driedOregano);
        tacos.addIngredient(sugar);
        tacos.addIngredient(orangeZest);
        tacos.addIngredient(oliveOil);
        tacos.addIngredient(chickenThighs);
        tacos.addCategory(mexican);
        recipes.add(tacos);

        return recipes;
    }
}
