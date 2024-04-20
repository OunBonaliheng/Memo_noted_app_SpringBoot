package com.example._memo_noted_takingapp.Service.serviceimpl;

import com.example._memo_noted_takingapp.Exception.NotFoundException;
import com.example._memo_noted_takingapp.Model.NotePaper;
import com.example._memo_noted_takingapp.Model.Tags;
import com.example._memo_noted_takingapp.Model.dto.Request.NotePaperRequest;
import com.example._memo_noted_takingapp.Model.dto.Response.TagResponse;
import com.example._memo_noted_takingapp.Repositority.ImgVideoRepo;
import com.example._memo_noted_takingapp.Repositority.NotePaperRepo;
import com.example._memo_noted_takingapp.Service.NotePaperService;
import com.example._memo_noted_takingapp.Service.TagsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class NotePaperimpl implements NotePaperService {
    private final NotePaperRepo notePaperRepo;
    private final UserServiceImpl userServiceImpl;
    private final TagsService tagsService;
    private final ImgVideoRepo imgVideoRepo;
    @Override
    public List<NotePaper> getAllNotes() {
        Long userId = userServiceImpl.getUsernameOfCurrentUser();
        return notePaperRepo.findAllNotes(userId);
    }

    @Override
    public NotePaper getNotesById(Integer id) {
        Long userId = userServiceImpl.getUsernameOfCurrentUser();
        NotePaper notePaper = notePaperRepo.findNotesById(id,userId);
        if (notePaper == null) {
            throw new NotFoundException("The notePaper with Id " + id + " doesn't exist.");
        }
        else {
            return notePaper;
        }
    }

    @Override
    public NotePaper saveNotes(NotePaperRequest notePaperRequest) {
        List<Integer> tagIds = notePaperRequest.getTagsLists();
        for (Integer tagId : tagIds) {
            tagsService.getTagsById(tagId);
        }
        notePaperRequest.setCreationDate(new Date());
        Long userId = userServiceImpl.getUsernameOfCurrentUser();
        NotePaper notePaper = notePaperRepo.createNote(notePaperRequest, userId);

        List<String> file = notePaperRequest.getReceiveFiles();
        for (String fileName : file) {
            imgVideoRepo.addtofile(notePaper.getNotedId(),fileName);
        }

        return notePaper;
    }


    @Override
    public NotePaper updateNote(Integer id, NotePaperRequest notePaperRequest) {
        List<Integer> tagIds = notePaperRequest.getTagsLists();
        for (Integer tagId : tagIds) {
            tagsService.getTagsById(tagId);
        }
        Long userId = userServiceImpl.getUsernameOfCurrentUser();
        NotePaper notePaper = notePaperRepo.updateNotes(id,notePaperRequest,userId);
       if (notePaper == null) {
           throw new NotFoundException("The notePaper with Id " + id + " not found for update");
       }
        return notePaper;
    }

    @Override
    public String deleteNote(Integer id) {
        Boolean isSuccess = notePaperRepo.deleteNote(id);
        if (!isSuccess) {
            throw new NotFoundException("Note with Id: " + id + " is not found");
        }
        return "Note with Id: " + id + " successfully deleted";
    }


}
