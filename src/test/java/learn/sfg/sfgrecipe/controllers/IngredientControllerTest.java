package learn.sfg.sfgrecipe.controllers;

import learn.sfg.sfgrecipe.commands.IngredientCommand;
import learn.sfg.sfgrecipe.commands.RecipeCommand;
import learn.sfg.sfgrecipe.services.IngredientService;
import learn.sfg.sfgrecipe.services.RecipeService;
import learn.sfg.sfgrecipe.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    IngredientService ingredientService;
    @Mock
    UnitOfMeasureService unitOfMeasureService;
    @InjectMocks
    IngredientController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testListIngredients() throws Exception {
        final Long recipeId = 12L;
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipeId);
        given(recipeService.findById(recipeId)).willReturn(Optional.of(recipeCommand));

        mockMvc.perform(get("/recipe/{recipeId}/ingredients", recipeId))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        then(recipeService).should().findById(recipeId);
    }

    @Test
    void testShowIngredient() throws Exception {
        final Long recipeId = 12L;
        final Long ingredientId = 123L;
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ingredientId);
        given(ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId)).willReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/{recipeId}/ingredient/{ingredientId}/show", recipeId, ingredientId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(view().name("recipe/ingredient/show"));

        then(ingredientService).should().findByRecipeIdAndIngredientId(recipeId, ingredientId);
    }

    @Test
    void testShowUpdateIngredientForm() throws Exception {
        final Long recipeId = 12L;
        final Long ingredientId = 123L;
        given(ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId))
                .willReturn(new IngredientCommand());

        mockMvc.perform(get("/recipe/{recipeId}/ingredient/{ingredientId}/update", recipeId, ingredientId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient", "uomAll"))
                .andExpect(view().name("recipe/ingredient/ingredientForm"));

        then(ingredientService).should().findByRecipeIdAndIngredientId(recipeId, ingredientId);
        then(unitOfMeasureService).should().findAll();
    }

    @Test
    void testSaveOrUpdateIngredient() throws Exception {
        final Long recipeId = 12L;
        final Long ingredientId = 123L;
        IngredientCommand command = IngredientCommand.builder().id(ingredientId).recipeId(recipeId).build();
        given(ingredientService.saveIngredientCommand(any())).willReturn(command);

        String expectedViewName = "redirect:/recipe/" + recipeId + "/ingredient/" + ingredientId + "/show";
        mockMvc.perform(post("/recipe/{recipeId}/ingredient", recipeId)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", ingredientId.toString())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(expectedViewName));
    }
}
