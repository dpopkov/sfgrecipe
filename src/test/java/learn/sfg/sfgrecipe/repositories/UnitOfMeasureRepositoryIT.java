package learn.sfg.sfgrecipe.repositories;

import learn.sfg.sfgrecipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository repository;

    @Test
    void testFindByDescription() {
        final UnitOfMeasure teaspoon = repository.findByDescription("Teaspoon").orElseThrow();
        assertEquals(teaspoon.getDescription(), "Teaspoon");
    }
}
