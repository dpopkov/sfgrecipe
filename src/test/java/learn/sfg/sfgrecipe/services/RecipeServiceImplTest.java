package learn.sfg.sfgrecipe.services;

import learn.sfg.sfgrecipe.domain.Recipe;
import learn.sfg.sfgrecipe.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;
    @InjectMocks
    RecipeServiceImpl service;

    @Test
    void testFindAll() {
        given(recipeRepository.findAll()).willReturn(List.of(new Recipe(), new Recipe()));
        Iterable<Recipe> all = service.findAll();
        assertThat(all).hasSize(2);
        then(recipeRepository).should().findAll();
    }
}
