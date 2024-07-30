package com.store.grocery.fresh_express.service;

import com.store.grocery.fresh_express.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Set;

public interface FileService {

    Image uploadFile(MultipartFile file, String filePath);

    Set<Image> uploadFiles(MultipartFile[] files, String filePath);

    void deleteImage(String publicId, String path);

}
