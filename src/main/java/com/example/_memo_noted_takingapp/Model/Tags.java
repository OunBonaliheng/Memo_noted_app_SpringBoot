package com.example._memo_noted_takingapp.Model;

import com.example._memo_noted_takingapp.Model.dto.Response.UserResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class Tags {
    private Integer tag_Id;
    private String tagName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserResponse users;
}
