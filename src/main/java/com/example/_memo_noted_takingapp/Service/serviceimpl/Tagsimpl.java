package com.example._memo_noted_takingapp.Service.serviceimpl;

import com.example._memo_noted_takingapp.Model.Tags;
import com.example._memo_noted_takingapp.Model.dto.Request.TagsRequest;
import com.example._memo_noted_takingapp.Repositority.TagsRepo;
import com.example._memo_noted_takingapp.Service.TagsService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class Tagsimpl implements TagsService {
    private final TagsRepo tagsRepo;
    public Tagsimpl(TagsRepo tagsRepo) {
        this.tagsRepo = tagsRepo;
    }

    @Override
    public List<Tags> getAllTags() {
        return tagsRepo.findallTags();
    }

    @Override
    public Tags getTagsById(Integer id) {
        return tagsRepo.findTagsById(id);
    }

    @Override
    public Tags addTags(TagsRequest tagsRequest) {
        return  tagsRepo.addTags(tagsRequest);
    }

    @Override
    public Tags updateTag(Integer id, TagsRequest tagsRequest) {
        return tagsRepo.updateTag(id,tagsRequest);
    }


    @Override
    public String deleteTag(Integer id) {
        Boolean isSuccess = tagsRepo.deleteTag(id);
        if (!isSuccess) {
            return "Tag with Id: " + id + " is not found";
        }
        return "Tag with Id: " + id + " successfully deleted";
    }

    @Override
    public List<Tags> getTagsByname(String tagname) {
        return tagsRepo.findTagsname(tagname);
    }
}
