package learn.sfg.sfgrecipe.converters;

import learn.sfg.sfgrecipe.commands.CategoryCommand;
import learn.sfg.sfgrecipe.commands.IngredientCommand;
import learn.sfg.sfgrecipe.commands.NotesCommand;
import learn.sfg.sfgrecipe.commands.RecipeCommand;
import learn.sfg.sfgrecipe.domain.Difficulty;
import learn.sfg.sfgrecipe.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest extends AbstractConverterTest {

    private static final Integer PREP_TIME = 10;
    private static final Integer COOK_TIME = 20;
    private static final Integer SERVINGS = 4;
    private static final String SOURCE = "Source";
    private static final String URL = "url";
    private static final String DIRECTIONS = "directions";
    private static final String DIFFICULTY = Difficulty.MODERATE.getDisplayName();
    public static final long NOTES_ID = 30L;

    RecipeCommandToRecipe converter;

    @BeforeEach
    void setUp() {
        UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandConverter = new UnitOfMeasureCommandToUnitOfMeasure();
        IngredientCommandToIngredient ingredientCommandConverter = new IngredientCommandToIngredient(unitOfMeasureCommandConverter);
        CategoryCommandToCategory categoryCommandConverter = new CategoryCommandToCategory();
        NotesCommandToNotes notesCommandConverter = new NotesCommandToNotes();
        converter = new RecipeCommandToRecipe(ingredientCommandConverter, categoryCommandConverter, notesCommandConverter);
    }

    @Test
    void testConvertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    void testConvert() {
        RecipeCommand command = new RecipeCommand();
        command.setId(LONG_ID);
        command.setDescription(DESCRIPTION);
        command.setPrepTime(PREP_TIME);
        command.setCookTime(COOK_TIME);
        command.setServings(SERVINGS);
        command.setSource(SOURCE);
        command.setUrl(URL);
        command.setDirections(DIRECTIONS);
        IngredientCommand ic1 = new IngredientCommand();
        ic1.setId(20L);
        IngredientCommand ic2 = new IngredientCommand();
        ic2.setId(21L);
        command.setIngredients(Set.of(ic1, ic2));
        command.setDifficulty(DIFFICULTY);
        NotesCommand nc = new NotesCommand();
        nc.setId(NOTES_ID);
        command.setNotes(nc);
        CategoryCommand cc1 = new CategoryCommand();
        cc1.setId(40L);
        CategoryCommand cc2 = new CategoryCommand();
        cc2.setId(41L);
        command.setCategories(Set.of(cc1, cc2));

        Recipe recipe = converter.convert(command);

        assertNotNull(recipe);
        assertEquals(LONG_ID, recipe.getId());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(DIFFICULTY, recipe.getDifficulty().getDisplayName());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(2, recipe.getIngredients().size());
        assertEquals(2, recipe.getCategories().size());
    }
}
