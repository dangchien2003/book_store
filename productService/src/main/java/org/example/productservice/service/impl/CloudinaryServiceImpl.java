package org.example.productservice.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CloudinaryServiceImpl implements CloudinaryService {
    Cloudinary cloudinary;

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> uploadFile(MultipartFile file, String folder) throws IOException {
        File tempFile = convertMultipartFileToFile(file);

        try {
            return cloudinary.uploader().upload(tempFile, ObjectUtils.asMap(
                    "folder", folder));
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = File.createTempFile("upload-", file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        }
        return convertedFile;
    }
}
