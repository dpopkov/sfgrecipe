package learn.sfg.sfgrecipe.commands;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<IngredientCommand> ingredientCommands = new HashSet<>();
    private String difficulty;
    private NotesCommand notesCommand;
    private Set<CategoryCommand> categoryCommands = new HashSet<>();
}
