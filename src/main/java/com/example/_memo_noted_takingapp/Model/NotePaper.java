package com.example._memo_noted_takingapp.Model;

import com.example._memo_noted_takingapp.Model.dto.Response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NotePaper {
    private Integer notedId;
    private String title;
    private String note_content;
    private String note_description;
    private Date creationDate = new Date();
    private String selectColor;
    private List<Tags> tagsLists;
    private List<FilesImgVideo> receiveFiles;
    private UserResponse users;

    public String getCreationDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(creationDate);
    }


}
