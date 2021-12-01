package nl.lee.moviestars.service;
import nl.lee.moviestars.exceptions.RecordNotFoundException;
import nl.lee.moviestars.model.Image;
import nl.lee.moviestars.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Collection;


@Service
public class ImageService {


    @Autowired
    private ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    public Collection<Image> getAllFiles(){
        return imageRepository.findAll();
    }


    public Image getFileById(Long id) {
        var optionalImage = imageRepository.findById(id);
        if (optionalImage.isPresent()) {
            return optionalImage.get();
        } else {
            throw new RecordNotFoundException();
        }
    }


    public Long uploadFile(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        Image image = new Image(fileName, multipartFile.getContentType(), multipartFile.getBytes());
        imageRepository.save(image);
        return image.getId();
    }


    public void deleteFile(long id)  {
        if (!imageRepository.existsById(id)) throw new RecordNotFoundException();
        imageRepository.deleteById(id);
    }



}
