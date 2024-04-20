package com.example._memo_noted_takingapp.Service.FileUpload;

import com.example._memo_noted_takingapp.Exception.InvalidInputException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class AppFileServiceImpl implements AppFileService {

    //reference path to directory profile_images
    private final Path root = Paths.get("src/main/resources/Image&Video");
    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        if (file.getOriginalFilename().toLowerCase().matches(".*\\.(png|jpg|jpeg|svg|gif|mp4|mov|avi|wmv|flv|webm|mp3|mkv|heic)$")) {
            //auto create directory profile_images
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
            //upload profile_image to directory
            //convert fileName to UUID because we don't want to have duplicate file name UUID help skip duplicate file
            fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(fileName);
            //Insert file as byte to directory
            Files.copy(file.getInputStream(), root.resolve(fileName));
            return fileName;
        } else {
            throw new InvalidInputException("Your file extension is not supported by our program: " + fileName);
        }
    }


    @Override
    public Resource getFileByFileName(String fileName) throws IOException {
        Path path = Paths.get("src/main/resources/Image&Video/" + fileName);
        return new ByteArrayResource(Files.readAllBytes(path));
    }
}
