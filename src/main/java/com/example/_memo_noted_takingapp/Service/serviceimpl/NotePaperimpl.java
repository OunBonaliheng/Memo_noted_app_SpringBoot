package com.example._memo_noted_takingapp.Service.serviceimpl;

import com.example._memo_noted_takingapp.Model.NotePaper;
import com.example._memo_noted_takingapp.Model.dto.Request.NotePaperRequest;
import com.example._memo_noted_takingapp.Repositority.NotePaperRepo;
import com.example._memo_noted_takingapp.Service.NotePaperService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotePaperimpl implements NotePaperService {
    private final NotePaperRepo notePaperRepo;
    public NotePaperimpl(NotePaperRepo notePaperRepo) {
        this.notePaperRepo = notePaperRepo;
    }
    @Override
    public List<NotePaper> getAllNotes() {
        return notePaperRepo.findAllNotes();
    }

    @Override
    public NotePaper getNotesById(Integer id) {
        return notePaperRepo.findNotesById(id);
    }

    @Override
    public NotePaper saveNotes(NotePaperRequest notePaperRequest) {
        notePaperRequest.setCreationDate(new Date());
        return notePaperRepo.createNote(notePaperRequest);
    }


    @Override
    public NotePaper updateNote(Integer id, NotePaperRequest notePaperRequest) {
        return notePaperRepo.updateNotes(id,notePaperRequest);
    }

    @Override
    public String deleteNote(Integer id) {
        Boolean isSuccess = notePaperRepo.deleteNote(id);
        if (!isSuccess) {
            return "Note with Id: " + id + " is not found";
        }
        return "Note with Id: " + id + " successfully deleted";
    }


}
