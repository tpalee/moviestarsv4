package nl.lee.moviestars.service;

import nl.lee.moviestars.exceptions.RecordNotFoundException;
import nl.lee.moviestars.model.Image;
import nl.lee.moviestars.repository.ImageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageService imageService;


    @Test
    void getAllImagesWhenImagesArePresentTest() {
        List<Image> images = new ArrayList<>();
        Image image1 = new Image();
        image1.setId(1);
        images.add(image1);

        Mockito
                .when(imageRepository.findAll())
                .thenReturn(images);

        assertTrue(imageService.getAllFiles().size() == 1);
    }


    @Test
    void getImagesWhenImageIsPresentTest() {
        Image image1 = new Image();
        image1.setFileName("test");
        image1.setId(1);

        Mockito
                .when(imageRepository.findById(1L))
                .thenReturn(Optional.of(image1));

        assertTrue(imageService.getFileById(1L).getFileName() == "test");
    }


    @Test
    void deleteFileWhenFileIsPresentTest() {
        Image image1 = new Image();
        image1.setId(1);

        Mockito
                .when(imageRepository.existsById(1L))
                .thenReturn(true);

        imageService.deleteFile(1L);

        Mockito.verify(imageRepository, Mockito.times(1)).deleteById(1L);
    }


    @Test
    void ThrowExeptionWhenFileIsNotPresentTest() {

        Mockito
                .when(imageRepository.findById(1L))
                .thenReturn(Optional.empty());

        Mockito
                .when(imageRepository.existsById(1L))
                .thenReturn(false);

        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            imageService.getFileById(1L);
        }, "RecordNotFoundException error was expected");

        Assertions.assertThrows(RecordNotFoundException.class, () -> {
            imageService.deleteFile(1L);
        }, "RecordNotFoundException error was expected");
    }

}
