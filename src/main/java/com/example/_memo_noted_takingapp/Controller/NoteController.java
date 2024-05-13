package com.example._memo_noted_takingapp.Controller;
import com.example._memo_noted_takingapp.Exception.NotFoundException;
import com.example._memo_noted_takingapp.Model.NotePaper;
import com.example._memo_noted_takingapp.Model.Tags;
import com.example._memo_noted_takingapp.Model.dto.Request.NotePaperRequest;
import com.example._memo_noted_takingapp.Model.dto.Response.APIResponse;
import com.example._memo_noted_takingapp.Repositority.NotePaperRepo;
import com.example._memo_noted_takingapp.Repositority.Tags_noteRepo;
import com.example._memo_noted_takingapp.Service.NotePaperService;

import com.example._memo_noted_takingapp.Service.UserService;
import com.example._memo_noted_takingapp.Service.serviceimpl.UserServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@SecurityRequirement(name = "bearerAuth")
public class NoteController {
    private final NotePaperService notePaperService;
    private final Tags_noteRepo tags_noteRepo;
    private final UserServiceImpl userService;
    Date currentDate = new Date();
    private final NotePaperRepo notePaperRepo;
    public NoteController(NotePaperService notePaperService, Tags_noteRepo tagsNoteRepo, UserServiceImpl userService, NotePaperRepo notePaperRepo) {
        this.notePaperService = notePaperService;
        tags_noteRepo = tagsNoteRepo;
        this.userService = userService;
        this.notePaperRepo = notePaperRepo;
    }
    @GetMapping("/api/memo/notes/")
    public ResponseEntity<APIResponse<List<NotePaper>>> getAllNotes() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse<>(
                        "Find all notes successful",notePaperService.getAllNotes(),HttpStatus.OK, currentDate
                )
        );
    }
    @GetMapping("/api/memo/notes/{id}")
    public ResponseEntity<NotePaper> getNoteById(@PathVariable @Valid @Positive(message = "must be greater than 0") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                notePaperService.getNotesById(id));
    }
    @GetMapping("/api/memo/notes/title/{title}")
    public ResponseEntity<List<NotePaper>> getNoteByTitle(@PathVariable String title) {
        Long userId = userService.getUsernameOfCurrentUser();
        List<NotePaper> foundNotes = notePaperRepo.searchTitleIgnoreCase(title,userId);

        System.out.println(foundNotes);
        if (foundNotes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(foundNotes);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    foundNotes
            );
        }
    }


    @PostMapping("/api/memo/notes/")
    public ResponseEntity<NotePaper> addNote(@RequestBody @Valid NotePaperRequest notePaperRequest) {
        NotePaper notePaper = notePaperService.saveNotes(notePaperRequest);
        notePaperRequest.setCreationDate(currentDate);
        System.out.println(notePaperRequest);
        for (Integer Id : notePaperRequest.getTagsLists()) {
            tags_noteRepo.insertTag(notePaper.getNotedId(),Id);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                     notePaperService.getNotesById(notePaper.getNotedId())
        );
    }

    @PutMapping("/api/memo/notes/{id}")
    public ResponseEntity<NotePaper> updateNote(@PathVariable @Valid @Positive(message = "must be greater than 0") Integer id, @RequestBody NotePaperRequest notePaperRequest) {


        NotePaper notePaper = notePaperService.updateNote(id, notePaperRequest);
        notePaperRequest.setCreationDate(currentDate);
        tags_noteRepo.removeTag(notePaper.getNotedId());
        for (Integer Id : notePaperRequest.getTagsLists()) {
            tags_noteRepo.insertTag(notePaper.getNotedId(),Id);
        }

        System.out.println(notePaperRequest);
        if (!notePaperRequest.getTagsLists().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    notePaperService.getNotesById(notePaper.getNotedId())
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(notePaper);
        }

    }

    @DeleteMapping("/api/memo/notes/{id}")
    public ResponseEntity<String> removeNote(@PathVariable @Valid @Positive(message = "must be greater than 0") Integer id) {
       String message =  notePaperService.deleteNote(id);
        System.out.println(message);
       return ResponseEntity.status(HttpStatus.OK).body("successfully Note deleted");
    }
}
