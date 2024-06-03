package com.example._memo_noted_takingapp.Service.serviceimpl;

import com.example._memo_noted_takingapp.Exception.NotFoundException;
import com.example._memo_noted_takingapp.Model.NotePaper;
import com.example._memo_noted_takingapp.Model.Tags;
import com.example._memo_noted_takingapp.Model.dto.Request.NotePaperRequest;
import com.example._memo_noted_takingapp.Model.dto.Response.TagResponse;
import com.example._memo_noted_takingapp.Repositority.ImgVideoRepo;
import com.example._memo_noted_takingapp.Repositority.NotePaperRepo;
import com.example._memo_noted_takingapp.Repositority.Tags_noteRepo;
import com.example._memo_noted_takingapp.Service.NotePaperService;
import com.example._memo_noted_takingapp.Service.TagsService;
import com.example._memo_noted_takingapp.config.EncryptionUtill;
import com.example._memo_noted_takingapp.config.SecretKeyGenerator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotePaperimpl implements NotePaperService {
    private final NotePaperRepo notePaperRepo;
    private final UserServiceImpl userServiceImpl;
    private final TagsService tagsService;
    private final ImgVideoRepo imgVideoRepo;
    private final EncryptionUtill encryptionUtill;
    private final ModelMapper modelMapper;
    private final SecretKeyGenerator secretKeyGenerator;
    private final Tags_noteRepo tags_noteRepo;
    private final String secretKey ="${encryption.secret.key}";


    @Override
    public List<NotePaper> getAllNotes() {
        Long userId = userServiceImpl.getUsernameOfCurrentUser();
        List<NotePaper> notes = notePaperRepo.findAllNotes(userId);
        notes.forEach(note -> decryptNoteContent(note));
        return notes;
    }

    private void logEncryptedValues(NotePaper note) {
        System.out.println("Encrypted Title: " + note.getTitle());
        System.out.println("Encrypted Content: " + note.getNote_content());
        System.out.println("Encrypted Description: " + note.getNote_description());
        System.out.println("Encrypted SelectColor: " + note.getSelectColor());
    }
    @Override
    public NotePaper getNotesById(Integer id) {
        Long userId = userServiceImpl.getUsernameOfCurrentUser();
        NotePaper notePaper = notePaperRepo.findNotesById(id, userId);

        if (notePaper == null) {
            throw new NotFoundException("The notePaper with Id " + id + " doesn't exist.");
        }
        try {
            notePaper.setTitle(decryptField(notePaper.getTitle()));
            notePaper.setNote_content(decryptField(notePaper.getNote_content()));
            notePaper.setNote_description(decryptField(notePaper.getNote_description()));
            notePaper.setSelectColor(decryptField(notePaper.getSelectColor()));
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting note content", e);
        }

        return notePaper;
    }

    private String decryptField(String encryptedField) throws Exception {
        if (encryptedField == null || encryptedField.isEmpty()) {
            return encryptedField; // return as is if null or empty
        }
        // Check if the field is Base64 encoded to determine if it is encrypted
        if (isBase64Encoded(encryptedField)) {
            return encryptionUtill.decrypt(encryptedField, "${encryption.secret.key}");
        }
        return encryptedField;
    }

    private boolean isBase64Encoded(String field) {
        try {
            Base64.getDecoder().decode(field);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public NotePaper saveNotes(NotePaperRequest notePaperRequest) {
        List<Integer> tagIds = notePaperRequest.getTagsLists();
        List<Tags> tags = new ArrayList<>();
        for (Integer tagId : tagIds) {
            TagResponse tagResponse = tagsService.getTagsById(tagId);
            Tags tag = modelMapper.map(tagResponse, Tags.class);
            tags.add(tag);
        }

        try {
            encryptNoteTitle(notePaperRequest);
            encryptNoteContent(notePaperRequest);
            encryptNoteDescription(notePaperRequest);
            encryptNoteSelectColor(notePaperRequest);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting note content", e);
        }

        notePaperRequest.setCreationDate(new Date());
        Long userId = userServiceImpl.getUsernameOfCurrentUser();
        NotePaper notePaper = notePaperRepo.createNote(notePaperRequest, userId);

        List<String> file = notePaperRequest.getReceiveFiles();
        for (String fileName : file) {
            imgVideoRepo.addtofile(notePaper.getNotedId(), fileName);
        }
        // Fetch and decrypt tags
        List<Tags> encryptedTags = tags_noteRepo.findTagsByNoteId(notePaper.getNotedId());
        encryptedTags.forEach(tag -> {
            try {
                tag.setTagName(encryptionUtill.decrypt(tag.getTagName(), "your-secret-key"));
            } catch (Exception e) {
                throw new RuntimeException("Error while decrypting tag name", e);
            }
        });

        notePaper.setTagsLists(encryptedTags);
        return notePaper;
    }






    private void encryptNoteTitle(NotePaperRequest notePaperRequest) throws Exception {
        String encryptedTitle = encryptionUtill.encrypt(notePaperRequest.getTitle(), "${encryption.secret.key}");
        notePaperRequest.setTitle(encryptedTitle);
    }
    private void encryptNoteContent(NotePaperRequest notePaperRequest) throws Exception {
        String encryptedContent = encryptionUtill.encrypt(notePaperRequest.getNote_content(), "${encryption.secret.key}");
        notePaperRequest.setNote_content(encryptedContent);
    }

    private void encryptNoteDescription(NotePaperRequest notePaperRequest) throws Exception {
        String encryptedDescription = encryptionUtill.encrypt(notePaperRequest.getNote_description(), "${encryption.secret.key}");
        notePaperRequest.setNote_description(encryptedDescription);
    }

    private void encryptNoteSelectColor(NotePaperRequest notePaperRequest) throws Exception {
        String encryptedSelectColor = encryptionUtill.encrypt(notePaperRequest.getSelectColor(), "${encryption.secret.key}");
        notePaperRequest.setSelectColor(encryptedSelectColor);
    }


    @Override
    public NotePaper updateNote(Integer id, NotePaperRequest notePaperRequest) {
        List<Integer> tagIds = notePaperRequest.getTagsLists();
        if (tagIds != null && !tagIds.isEmpty()) {
            for (Integer tagId : tagIds) {
                tagsService.getTagsById(tagId);
            }
        }
        try {
            // Encrypt the fields before updating
            String encryptedTitle = encryptionUtill.encrypt(notePaperRequest.getTitle(), "${encryption.secret.key}");
            String encryptedContent = encryptionUtill.encrypt(notePaperRequest.getNote_content(), "${encryption.secret.key}");
            String encryptedDescription = encryptionUtill.encrypt(notePaperRequest.getNote_description(), "${encryption.secret.key}");
            String encryptedSelectColor = encryptionUtill.encrypt(notePaperRequest.getSelectColor(), "${encryption.secret.key}");
            // Set the encrypted fields back to the request object
            notePaperRequest.setTitle(encryptedTitle);
            notePaperRequest.setNote_content(encryptedContent);
            notePaperRequest.setNote_description(encryptedDescription);
            notePaperRequest.setSelectColor(encryptedSelectColor);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting note content", e);
        }

        Long userId = userServiceImpl.getUsernameOfCurrentUser();
        NotePaper notePaper = notePaperRepo.updateNotes(id, notePaperRequest, userId);
        if (notePaper == null) {
            throw new NotFoundException("The notePaper with Id " + id + " not found for update");
        }
        return notePaper;
    }


    @Override
    public String deleteNote(Integer id) {
        Long userId = userServiceImpl.getUsernameOfCurrentUser();
        Boolean isSuccess = notePaperRepo.deleteNote(id, userId);
        if (!isSuccess) {
            throw new NotFoundException("Note with Id: " + id + " is not found");
        }
        return "Note with Id: " + id + " successfully deleted";
    }

    @Override
    public List<NotePaper> searchTitleIgnoreCase(String title) {
        Long userId = userServiceImpl.getUsernameOfCurrentUser();
        List<NotePaper> notes = notePaperRepo.findAllNotes(userId);
        return notes.stream().filter(note -> {
            try {
                String decryptedTitle = encryptionUtill.decrypt(note.getTitle(), secretKey);
                return decryptedTitle.toLowerCase().contains(title.toLowerCase());
            } catch (Exception e) {
                throw new RuntimeException("Error while decrypting note content", e);
            }
        }).map(note -> {
            decryptNoteContent(note);
            return note;
        }).collect(Collectors.toList());
    }


    private void decryptNoteContent(NotePaper note) {
        try {
            note.setTitle(encryptionUtill.decrypt(note.getTitle(), secretKey));
            note.setNote_content(encryptionUtill.decrypt(note.getNote_content(), secretKey));
            note.setNote_description(encryptionUtill.decrypt(note.getNote_description(), secretKey));
            note.setSelectColor(encryptionUtill.decrypt(note.getSelectColor(), secretKey));

            List<Tags> decryptedTags = note.getTagsLists().stream().map(tag -> {
                try {
                    tag.setTagName(encryptionUtill.decrypt(tag.getTagName(), secretKey));
                    return tag;
                } catch (Exception e) {
                    throw new RuntimeException("Error while decrypting tag name", e);
                }
            }).collect(Collectors.toList());
            note.setTagsLists(decryptedTags);
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting note content", e);
        }
    }




}
