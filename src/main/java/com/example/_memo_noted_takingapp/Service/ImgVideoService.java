package com.example._memo_noted_takingapp.Service;

import com.example._memo_noted_takingapp.Model.FilesImgVideo;
import com.example._memo_noted_takingapp.Model.dto.Request.FilesImgVideoRequest;
import com.example._memo_noted_takingapp.Model.dto.Response.FilesImgVideoResponse;


import java.util.List;


public interface ImgVideoService {
    List<FilesImgVideo> getAllFile();

    FilesImgVideo createImgVideo(FilesImgVideoRequest filesImgVideo);

    FilesImgVideoResponse findImgVideoById(Integer id);
}
