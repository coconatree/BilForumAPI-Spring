package com.forum.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forum.PojoClasses.Post;

import com.forum.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/post")
public class PostController
{
    @Autowired
    PostService postService;
    
    @GetMapping(value = "/getTop10")
    public ArrayList<Post> getTopTen()
    {
        try
        {
            return postService.getTop10();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/getAll/{FORUM}")
    public ArrayList<Post> getAllPosts(@PathVariable(value="FORUM") String FORUM)
    {
        try
        {
            return postService.getAllPostsForum(FORUM);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
            System.out.println(exc.getMessage());
        }
        return null;
    }

    @GetMapping("/get/{ID}")
    public Post getPost(@PathVariable(value="ID") String ID)
    {
        try
        {
            Post post = postService.getPost(ID);

            if(post != null)
            {
                return post;
            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
            System.out.println(exc.getMessage());
        }
        return null;
    }

    @PutMapping("/addPost/{FORUM}")
    public ResponseEntity<Post> addPost(@RequestBody String data, @PathVariable(value="FORUM") String FORUM)
    {
        ObjectMapper mapper = new ObjectMapper();

        Post post = null;

        try
        {
            post = mapper.readValue(data, new TypeReference<Post>(){});
            postService.putPostToForum(post, FORUM);
        }
        catch (IOException | ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @PutMapping("/updatePost")
    public ResponseEntity<Post> updateUser(@RequestBody String data)
    {
        ObjectMapper mapper = new ObjectMapper();

        Post post = null;

        try
        {
            post = mapper.readValue(data, new TypeReference<Post>(){});
            postService.updatePost(post);
        }
        catch (IOException | ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{ID}")
    public void deletePost(@PathVariable(value = "ID") String ID)
    {
        try
        {
            postService.deletePost(ID);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
