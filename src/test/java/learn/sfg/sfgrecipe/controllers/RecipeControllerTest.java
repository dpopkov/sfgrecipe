package learn.sfg.sfgrecipe.controllers;

import learn.sfg.sfgrecipe.domain.Recipe;
import learn.sfg.sfgrecipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {
    @Mock
    RecipeService recipeService;
    @InjectMocks
    RecipeController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testShow() throws Exception {
        Recipe recipe = new Recipe();
        given(recipeService.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(recipe));
        mockMvc.perform(get("/recipe/show/1"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("/recipe/show"))
                .andExpect(status().isOk());
        then(recipeService).should().findById(anyLong());
        then(recipeService).shouldHaveNoMoreInteractions();
    }
}
