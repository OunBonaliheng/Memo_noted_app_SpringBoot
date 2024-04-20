package com.example._memo_noted_takingapp.Model.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagResponse {
    private Integer tag_Id;
    private String tagName;
    private UserResponse users;
}
