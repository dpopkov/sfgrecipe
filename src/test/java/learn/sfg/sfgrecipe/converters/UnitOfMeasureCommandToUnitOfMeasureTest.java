package learn.sfg.sfgrecipe.converters;

import learn.sfg.sfgrecipe.commands.UnitOfMeasureCommand;
import learn.sfg.sfgrecipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest extends AbstractConverterTest {

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    void testConvertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    void testConvert() {
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(LONG_ID);
        command.setDescription(DESCRIPTION);

        UnitOfMeasure uom = converter.convert(command);

        assertNotNull(uom);
        assertEquals(LONG_ID, uom.getId());
        assertEquals(DESCRIPTION, uom.getDescription());
    }
}
