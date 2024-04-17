package com.example._memo_noted_takingapp.Repositority;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface Tags_noteRepo  {
    @Insert("INSERT INTO tag_note (noted_id, tag_id) VALUES (#{notedId}, #{tagId})")
    void insertTag(Integer notedId, Integer tagId);
    @Delete("DELETE FROM tag_note WHERE noted_id = #{notedId}")
    void removeTag(Integer notedId);

}
