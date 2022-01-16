package learn.sfg.sfgrecipe.controllers;

import learn.sfg.sfgrecipe.commands.RecipeCommand;
import learn.sfg.sfgrecipe.services.ImageService;
import learn.sfg.sfgrecipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {
    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;
    @InjectMocks
    ImageController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testShowUploadForm() throws Exception {
        final Long recipeId = 1L;
        given(recipeService.findById(recipeId)).willReturn(Optional.of(new RecipeCommand()));
        mockMvc.perform(get("/recipe/{recipeId}/image", recipeId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/imageuploadform"));

        then(recipeService).should().findById(recipeId);
    }

    @Test
    void testHandleImagePost() throws Exception {
        byte[] content = "Spring Framework".getBytes();
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt",
                "text/plain", content);
        final Long recipeId = 1L;
        mockMvc.perform(multipart("/recipe/{recipeId}/image", recipeId)
                .file(multipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/" + recipeId + "/show"));

        then(imageService).should().saveImageFile(eq(recipeId), any(MultipartFile.class));
    }
}
