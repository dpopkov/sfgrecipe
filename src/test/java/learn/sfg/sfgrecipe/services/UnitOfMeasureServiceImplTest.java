package learn.sfg.sfgrecipe.services;

import learn.sfg.sfgrecipe.commands.UnitOfMeasureCommand;
import learn.sfg.sfgrecipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import learn.sfg.sfgrecipe.domain.UnitOfMeasure;
import learn.sfg.sfgrecipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    @Mock
    UnitOfMeasureToUnitOfMeasureCommand uomConverter;
    @InjectMocks
    UnitOfMeasureServiceImpl service;

    @Test
    void testFindAll() {
        Iterable<UnitOfMeasure> unitOfMeasures = List.of(new UnitOfMeasure());
        given(unitOfMeasureRepository.findAll()).willReturn(unitOfMeasures);

        Collection<UnitOfMeasureCommand> all = service.findAll();

        assertEquals(1, all.size());
        then(unitOfMeasureRepository).should().findAll();
        then(uomConverter).should().convert(any());
    }
}
