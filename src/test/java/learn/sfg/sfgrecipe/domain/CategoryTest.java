package learn.sfg.sfgrecipe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        category.setId(42L);
        assertEquals(42L, category.getId());
    }

    @Test
    void getDescription() {
        category.setDescription("Description");
        assertEquals("Description", category.getDescription());
    }
}
