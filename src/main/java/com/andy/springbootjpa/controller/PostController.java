package com.andy.springbootjpa.controller;

import com.andy.springbootjpa.dto.PostDTO;
import com.andy.springbootjpa.exceptions.ResourceNotFoundException;
import com.andy.springbootjpa.model.Post;
import com.andy.springbootjpa.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/v1/post")
public class PostController {

    @Autowired
    private PostService postService;


    @PostMapping("")
    @ResponseBody
    public ResponseEntity<?> publish(@RequestBody PostDTO postDTO) {
        // todo: how to get user after login?
        // fields: content, user_id
        Post post = new Post();
        post.setPublishedOn(postDTO.getPublishedOn());
        long userId = Long.parseLong(postDTO.getUser_id());
        post.setUser(postService.getUser(userId));
        post.setContent(postDTO.getContent());
        post = postService.save(post);
        return ResponseEntity.status(HttpStatus.OK).body(PostDTO.createDTO(post));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getPost(@PathVariable(value = "id") Long postId) throws ResourceNotFoundException {
        Post post = postService.getPostById(postId);
        if (post == null) {
            throw new ResourceNotFoundException("Post not found, id = " + postId);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(PostDTO.createDTO(post));
        }
    }


    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<?> getPosts(@RequestParam(value = "user_id", required = false) Long userId,
                                      @RequestParam(value = "asc") Boolean asc,
                                      @RequestParam(value = "page") Integer page) {
        /*
         Returns all posts if parameter user_id is not provided.
         */

        Page<Post> posts;
        if (userId == null) {
            posts = postService.getAllPosts(page, asc);
        } else {
            posts = postService.getPostsByUserId(userId, page, asc);
        }
        List<PostDTO> postDTOS = new ArrayList<>();
        for (Post post : posts) {
            postDTOS.add(PostDTO.createDTO(post));
        }
        return ResponseEntity.status(HttpStatus.OK).body(postDTOS);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updatePost(@PathVariable(value = "id") Long postId, @RequestBody PostDTO postDTO) throws ResourceNotFoundException {
        Post post = postService.getPostById(postId);
        if (post == null) {
            throw new ResourceNotFoundException("Post not found, id = " + postId);
        } else {
            post.setContent(postDTO.getContent());
            post.setLastUpdated(null);
            post = postService.save(post);
            return ResponseEntity.status(HttpStatus.OK).body(PostDTO.createDTO(post));
        }

    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deletePost(@PathVariable(value = "id") Long postId) throws ResourceNotFoundException {
        Post post = postService.getPostById(postId);
        if (post == null) {
            throw new ResourceNotFoundException("Post not found, id = " + postId);
        } else {
            postService.deletePost(post);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted post which id = " + postId, Boolean.TRUE);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}
