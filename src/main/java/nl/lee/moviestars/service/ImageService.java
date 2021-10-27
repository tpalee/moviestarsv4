package nl.lee.moviestars.service;


import nl.lee.moviestars.exceptions.RecordNotFoundException;
import nl.lee.moviestars.model.Image;
import nl.lee.moviestars.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;


@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Long uploadFile(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        Image image = new Image(fileName, multipartFile.getContentType(), multipartFile.getBytes());
        imageRepository.save(image);
        return image.getId();
    }

    public Optional<Image> getFileById(Long id){
        if (!imageRepository.existsById(id)) throw new RecordNotFoundException();
        return imageRepository.findById(id);
    }

    public Collection<Image> getAllFiles(){
        return imageRepository.findAll();
    }



    //update an existing file
    public void updateFile(long id, MultipartFile newMultipartFile) throws IOException {
        if (!imageRepository.existsById(id)) throw new RecordNotFoundException();
        Image image = imageRepository.findById(id).get();
        image.setFileName(newMultipartFile.getOriginalFilename());
        image.setType(newMultipartFile.getContentType());
        image.setFile(newMultipartFile.getBytes());
        imageRepository.save(image);
    }

    //delete an existing file
    public void deleteFile(long id)  {
        if (!imageRepository.existsById(id)) throw new RecordNotFoundException();
        imageRepository.deleteById(id);
    }



}
