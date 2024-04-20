package com.example._memo_noted_takingapp.Repositority;

import com.example._memo_noted_takingapp.Model.Tags;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface Tags_noteRepo  {
    @Insert("INSERT INTO tag_note (noted_id, tag_id) VALUES (#{notedId}, #{tagId})")
    void insertTag(Integer notedId, Integer tagId);

    @Select("SELECT t.* FROM tags_tb t INNER JOIN tag_note tn ON tn.tag_id = t.tag_id WHERE tn.noted_id = #{id}")
    List<Tags> findTagsByNoteId(Integer id);



    @Delete("DELETE FROM tag_note WHERE noted_id = #{notedId}")
    void removeTag(Integer notedId);

}
