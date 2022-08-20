package com.example.filemanager.controller;

import com.example.filemanager.FileManagerApplication;
import com.example.filemanager.repository.StorageRepository;
import com.example.filemanager.service.StorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)// Mark the class to run as test case using SpringExtension class.
@SpringBootTest(classes = FileManagerApplication.class)
class ImageControllerTest {

    @Autowired
    ImageController imageController;

    @MockBean
    private StorageService storageService;

    @Test
    void canUploadImage() throws Exception {
        FileInputStream inputFile = new FileInputStream("C:\\Users\\yakup\\Desktop\\a.jpg");
        MockMultipartFile file = new MockMultipartFile(
                "data",
                "a.jpg",
                "image/jpeg",
                inputFile);
        when(storageService.uploadImage(file)).thenReturn("a.jpg");
        ResponseEntity<?> responseEntity = imageController.uploadImage(file);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("a.jpg", responseEntity.getBody());
    }

    @Test
    void canDownloadImage() throws Exception {
    String imageName = "a.jpg";
    when(storageService.downloadImage(imageName)).thenReturn(new byte[0]);
        ResponseEntity<?> responseEntity = imageController.downloadImage(imageName);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody() instanceof byte[]);
    }
}