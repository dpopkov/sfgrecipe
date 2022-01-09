package learn.sfg.sfgrecipe.converters;

import learn.sfg.sfgrecipe.commands.IngredientCommand;
import learn.sfg.sfgrecipe.commands.UnitOfMeasureCommand;
import learn.sfg.sfgrecipe.domain.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest extends AbstractConverterTest {

    IngredientCommandToIngredient converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    void testConvertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    void testConvert() {
        IngredientCommand command = new IngredientCommand();
        command.setId(LONG_ID);
        command.setAmount(AMOUNT);
        command.setDescription(DESCRIPTION);
        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId(UOM_ID);
        command.setUom(uomCommand);

        Ingredient ingredient = converter.convert(command);

        assertNotNull(ingredient);
        assertNotNull(ingredient.getUom());
        assertEquals(LONG_ID, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(UOM_ID, ingredient.getUom().getId());
    }
}
