package learn.sfg.sfgrecipe.converters;

import learn.sfg.sfgrecipe.commands.NotesCommand;
import learn.sfg.sfgrecipe.domain.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Override
    public NotesCommand convert(Notes source) {
        if (source == null) {
            return null;
        }
        NotesCommand command = new NotesCommand();
        command.setId(source.getId());
        command.setRecipeNotes(source.getRecipeNotes());
        return command;
    }
}
