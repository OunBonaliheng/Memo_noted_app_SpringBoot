package com.example._memo_noted_takingapp.Service;

import com.example._memo_noted_takingapp.Model.NotePaper;
import com.example._memo_noted_takingapp.Model.dto.Request.NotePaperRequest;

import java.util.List;

public interface NotePaperService {
    List<NotePaper> getAllNotes();

    NotePaper getNotesById(Integer id);

    NotePaper saveNotes(NotePaperRequest notePaperRequest);

    NotePaper updateNote(Integer id, NotePaperRequest notePaperRequest);

    String deleteNote(Integer id);

}
