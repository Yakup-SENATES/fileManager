package com.example.filemanager.repository;

import com.example.filemanager.FileManagerApplication;
import com.example.filemanager.entity.ImageData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)// Mark the class to run as test case using SpringExtension class.
@SpringBootTest(classes = FileManagerApplication.class)// Configure the Spring Boot application.
class StorageRepositoryTest {

    @Autowired
    private StorageRepository storageRepository;

    @Test
    public void testSave() throws Exception {
        ImageData imageData = getData();
        ImageData savedImageData = storageRepository.save(imageData);
        assertEquals(imageData, savedImageData);
        assertNotNull(savedImageData.getId());
    }
    @Test
    public void testFindByName(){
        ImageData image = ImageData
                .builder()
                .imageData(new byte[10])
                .name("search.jpg")
                .type("image/jpeg")
                .build();
        ImageData savedImage = storageRepository.save(image);
        Optional<ImageData> foundImage = storageRepository.findByName(image.getName());
        assertTrue(foundImage.isPresent());
        assertEquals(savedImage, foundImage.get());

    }

    @Test
    public void testFindById(){
        ImageData image = getData();
        ImageData savedImage = storageRepository.save(image);
        Optional<ImageData> foundImage = storageRepository.findById(savedImage.getId());
        assertTrue(foundImage.isPresent());
        assertEquals(savedImage, foundImage.get());
    }

    @Test
    public void testDeleteImage(){
        ImageData image = getData();
        storageRepository.delete(image);
        assertFalse(storageRepository.findByName(image.getName()).isPresent());

    }


    public ImageData getData(){
        return ImageData
                .builder()
                .imageData(new byte[10])
                .name("test.jpg")
                .type("image/jpeg")
                .build();

    }


}