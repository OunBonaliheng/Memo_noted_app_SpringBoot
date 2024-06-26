package com.example._memo_noted_takingapp.Service;

import com.example._memo_noted_takingapp.Model.Tags;
import com.example._memo_noted_takingapp.Model.dto.Request.TagsRequest;
import com.example._memo_noted_takingapp.Model.dto.Response.TagResponse;

import java.util.List;

public interface TagsService {
    

    List<Tags> getAllTags();

    TagResponse getTagsById(Integer id);

    Tags addTags(TagsRequest tagsRequest);

    Tags updateTag(Integer id, TagsRequest tagsRequest);

    String deleteTag(Integer id);

    List<Tags> getTagsByname(String tagname);

    TagResponse updateTagByTagName(Integer id, TagsRequest tagName);
}
