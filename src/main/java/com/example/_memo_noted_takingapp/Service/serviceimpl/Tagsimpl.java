package com.example._memo_noted_takingapp.Service.serviceimpl;

import com.example._memo_noted_takingapp.Exception.InvalidInputException;
import com.example._memo_noted_takingapp.Exception.NotFoundException;
import com.example._memo_noted_takingapp.Model.Tags;
import com.example._memo_noted_takingapp.Model.dto.Request.NotePaperRequest;
import com.example._memo_noted_takingapp.Model.dto.Request.TagsRequest;
import com.example._memo_noted_takingapp.Model.dto.Response.TagResponse;
import com.example._memo_noted_takingapp.Repositority.TagsRepo;
import com.example._memo_noted_takingapp.Service.TagsService;
import com.example._memo_noted_takingapp.config.EncryptionUtill;
import com.example._memo_noted_takingapp.config.SecretKeyGenerator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class Tagsimpl implements TagsService {
    private final TagsRepo tagsRepo;
    private final UserServiceImpl userServiceImple;
    private final ModelMapper modelMapper;
    private final EncryptionUtill encryptionUtill;
    private final SecretKeyGenerator secretKeyGenerator;
    private final String secretKey ="${encryption.secret.key}";


    @Override
    public List<Tags> getAllTags() {
        Long userId = userServiceImple.getUsernameOfCurrentUser();
        List<Tags> tags = tagsRepo.findallTags(userId);
        tags.forEach(tag -> {
            try {
                if (isBase64Encoded(tag.getTagName())) {
                    tag.setTagName(decryptField(tag.getTagName()));
                }
            } catch (Exception e) {
                throw new RuntimeException("Error while decrypting tag name", e);
            }
        });
        return tags;
    }

    @Override
    public TagResponse getTagsById(Integer id) {
        Long userId = userServiceImple.getUsernameOfCurrentUser();
        Tags tags = tagsRepo.findTagsById(id, userId);
        try {
            if (isBase64Encoded(tags.getTagName())) {
                tags.setTagName(decryptField(tags.getTagName()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting note content", e);
        }
        if (tags == null) {
            throw new NotFoundException("The Tags with Id " + id + " doesn't exist.");
        } else {
            return modelMapper.map(tags, TagResponse.class);
        }
    }


    private String decryptField(String encryptedField) throws Exception {
        if (encryptedField == null || encryptedField.isEmpty()) {
            return encryptedField;
        }
        if (isBase64Encoded(encryptedField)) {
            return encryptionUtill.decrypt(encryptedField, secretKey);
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
    public Tags addTags(TagsRequest tagsRequest) {
        long userId = userServiceImple.getUsernameOfCurrentUser();

        try {
            encryptTagName(tagsRequest);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting note content", e);
        }

        // Check if a tag with the same name already exists for the user
        Tags existingTag = tagsRepo.findTagsByTagName(tagsRequest.getTagName(), userId);
        if (existingTag != null) {
            throw new InvalidInputException("Tag with the same name already exists for this user.");
        }

        // If the tag doesn't exist, add it
        return tagsRepo.addTags(tagsRequest, userId);
    }
    private void encryptTagName(TagsRequest tagsRequest) throws Exception {
        String encryptedTagName = encryptionUtill.encrypt(tagsRequest.getTagName(), "${encryption.secret.key}");
        tagsRequest.setTagName(encryptedTagName);
    }

    @Override
    public Tags updateTag(Integer id, TagsRequest tagsRequest) {
        try {
            String encryptedTitle = encryptionUtill.encrypt(tagsRequest.getTagName(), "${encryption.secret.key}");
            tagsRequest.setTagName(encryptedTitle);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting note content", e);
        }
        long userId = userServiceImple.getUsernameOfCurrentUser();
        Tags tags = tagsRepo.updateTag(id,tagsRequest,userId);

        if (tags == null) {
            throw new NotFoundException("The Tags with Id " + id + " is not found for update.");
        }
        return tagsRepo.updateTag(id,tagsRequest,userId);
    }


    @Override
    public String deleteTag(Integer id) {
        Boolean isSuccess = tagsRepo.deleteTag(id,userServiceImple.getUsernameOfCurrentUser());
        if (!isSuccess) {
            throw new NotFoundException("Tag with Id: " + id + " is not found for delete.");
        }
        return "Tag with Id: " + id + " successfully deleted";
    }

    @Override
    public List<Tags> getTagsByname(String tagname) {
        long userId = userServiceImple.getUsernameOfCurrentUser();
        List<Tags> tags = tagsRepo.findallTags(userId);
        return tags.stream().filter(tag -> {
            try {
                String decryptedTitle = encryptionUtill.decrypt(tag.getTagName(), secretKey);
                // Check if the decrypted title contains the search input (case insensitive)
                return decryptedTitle.toLowerCase().contains(tagname.toLowerCase());
            } catch (Exception e) {
                throw new RuntimeException("Error while decrypting note content", e);
            }
        }).map(tag -> {
            try {
                String decryptedTitle = encryptionUtill.decrypt(tag.getTagName(), secretKey);
                tag.setTagName(decryptedTitle);
                return tag;
            } catch (Exception e) {
                throw new RuntimeException("Error while decrypting note content", e);
            }
        }).collect(Collectors.toList());
    }
}
