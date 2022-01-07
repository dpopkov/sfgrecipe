package learn.sfg.sfgrecipe.converters;

import learn.sfg.sfgrecipe.commands.UnitOfMeasureCommand;
import learn.sfg.sfgrecipe.domain.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {
        if (source == null) {
            return null;
        }
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(source.getId());
        command.setDescription(source.getDescription());
        return command;
    }
}
