package learn.sfg.sfgrecipe.services;

import learn.sfg.sfgrecipe.domain.Recipe;
import learn.sfg.sfgrecipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        log.debug("saving image for recipe id {}", recipeId);
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();
        try {
            byte[] bytesOfImage = Arrays.copyOf(file.getBytes(), file.getBytes().length);
            recipe.setImage(bytesOfImage);
            recipeRepository.save(recipe);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed getting bytes from image", e);
        }
    }
}
