package learn.sfg.sfgrecipe.converters;

import learn.sfg.sfgrecipe.domain.Category;
import learn.sfg.sfgrecipe.commands.CategoryCommand;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {
    @Override
    public Category convert(CategoryCommand source) {
        Category category = new Category();
        category.setId(source.getId());
        category.setDescription(source.getDescription());
        return category;
    }
}
