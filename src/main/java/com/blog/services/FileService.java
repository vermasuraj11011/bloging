package com.blog.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface FileService {
    String uploadImage(String path, MultipartFile file) throws Exception;
    InputStream getResources(String path, String fileName) throws FileNotFoundException;
}
