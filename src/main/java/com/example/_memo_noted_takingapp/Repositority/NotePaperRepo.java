package com.example._memo_noted_takingapp.Repositority;

import com.example._memo_noted_takingapp.Model.NotePaper;
import com.example._memo_noted_takingapp.Model.dto.Request.NotePaperRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotePaperRepo {
     // Get all notes
     @Select("""
        SELECT * FROM note_tb ORDER BY noted_id
    """)
     @Results(id = "NoteMapper", value = {
             @Result(property = "notedId", column = "noted_id"),
             @Result(property = "title", column = "title"),
             @Result(property = "tagName",column = "tag_name"),
             @Result(property = "creationDate", column = "creation_date"),
             @Result(property = "selectColor", column = "select_color"),
             @Result(property = "note_content", column = "note_content"),
             @Result(property = "note_description", column = "note_description"),
             @Result(property = "receiveImg", column = "receive_img"),
             @Result(property = "receiveVideo", column = "receive_video"),
             @Result(property = "tagsLists", column = "noted_id",many = @Many(select = "com.example._memo_noted_takingapp.Repositority.TagsRepo.findNoteWithTags")),
     })
     List<NotePaper> findAllNotes();

     // Get note by id
     @Select("""
        SELECT * FROM note_tb WHERE noted_id = #{id}
    """)
     @ResultMap("NoteMapper")
     NotePaper findNotesById(Integer id);

     // Insert note
     @Select("""
        INSERT INTO note_tb (title,note_content,note_description,creation_date, select_color, receive_img, receive_video)
        VALUES (#{Note.title},#{Note.note_content},#{Note.note_description},#{Note.creationDate}, #{Note.selectColor}, #{Note.receiveImg}, #{Note.receiveVideo})
        RETURNING *;
    """)
     @ResultMap("NoteMapper")
     NotePaper createNote(@Param("Note") NotePaperRequest notePaperRequest);

     // Update note by id
     @Select("""
        UPDATE note_tb SET title = #{note.title},note_content =#{note.note_content},note_description = #{note.note_description} ,creation_date = #{note.creationDate}, select_color = #{note.selectColor}, receive_img = #{note.receiveImg}, receive_video = #{note.receiveVideo}
        WHERE noted_id = #{id} RETURNING *;
    """)
     @ResultMap("NoteMapper")
     NotePaper updateNotes(@Param("id") Integer id, @Param("note") NotePaperRequest notePaperRequest);

     // Delete note by id
     @Delete("""
        DELETE FROM note_tb WHERE noted_id = #{id}
    """)
     @ResultMap("NoteMapper")
     Boolean deleteNote(Integer id);

     @Select("SELECT * FROM note_tb WHERE LOWER(title) LIKE CONCAT('%', LOWER(#{title}), '%')")
     @ResultMap("NoteMapper")
     List<NotePaper> searchTitleIgnoreCase(String title);
}
