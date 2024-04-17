package com.example._memo_noted_takingapp.Controller;
import com.example._memo_noted_takingapp.Model.Tags;
import com.example._memo_noted_takingapp.Model.dto.Request.TagsRequest;
import com.example._memo_noted_takingapp.Model.dto.Response.APIResponse;
import com.example._memo_noted_takingapp.Repositority.TagsRepo;
import com.example._memo_noted_takingapp.Service.TagsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/api/memo/tags/")
@SecurityRequirement(name = "bearerAuth")
public class TagController {
    private final TagsService tagsService;
    private final TagsRepo tagsRepo;
    public TagController(TagsService tagsService, TagsRepo tagsRepo) {
        this.tagsService = tagsService;
        this.tagsRepo = tagsRepo;
    }

    @PostMapping
    public ResponseEntity<APIResponse<Tags>> addTags(@RequestBody TagsRequest tagsRequest) {
        Tags tags = tagsService.addTags(tagsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new APIResponse<>("Tag is create successful",
                        tags,
                        HttpStatus.OK, new Date())
        );
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<Tags>>> getAllTags() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse<>(
                        "Find all tags successful",
                        tagsService.getAllTags(),
                        HttpStatus.OK, new Date()));
    }
    @GetMapping("{id}")
    public ResponseEntity<APIResponse<Tags>> getTagById(@PathVariable Integer id) {
        Tags tags = tagsService.getTagsById(id);
        System.out.println(tags);
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse<>(
                        "Tag with Id: "+id+ " is found successful",
                        tags,
                        HttpStatus.OK, new Date()
        ));
    }
    @PutMapping("{id}")
    public ResponseEntity<APIResponse<Tags>> updateTag(@PathVariable Integer id, @RequestBody TagsRequest tagsRequest) {
        Tags tags = tagsService.updateTag(id, tagsRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse<>(
                        "Tags with Id: "+id+ " is updated successful",
                        tags,
                        HttpStatus.OK, new Date())

        );
    }

    @GetMapping("tagName/{tagname}")
    public ResponseEntity<APIResponse<List<Tags>>> getTagByTagName(@PathVariable String tagname) {
        List<Tags> tags =  tagsRepo.findTagsname(tagname);
        System.out.println(tags);
        if (tags.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new APIResponse<>("No tags found with the tagname: " + tagname, null, HttpStatus.NOT_FOUND,  new Date())
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new APIResponse<>("Search tag successful", tags, HttpStatus.OK,  new Date())
            );
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTag(@PathVariable Integer id) {
        String message =  tagsService.deleteTag(id);
        System.out.println(message);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
