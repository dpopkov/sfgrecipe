package learn.sfg.sfgrecipe.controllers;

import learn.sfg.sfgrecipe.commands.RecipeCommand;
import learn.sfg.sfgrecipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        RecipeCommand command = new RecipeCommand();
        given(recipeService.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(command));
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("/recipe/show"))
                .andExpect(status().isOk());
        then(recipeService).should().findById(anyLong());
        then(recipeService).shouldHaveNoMoreInteractions();
    }

    @Test
    void testUpdateRecipe() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(12L);
        given(recipeService.findById(12L)).willReturn(Optional.of(command));

        mockMvc.perform(get("/recipe/12/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("/recipe/recipeform"));
    }

    @Test
    void testSave() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(12L);
        given(recipeService.saveRecipeCommand(any())).willReturn(command);

        mockMvc.perform(post("/recipe/")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "description-value")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/12/show"));
    }

    @Test
    void testDelete() throws Exception {
        final long recipeId = 21L;
        mockMvc.perform(get("/recipe/" + recipeId + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
        then(recipeService).should().deleteById(recipeId);
    }
}
