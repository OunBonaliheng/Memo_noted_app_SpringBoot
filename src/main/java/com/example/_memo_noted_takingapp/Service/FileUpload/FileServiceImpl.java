package com.example._memo_noted_takingapp.Service.FileUpload;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {
    private final Path path = Paths.get("src/main/resources/images");


    @Override
    public List<String> saveFile(List<MultipartFile> files) throws IOException {
        List<String> savedFileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String extension = StringUtils.getFilenameExtension(originalFilename);
                if (extension == null || extension.isBlank()) {
                    extension = "png"; // Default extension if none found
                }
                String fileName = UUID.randomUUID().toString() + "." + extension;
                Files.copy(file.getInputStream(), path.resolve(fileName));
                savedFileNames.add(fileName);
            }
        }
        return savedFileNames;
    }

    @Override
    public Resource getFileByFileName(String fileName) throws IOException {
        Path filePath = path.resolve(fileName);
        if (Files.exists(filePath)) {
            return new ByteArrayResource(Files.readAllBytes(filePath));
        } else {
            throw new IOException("File not found: " + fileName);
        }
    }
}
