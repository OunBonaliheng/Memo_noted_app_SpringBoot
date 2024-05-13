package com.example._memo_noted_takingapp.Controller;
import com.example._memo_noted_takingapp.Exception.NotFoundException;
import com.example._memo_noted_takingapp.Model.Tags;
import com.example._memo_noted_takingapp.Model.dto.Request.TagsRequest;
import com.example._memo_noted_takingapp.Model.dto.Response.APIResponse;
import com.example._memo_noted_takingapp.Model.dto.Response.TagResponse;
import com.example._memo_noted_takingapp.Repositority.TagsRepo;
import com.example._memo_noted_takingapp.Service.TagsService;
import com.example._memo_noted_takingapp.Service.UserService;
import com.example._memo_noted_takingapp.Service.serviceimpl.UserServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
@RestController

@CrossOrigin(origins = "http://localhost:8080")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class TagController {
    private final TagsService tagsService;
    private final TagsRepo tagsRepo;
   private final UserServiceImpl userService;

    @PostMapping("/api/memo/tags/")
    public ResponseEntity<APIResponse<Tags>> addTags(@RequestBody TagsRequest tagsRequest) {
        Tags tags = tagsService.addTags(tagsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new APIResponse<>("Tag is create successful",
                        tags,
                        HttpStatus.OK, new Date())
        );
    }

    @GetMapping("/api/memo/tags/")
    public ResponseEntity<APIResponse<List<Tags>>> getAllTags() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse<>(
                        "Find all tags successful",
                        tagsService.getAllTags(),
                        HttpStatus.OK, new Date()));
    }
    @GetMapping("/api/memo/tags/{id}")
    public ResponseEntity<APIResponse<TagResponse>> getTagById(@PathVariable Integer id) {
        TagResponse tags = tagsService.getTagsById(id);
        System.out.println(tags);
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse<>(
                        "Tag with Id: "+id+ " is found successful",
                        tags,
                        HttpStatus.OK, new Date()
        ));
    }
    @PutMapping("/api/memo/tags/{id}")
    public ResponseEntity<APIResponse<Tags>> updateTag(@PathVariable Integer id, @RequestBody TagsRequest tagsRequest) {
        Tags tags = tagsService.updateTag(id, tagsRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse<>(
                        "Tags with Id: "+id+ " is updated successful",
                        tags,
                        HttpStatus.OK, new Date())

        );
    }

    @GetMapping("/api/memo/tags/tagName/{tagname}")
    public ResponseEntity<APIResponse<List<Tags>>> getTagByTagName(@PathVariable String tagname) {
        Long userId = userService.getUsernameOfCurrentUser();
        List<Tags> tags =  tagsRepo.findTagsname(tagname,userId);
        System.out.println(tags);
        if (tags.isEmpty()) {
            throw new NotFoundException("No tags found with the tagname: "+ tagname);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new APIResponse<>("Search tag successful", tags, HttpStatus.OK,  new Date())
            );
        }
    }
    @DeleteMapping("/api/memo/tags/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable Integer id) {
        String message =  tagsService.deleteTag(id);
        System.out.println(message);
        return ResponseEntity.status(HttpStatus.OK).body("successfully Tag deleted");
    }
}
