package com.example._memo_noted_takingapp.Controller;

import com.example._memo_noted_takingapp.Model.FilesImgVideo;
import com.example._memo_noted_takingapp.Model.NotePaper;
import com.example._memo_noted_takingapp.Model.dto.Request.FilesImgVideoRequest;
import com.example._memo_noted_takingapp.Model.dto.Response.APIResponse;
import com.example._memo_noted_takingapp.Model.dto.Response.FilesImgVideoResponse;
import com.example._memo_noted_takingapp.Service.ImgVideoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/memo/notes/imgVideo/")
@AllArgsConstructor
public class ImgVideoController {
    private final ImgVideoService imgVideoService;

    @GetMapping("")
    public ResponseEntity<APIResponse<List<FilesImgVideo>>> getImgVideo() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse<>(
                        "All Files are found",
                        imgVideoService.getAllFile(),
                        HttpStatus.OK, new Date()));
    }
    @PostMapping
    public ResponseEntity<APIResponse<FilesImgVideo>> createImgVideo(@RequestBody FilesImgVideoRequest filesImgVideo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse<>(
                "File is added successfully",imgVideoService.createImgVideo(filesImgVideo),HttpStatus.CREATED,new Date()
        ));
    }
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<FilesImgVideoResponse>> findImgVideoById(@PathVariable Integer id){
        FilesImgVideoResponse filesImgVideoResponse = imgVideoService.findImgVideoById(id);
        APIResponse<FilesImgVideoResponse> response= APIResponse.<FilesImgVideoResponse> builder()
                .message("The Files have been successfully founded")
                .payload(filesImgVideoResponse)
                .status(HttpStatus.OK)
                .creationDate(new Date())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}
