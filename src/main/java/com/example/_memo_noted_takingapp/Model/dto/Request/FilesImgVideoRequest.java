package com.example._memo_noted_takingapp.Model.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilesImgVideoRequest {
    @NotNull
    @NotBlank
    private String receiveFiles;
}
