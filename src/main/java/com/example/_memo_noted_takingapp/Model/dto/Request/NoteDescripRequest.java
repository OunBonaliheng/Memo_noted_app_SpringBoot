package com.example._memo_noted_takingapp.Model.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDescripRequest {
    private String content;
    private Integer NotedId;
    private Integer typeId;
}
