package com.example.BlogApp.Repository;

import com.example.BlogApp.DTO.PostDto;
import com.example.BlogApp.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
//    @Query("SELECT p FROM Post p JOIN p.user u")
//    List<Post> findAllPostsWithUserId();
}