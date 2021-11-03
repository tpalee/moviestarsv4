package nl.lee.moviestars.controller;

import nl.lee.moviestars.model.Image;
import nl.lee.moviestars.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
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

    //connect Service to Controller
    @Autowired
    private ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    // get all Images from db
    @GetMapping
    public ResponseEntity<Collection<Image>> getAllFiles(){
            return ResponseEntity.ok().body(imageService.getAllFiles());
    }

    // find Image by Id from db
    @GetMapping(value = "/{id}")
    public ResponseEntity getFileById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(imageService.getFileById(id));
    }

    // Upload an image to db
    @PostMapping
    public ResponseEntity<Object> upLoadFile(@RequestParam("file")MultipartFile multipartFile) throws IOException {
        Long newId = imageService.uploadFile(multipartFile);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();
        return ResponseEntity.created(location).header("Access-Control-Expose-Headers", "Location").build();
    }

    //replace an existing image
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateFile(@PathVariable("id") long id, @RequestParam("file")MultipartFile newMultipartFile) throws IOException {
        imageService.updateFile(id, newMultipartFile);
        return ResponseEntity.noContent().build();
    }

    //delete movie
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteImage(@PathVariable("id") long id) {
        imageService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }


}
