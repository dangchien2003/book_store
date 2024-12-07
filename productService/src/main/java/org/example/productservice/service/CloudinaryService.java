package org.example.productservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {
    Map<String, Object> uploadFile(MultipartFile file, String folder) throws IOException;
}
