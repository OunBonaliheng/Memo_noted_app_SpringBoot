package com.example._memo_noted_takingapp.Repositority;

import com.example._memo_noted_takingapp.Model.NotePaper;
import com.example._memo_noted_takingapp.Model.dto.Request.NotePaperRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotePaperRepo {
     // Get all notes
     @Select("""
        SELECT * FROM note_tb WHERE user_id =#{userId} ORDER BY noted_id
    """)
     @Results(id = "NoteMapper", value = {
             @Result(property = "notedId", column = "noted_id"),
             @Result(property = "title", column = "title"),
             @Result(property = "creationDate", column = "creation_date"),
             @Result(property = "selectColor", column = "select_color"),
             @Result(property = "note_content", column = "note_content"),
             @Result(property = "note_description", column = "note_description"),
             @Result(property = "receiveFiles", column = "noted_id", many = @Many(select = "com.example._memo_noted_takingapp.Repositority.ImgVideoRepo.findImgVideoByNoteId")),
             @Result(property = "users", column = "user_id", one = @One(select = "com.example._memo_noted_takingapp.Repositority.UserRepository.getUserById")),
             @Result(property = "tagsLists", column = "noted_id", many = @Many(select = "com.example._memo_noted_takingapp.Repositority.Tags_noteRepo.findTagsByNoteId")),
     })
     List<NotePaper> findAllNotes(Long userId);

     // Get note by id
     @Select("""
        SELECT * FROM note_tb WHERE noted_id = #{id} AND user_id =#{userId} ORDER BY noted_id
    """)
     @ResultMap("NoteMapper")
     NotePaper findNotesById(Integer id, Long userId);

     // Insert note
     @Select("""
        INSERT INTO note_tb (title,note_content,note_description,creation_date, select_color,user_id)
        VALUES (#{Note.title},#{Note.note_content},#{Note.note_description},#{Note.creationDate}, #{Note.selectColor},#{userId})
        RETURNING *;
    """)
     @ResultMap("NoteMapper")
     NotePaper createNote(@Param("Note") NotePaperRequest notePaperRequest, Long userId);

     // Update note by id
     @Select("""
        UPDATE note_tb SET title = #{note.title},note_content =#{note.note_content},note_description = #{note.note_description} ,creation_date = #{note.creationDate}, select_color = #{note.selectColor},  filesimgvideo = #{note.filesimgvideo},user_id=#{userId}
        WHERE noted_id = #{id} RETURNING *;
    """)
     @ResultMap("NoteMapper")
     //@Param("id")
     NotePaper updateNotes(Integer id, @Param("note") NotePaperRequest notePaperRequest, Long userId);

     // Delete note by id
     @Delete("""
        DELETE FROM note_tb WHERE noted_id = #{id} AND user_id =#{userId}
    """)
     @ResultMap("NoteMapper")
     Boolean deleteNote(Integer id,Long userId);

     @Select("SELECT * FROM note_tb WHERE LOWER(title) LIKE CONCAT('%', LOWER(#{title}), '%') AND user_id =#{userId}")
     @ResultMap("NoteMapper")
     List<NotePaper> searchTitleIgnoreCase(String title,Long userId);
}
