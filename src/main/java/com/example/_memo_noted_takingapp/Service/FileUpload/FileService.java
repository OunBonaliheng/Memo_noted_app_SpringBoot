package com.example._memo_noted_takingapp.Service.FileUpload;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {
    List<String> saveFile(List<MultipartFile> file) throws IOException;
    Resource getFileByFileName(String fileName) throws IOException;


}
