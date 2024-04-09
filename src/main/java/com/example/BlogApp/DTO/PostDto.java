package com.example.BlogApp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private long id ;

    private long userId;

    @NotNull
    @Size(min=2,message = "Title size minimum 4")
    private String title;
    @NotNull
    @Size(min=4,message ="description size minimum is 4 ")
    private String description;
    @NotNull
    @Size(min=4,message = "content size minimum is 4")
    private String content;



}
