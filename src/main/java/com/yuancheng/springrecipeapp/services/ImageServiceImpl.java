package com.yuancheng.springrecipeapp.services;

import com.yuancheng.springrecipeapp.models.Recipe;
import com.yuancheng.springrecipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

  private final RecipeRepository recipeRepository;

  public ImageServiceImpl(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  @Override
  @Transactional
  public void saveImageFile(Long recipeId, MultipartFile file) {
    log.debug("Received a file.");

    try {
      Recipe recipe = recipeRepository.findById(recipeId).get();

      // convert primitive byte array to Byte objects
      Byte[] byteObjects = new Byte[file.getBytes().length];
      int i = 0;
      for (byte b : file.getBytes()) {
        byteObjects[i++] = b;
      }

      recipe.setImage(byteObjects);

      recipeRepository.save(recipe);
    } catch (IOException e) {
      // todo handle error
      log.error("Error occurred: " + e);
      e.printStackTrace();
    }
  }
}
