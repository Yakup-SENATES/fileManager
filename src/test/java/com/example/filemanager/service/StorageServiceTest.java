package com.example.filemanager.service;

import com.example.filemanager.FileManagerApplication;
import com.example.filemanager.entity.ImageData;
import com.example.filemanager.repository.StorageRepository;
import com.example.filemanager.util.ImageUtils;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)// Mark the class to run as test case using SpringExtension class.
@SpringBootTest(classes = FileManagerApplication.class)
class StorageServiceTest {

    @MockBean
    private StorageRepository storageRepository;

    @Autowired
    private StorageService storageService;


    /**
     * Method under test: {@link StorageService#uploadImage(MultipartFile)}
     */
    @Test
    void testUploadImage() throws IOException {
        FileInputStream inputFile = new FileInputStream( "C:\\Users\\yakup\\Desktop\\a.jpg");
        MockMultipartFile file = new MockMultipartFile(
                "data",
                "a.jpg",
                "image/jpeg",
                inputFile);

        ImageData imageData = ImageData
                .builder()
                .name("a.jpg")
                .type("image/jpeg")
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .build();

        when(storageRepository.save(any())).thenReturn(imageData);
        assertTrue(storageService.uploadImage(file).contains("a.jpg"));
    }

    @Test
    void testDownloadImage() {

        ImageData imageData = ImageData
                .builder()
                .name("a.jpg")
                .type("image/jpeg")
                .imageData(new byte[10])
                .build();
        Optional<ImageData> ofResult = Optional.of(imageData);
        when(storageRepository.findByName(any())).thenReturn(ofResult);
        assertEquals(0, storageService.downloadImage("a.jpg").length);
        verify(storageRepository).findByName(any());

    }


}