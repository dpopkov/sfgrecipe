package learn.sfg.sfgrecipe.services;

import learn.sfg.sfgrecipe.domain.Recipe;
import learn.sfg.sfgrecipe.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    ImageServiceImpl service;
    @Captor
    ArgumentCaptor<Recipe> captor;

    @Test
    void testSaveImageFile() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt",
                "text/plain", "Spring Framework".getBytes());
        final Long recipeId = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        given(recipeRepository.findById(recipeId)).willReturn(Optional.of(recipe));

        service.saveImageFile(recipeId, multipartFile);

        then(recipeRepository).should().save(captor.capture());
        Recipe savedRecipe = captor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
    }
}
