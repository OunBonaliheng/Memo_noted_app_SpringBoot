package com.example._memo_noted_takingapp.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppFile {
    private String fileName;
    private String fileType;
    private Long fileSize;
}
