package nl.lee.moviestars.controller;

import nl.lee.moviestars.model.Image;
import nl.lee.moviestars.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/images")
@CrossOrigin


public class ImageController {

    @Autowired
    private ImageService imageService;


    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    @GetMapping
    public ResponseEntity<Collection<Image>> getAllImages() {
        return ResponseEntity.ok().body(imageService.getAllFiles());
    }


    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        Image image = imageService.getFileById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename =\"" + image.getFileName() + "\"")
                .body(image.getFile());
    }


    @PostMapping
    public ResponseEntity<Object> upLoadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        Long newId = imageService.uploadFile(multipartFile);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();
        return ResponseEntity.created(location).header("Access-Control-Expose-Headers", "Location").build();
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteImage(@PathVariable("id") long id) {
        imageService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }


}
