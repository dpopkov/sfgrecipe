package learn.sfg.sfgrecipe.controllers;

import learn.sfg.sfgrecipe.domain.Recipe;
import learn.sfg.sfgrecipe.services.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {
    @Mock
    RecipeService recipeService;
    @InjectMocks
    IndexController controller;
    @Mock
    Model model;
    @Captor
    ArgumentCaptor<Set<Recipe>> captor;

    @Test
    void testGetIndexPage() {
        // Given
        given(recipeService.findAll()).willReturn(List.of(new Recipe(), new Recipe()));
        // When
        String viewName = controller.getIndexPage(model);
        // Then
        then(recipeService).should().findAll();
        then(model).should().addAttribute(eq("recipes"), captor.capture());
        assertThat(viewName).isEqualTo("index");
        assertThat(captor.getValue()).hasSize(2);
    }
}
