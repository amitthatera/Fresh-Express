package com.store.grocery.fresh_express.service.impl;

import com.store.grocery.fresh_express.custom_exception.ApiException;
import com.store.grocery.fresh_express.custom_exception.FileNotSupportedException;
import com.store.grocery.fresh_express.custom_exception.ResourceNotFoundException;
import com.store.grocery.fresh_express.model.Image;
import com.store.grocery.fresh_express.repository.ImageRepository;
import com.store.grocery.fresh_express.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final ImageRepository imageRepository;

    public FileServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("image/jpeg", "image/png", "image/webp");

    @Override
    public Image uploadFile(MultipartFile file, String path) {
        File directory = new File(path);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new ApiException("Failed to create directories at " + path);
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new ApiException("File name cannot be null");
        }

        String randomId = UUID.randomUUID().toString();
        String newFileName = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));

        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            throw new FileNotSupportedException("Image Not Supported! Only JPEG/PNG/WEBP Files Allowed!");
        }

        Image image = Image.builder()
                .imageName(newFileName)
                .imageType(file.getContentType())
                .imageSize(file.getSize())
                .build();

        String filePath = path + File.separator + newFileName;

        try {
            file.transferTo(Paths.get(filePath));
            logger.info("File Transferred successfully to {}", filePath);
        } catch (IllegalStateException | IOException e) {
            logger.error("Error while transferring file: {}", e.getMessage(), e);
            throw new ApiException("Error occurred while transferring file");
        }
        imageRepository.save(image);
        return image;
    }

    @Override
    public Set<Image> uploadFiles(MultipartFile[] files, String path) {
        Set<Image> images = new HashSet<>();

        File directory = new File(path);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new ApiException("Failed to create directories at " + path);
        }

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            if (fileName == null) {
                throw new ApiException("File name cannot be null");
            }

            String randomId = UUID.randomUUID().toString();
            String newFileName = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));

            if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
                throw new FileNotSupportedException("Image Not Supported! Only JPEG/PNG/WEBP Files Allowed!");
            }

            Image image = Image.builder()
                    .imageName(newFileName)
                    .imageType(file.getContentType())
                    .imageSize(file.getSize())
                    .build();
            images.add(image);

            String filePath = path + File.separator + newFileName;
            try {
                file.transferTo(Paths.get(filePath));
                logger.info("File transferred successfully to {}", filePath);
            } catch (IllegalStateException | IOException e) {
                logger.error("Error while transferring file: {}", e.getMessage(), e);
                throw new ApiException("Error occurred while transferring file");
            }
        }

        imageRepository.saveAll(images);
        return images;
    }


    @Override
    public InputStream serveImage(String imageFolder, String fileName) {
        String path = Paths.get(imageFolder + fileName).toString();
        InputStream stream;
        try {
            stream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new ResourceNotFoundException("IMAGE NOT AVAILABLE !!");
        }
        return stream;
    }
}
