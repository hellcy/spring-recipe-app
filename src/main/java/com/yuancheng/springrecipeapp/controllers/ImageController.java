package com.yuancheng.springrecipeapp.controllers;

import com.yuancheng.springrecipeapp.commands.RecipeCommand;
import com.yuancheng.springrecipeapp.services.ImageService;
import com.yuancheng.springrecipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
public class ImageController {
  private final RecipeService recipeService;
  private final ImageService imageService;

  public ImageController(RecipeService recipeService, ImageService imageService) {
    this.recipeService = recipeService;
    this.imageService = imageService;
  }

  @GetMapping("/recipe/{recipeId}/image")
  public String showUploadForm(@PathVariable String recipeId, Model model) {
    RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

    model.addAttribute("recipe", recipeCommand);
    return "/recipe/imageuploadform";
  }

  @PostMapping("/recipe/{recipeId}/image")
  public String handleImagePost(@PathVariable String recipeId,
                                @RequestParam("imagefile")MultipartFile file) {
    imageService.saveImageFile(Long.valueOf(recipeId), file);

    return "redirect:/recipe/" + recipeId + "/show";
  }

  @GetMapping("/recipe/{recipeId}/recipeimage")
  public void renderImageFromDB(@PathVariable String recipeId,
                                HttpServletResponse response) throws IOException {
    log.debug("Render image from DB for Recipe id: " + recipeId);
    RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
    if (recipeCommand.getImage() != null) {
      byte[] byteArray = new byte[recipeCommand.getImage().length];
      int i = 0;
      for (Byte b : recipeCommand.getImage()) {
        // todo auto unboxing
        byteArray[i++] = b;
      }

      // convert input stream to output stream
      response.setContentType("image/jpeg");
      InputStream is = new ByteArrayInputStream(byteArray);
      IOUtils.copy(is, response.getOutputStream());
    }
  }
}
