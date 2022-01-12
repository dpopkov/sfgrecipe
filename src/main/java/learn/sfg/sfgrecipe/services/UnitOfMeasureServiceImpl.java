package learn.sfg.sfgrecipe.services;

import learn.sfg.sfgrecipe.commands.UnitOfMeasureCommand;
import learn.sfg.sfgrecipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import learn.sfg.sfgrecipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.uomConverter = uomConverter;
    }

    @Override
    public Collection<UnitOfMeasureCommand> findAll() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(uomConverter::convert)
                .collect(Collectors.toList());
    }
}
