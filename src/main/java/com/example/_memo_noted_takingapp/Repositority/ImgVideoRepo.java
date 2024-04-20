package com.example._memo_noted_takingapp.Repositority;

import com.example._memo_noted_takingapp.Model.FilesImgVideo;
import com.example._memo_noted_takingapp.Model.dto.Request.FilesImgVideoRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ImgVideoRepo {

    @Results(id = "filesMapper", value = {
            @Result(property = "fileId", column = "file_id"),
            @Result(property = "receiveFiles", column = "receiveFiles"),

    })
    @Select("SELECT * FROM files WHERE user_id =#{userId} ORDER BY file_id")
    List<FilesImgVideo> findAllFiles(Long userId);

    @Select("INSERT INTO files (receiveFiles) " +
            "VALUES ( #{files.receiveFiles}) RETURNING *")
    @ResultMap("filesMapper")
    FilesImgVideo addImgVideo(@Param("files") FilesImgVideoRequest filesImgVideo);

    @Select("""
        SELECT * FROM files WHERE note_id =#{id} AND user_id =#{userId} ;
    """)
    @ResultMap("filesMapper")
    FilesImgVideo findImgVideoById(Integer id, long userId);


    @ResultMap("filesMapper")
    @Select("SELECT * FROM files WHERE noted_id = #{notedId}")
    List<FilesImgVideo> findImgVideoByNoteId(Integer notedId);


    @Select("""
    INSERT INTO files (noted_id,receiveFiles) VALUES (#{notedId},#{fileName}) RETURNING *
""")
    @ResultMap("filesMapper")
    FilesImgVideo addtofile(Integer notedId, String fileName);

}


