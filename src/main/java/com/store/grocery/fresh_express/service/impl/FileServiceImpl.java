package com.store.grocery.fresh_express.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.store.grocery.fresh_express.custom_exception.ApiException;
import com.store.grocery.fresh_express.custom_exception.FileNotSupportedException;
import com.store.grocery.fresh_express.model.Image;
import com.store.grocery.fresh_express.repository.ImageRepository;
import com.store.grocery.fresh_express.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {

    private final ImageRepository imageRepository;

    private final Cloudinary cloudinary;

    public FileServiceImpl(ImageRepository imageRepository, Cloudinary cloudinary) {
        this.imageRepository = imageRepository;
        this.cloudinary = cloudinary;

    }

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("image/jpeg", "image/png", "image/webp");

    @Override
    public Image uploadFile(MultipartFile file, String filePath) {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new ApiException("File name cannot be null");
        }

        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            throw new FileNotSupportedException("Image Not Supported! Only JPEG/PNG/WEBP Files Allowed!");
        }

        String newFileName = UUID.randomUUID().toString();

        Image image;

        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", filePath,
                            "public_id", newFileName
                    ));
            image = Image.builder()
                    .imageName(newFileName)
                    .imageType(file.getContentType())
                    .imageSize(file.getSize())
                    .imageUrl((String) uploadResult.get("url"))
                    .build();
        } catch (IllegalStateException | IOException e) {
            logger.error("Error while transferring file: {}", e.getMessage(), e);
            throw new ApiException("Error occurred while uploading file");
        }
        imageRepository.save(image);
        return image;
    }

    @Override
    public Set<Image> uploadFiles(MultipartFile[] files, String filePath) {
        Set<Image> images = new HashSet<>();

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            if (fileName == null) {
                throw new ApiException("File name cannot be null");
            }

            if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
                throw new FileNotSupportedException("Image Not Supported! Only JPEG/PNG/WEBP Files Allowed!");
            }

            String newFileName = UUID.randomUUID().toString();

            try {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap(
                                "folder", filePath,
                                "public_id", newFileName
                        ));
                Image image = Image.builder()
                        .imageName(newFileName)
                        .imageType(file.getContentType())
                        .imageSize(file.getSize())
                        .imageUrl((String) uploadResult.get("url"))
                        .build();
                images.add(image);
            } catch (IllegalStateException | IOException e) {
                logger.error("Error while transferring file: {}", e.getMessage(), e);
                throw new ApiException("Error occurred while uploading file");
            }
        }

        imageRepository.saveAll(images);
        return images;
    }

    @Override
    public void deleteImage(String publicId, String path) {
        try {
            cloudinary.api().deleteResources(Collections.singletonList(path + "/" + publicId), ObjectUtils.asMap(
                    "type", "upload",
                    "resource_type", "image"
            ));
        } catch (IOException e) {
            logger.error("Error while deleting file from Cloudinary: {}", e.getMessage(), e);
            throw new ApiException("Error occurred while deleting file");
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

}
