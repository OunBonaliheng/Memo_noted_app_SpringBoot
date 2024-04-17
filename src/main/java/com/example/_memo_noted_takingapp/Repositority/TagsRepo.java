package com.example._memo_noted_takingapp.Repositority;

import com.example._memo_noted_takingapp.Model.Tags;
import com.example._memo_noted_takingapp.Model.dto.Request.TagsRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TagsRepo {
    //Get All tags
    @Select("""
        SELECT * FROM tags_tb ORDER BY tag_id
    """)
    @Results(id = "TagMapper", value = {
            @Result(property = "tag_Id", column = "tag_id"),
            @Result(property = "tagName", column = "tag_name"),
            @Result(property = "user", column = "tag_id",one=@One(select = "com.example._memo_noted_takingapp.Repositority.UserRepository.getUserByEmail"))

    })
    List<Tags> findallTags();

    @Select("""
    SELECT note_table.*, tag_note_table.*, tags_table.*\s
    FROM note_tb AS note_table
    JOIN tag_note AS tag_note_table ON note_table.noted_id = tag_note_table.noted_id
    JOIN tags_tb AS tags_table ON tags_table.tag_id = tag_note_table.tag_id
    WHERE tag_note_table.noted_id = #{note_id}
    
""")
    @Result(property = "tagName",column = "tag_name")
    List<Tags> findNoteWithTags(Integer note_id);


    //Find tag by id
    @Select("""
        SELECT * FROM tags_tb WHERE tag_id = #{id}
    
    """)
    @ResultMap("TagMapper")
    Tags findTagsById(@Param("id") Integer id);

    //add tag
    @Select("""
        INSERT INTO tags_tb (tag_name)
        VALUES (#{tag.tagName}) RETURNING *
    """)
    @ResultMap("TagMapper")
    Tags addTags(@Param("tag") TagsRequest tagsRequest);


    //update tag By id
    @Select("""
        UPDATE tags_tb SET tag_name = #{update.tagName}
        WHERE tag_id = #{id}  RETURNING *;
    """)
    @ResultMap("TagMapper")
    Tags updateTag(@Param("id") Integer id, @Param("update") TagsRequest tagsRequest);

    //Delete tag by Id
    @Delete("""
        DELETE FROM tags_tb WHERE tag_id = #{id};
    """)
    @ResultMap("TagMapper")
    Boolean deleteTag(Integer id);

    //search filter tag
    @Select("SELECT * FROM tags_tb WHERE LOWER(tag_name) LIKE CONCAT('%', LOWER(#{tagname}), '%')")
    @ResultMap("TagMapper")
    List<Tags> findTagsname(String tagname);
}
