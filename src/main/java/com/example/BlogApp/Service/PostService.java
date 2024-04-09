package com.example.BlogApp.Service;


import com.example.BlogApp.DTO.PostDto;

import java.util.List;

public interface PostService {
    public PostDto createPost(PostDto postDto);

    void deletePost(long id);

//    List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String Dir);

    PostDto updatePost(long postId, PostDto postDto);

    List<PostDto>getAllPostWithUserID();
}

