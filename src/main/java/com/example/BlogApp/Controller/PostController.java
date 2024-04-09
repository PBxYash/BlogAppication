package com.example.BlogApp.Controller;

import com.example.BlogApp.DTO.PostDto;
import com.example.BlogApp.Service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public void deletePost(@PathVariable long id ){
        postService.deletePost(id);
    }

//    http://localhost:8081/api/posts?pageNo=0&pageSize=3&sortBy=title&sortDir=asc

//    @GetMapping
//    @CrossOrigin(origins = "http://localhost:4200")
//    public ResponseEntity<List<PostDto>>getAllPost(
//            @RequestParam(name="pageNo",defaultValue = "0",required = false)int pageNo,
//            @RequestParam(name="pageSize",defaultValue = "3",required = false)int pageSize,
//            @RequestParam(name="sortBy",defaultValue = "id",required = false) String sortBy,
//            @RequestParam(name="sortDir",defaultValue = "asc",required = false)String sortDir
//    ) {
//        List<PostDto> dto = postService.getAllPost(pageNo, pageSize,sortBy,sortDir);
//        return new ResponseEntity<>(dto,HttpStatus.OK);
//    }
    @PutMapping
    @CrossOrigin(origins = "http://localhost:4200")
    public  ResponseEntity<PostDto>updatePost(
            @RequestParam("postId") long postId ,
            @RequestBody PostDto postDto
    ){
        PostDto dtos = postService.updatePost(postId, postDto);
        return  new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<PostDto>>getAllWIthUserID(){
        List<PostDto> allPostWithUserID = postService.getAllPostWithUserID();
        return new ResponseEntity<>(allPostWithUserID,HttpStatus.OK);
    }
}

