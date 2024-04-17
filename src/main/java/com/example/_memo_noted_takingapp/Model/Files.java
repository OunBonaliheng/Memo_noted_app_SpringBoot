package com.example._memo_noted_takingapp.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Files {
    private Integer fileId;
    private Integer notedId;
    private String fileName;
    private String fileType;
    private String fileUrl;
}
