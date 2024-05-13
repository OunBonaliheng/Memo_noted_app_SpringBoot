package com.example._memo_noted_takingapp.Service.serviceimpl;

import com.example._memo_noted_takingapp.Exception.NotFoundException;
import com.example._memo_noted_takingapp.Model.FilesImgVideo;
import com.example._memo_noted_takingapp.Model.dto.Request.FilesImgVideoRequest;
import com.example._memo_noted_takingapp.Model.dto.Response.FilesImgVideoResponse;
import com.example._memo_noted_takingapp.Repositority.ImgVideoRepo;
import com.example._memo_noted_takingapp.Service.ImgVideoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ImgVideoServiceImpl implements ImgVideoService {
    private final ImgVideoRepo imgVideoRepo;
    private final UserServiceImpl userServiceImpl;
    private final ModelMapper modelMapper;
    @Override
    public List<FilesImgVideo> getAllFile() {
        Long userId = userServiceImpl.getUsernameOfCurrentUser();
        return imgVideoRepo.findAllFiles(userId);
    }

    @Override
    public FilesImgVideo createImgVideo(FilesImgVideoRequest filesImgVideo) {
        return imgVideoRepo.addImgVideo(filesImgVideo);
    }

    @Override
    public FilesImgVideoResponse findImgVideoById(Integer id) {
        long userId = userServiceImpl.getUsernameOfCurrentUser();
        FilesImgVideo filesImgVideo = imgVideoRepo.findImgVideoById(id,userId);

        if (filesImgVideo == null) {
            throw new NotFoundException("The filesImgVideo with id " + id + " doesn't exist.");
        }
        else {
            return modelMapper.map(filesImgVideo, FilesImgVideoResponse.class);
        }
    }
}
