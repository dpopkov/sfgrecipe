package learn.sfg.sfgrecipe.services;

import learn.sfg.sfgrecipe.commands.UnitOfMeasureCommand;

import java.util.Collection;

public interface UnitOfMeasureService {

    Collection<UnitOfMeasureCommand> findAll();
}
