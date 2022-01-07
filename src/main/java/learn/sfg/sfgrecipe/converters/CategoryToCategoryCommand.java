package learn.sfg.sfgrecipe.converters;

import learn.sfg.sfgrecipe.commands.CategoryCommand;
import learn.sfg.sfgrecipe.domain.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) {
            return null;
        }
        CategoryCommand command = new CategoryCommand();
        command.setId(source.getId());
        command.setDescription(source.getDescription());
        return command;
    }
}
