package com.example._memo_noted_takingapp.Model.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotePaperRequest {
    private String title;
    private String note_content;
    private String note_description;
    private Date creationDate;
    private List<Integer> tagsLists;
    private String selectColor;
    private List<String> receiveFiles;
}
