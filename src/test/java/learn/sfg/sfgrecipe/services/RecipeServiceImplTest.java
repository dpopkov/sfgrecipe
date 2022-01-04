package learn.sfg.sfgrecipe.services;

import learn.sfg.sfgrecipe.domain.Recipe;
import learn.sfg.sfgrecipe.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @Test
    void testGetRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        given(recipeRepository.findById(anyLong())).willReturn(Optional.of(recipe));

        final Recipe found = service.findById(1L).orElseThrow();

        then(recipeRepository).should().findById(anyLong());
        then(recipeRepository).shouldHaveNoMoreInteractions();
        assertThat(found.getId()).isEqualTo(1L);
    }

    @Test
    void testGetRecipeByIdNotFound() {
        given(recipeRepository.findById(anyLong())).willReturn(Optional.empty());

        final Optional<Recipe> byId = service.findById(1L);

        then(recipeRepository).should().findById(anyLong());
        then(recipeRepository).shouldHaveNoMoreInteractions();
        assertThat(byId).isEmpty();
    }
}
