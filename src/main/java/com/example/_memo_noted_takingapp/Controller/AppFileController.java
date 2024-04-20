package com.example._memo_noted_takingapp.Controller;


import com.example._memo_noted_takingapp.Model.AppFile;
import com.example._memo_noted_takingapp.Model.dto.Response.APIResponse;
import com.example._memo_noted_takingapp.Service.FileUpload.AppFileService;
import lombok.AllArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/files")
@AllArgsConstructor

public class AppFileController {
    private final AppFileService appFileService;

    @PostMapping(value ="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) throws IOException {
        String fileName = appFileService.uploadFile(file);
        APIResponse<AppFile> response = APIResponse.<AppFile>builder()
                .message("Profile upload successful.")
                .payload(new AppFile(
                        fileName,
                        file.getContentType(),
                        file.getSize()
                ))
                .status(HttpStatus.CREATED)
                .creationDate(new Date())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<?> getFileByFileName(@RequestParam String fileName) throws IOException {
        Resource resource = appFileService.getFileByFileName(fileName);
        // Set default media type to octet-stream for all files
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg") ||
                fileName.toLowerCase().endsWith(".png") || fileName.toLowerCase().endsWith(".gif") ||fileName.toLowerCase().endsWith(".heic") ) {
            mediaType = MediaType.IMAGE_JPEG;
            ;
        }

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .contentType(mediaType)
                .body(resource);
    }

}
