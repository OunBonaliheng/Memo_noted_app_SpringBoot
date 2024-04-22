package com.example._memo_noted_takingapp.Service.serviceimpl;

import com.example._memo_noted_takingapp.Exception.NotFoundException;
import com.example._memo_noted_takingapp.Model.Tags;
import com.example._memo_noted_takingapp.Model.dto.Request.TagsRequest;
import com.example._memo_noted_takingapp.Model.dto.Response.TagResponse;
import com.example._memo_noted_takingapp.Repositority.TagsRepo;
import com.example._memo_noted_takingapp.Service.TagsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class Tagsimpl implements TagsService {
    private final TagsRepo tagsRepo;
    private final UserServiceImpl userServiceImple;
    private final ModelMapper modelMapper;
    @Override
    public List<Tags> getAllTags() {
        Long userId = userServiceImple.getUsernameOfCurrentUser();
        return tagsRepo.findallTags(userId);
    }

    @Override
    public TagResponse getTagsById(Integer id) {
        Long userId = userServiceImple.getUsernameOfCurrentUser();
        Tags tags = tagsRepo.findTagsById(id,userId);

        if (tags == null) {
            throw new NotFoundException("The Tags with Id " + id + " doesn't exist.");
        }
        else {
            return modelMapper.map(tags, TagResponse.class);
        }
    }

    @Override
    public Tags addTags(TagsRequest tagsRequest) {
        long userId = userServiceImple.getUsernameOfCurrentUser();
        return  tagsRepo.addTags(tagsRequest,userId);
    }

    @Override
    public Tags updateTag(Integer id, TagsRequest tagsRequest) {
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
        return tagsRepo.findTagsname(tagname,userId);
    }
}
