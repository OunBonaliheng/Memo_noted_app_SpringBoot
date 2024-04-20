package com.example._memo_noted_takingapp.Model.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilesImgVideoResponse {
    private Integer fileId;
    private String receiveFiles;
    private UserResponse users;
}
