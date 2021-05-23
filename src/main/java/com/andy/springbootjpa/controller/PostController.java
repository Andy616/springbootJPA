package com.andy.springbootjpa.controller;

import com.andy.springbootjpa.dto.PostDTO;
import com.andy.springbootjpa.exceptions.NotAuthorizedException;
import com.andy.springbootjpa.exceptions.ResourceNotFoundException;
import com.andy.springbootjpa.model.Post;
import com.andy.springbootjpa.service.PostService;
import com.andy.springbootjpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

    @Autowired
    private UserService userService;


    private Long getUserIdFromRequest() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return userService.findByEmail(user.getUsername()).getId();
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<?> publish(@RequestBody PostDTO postDTO) {
        // todo: how to get user after login?
        // fields: content
        Post post = new Post();
        post.setPublishedOn(null);
        long userId = getUserIdFromRequest();
        post.setUser(postService.getUser(userId));
        post.setContent(postDTO.getContent());
        post.setLastUpdated(post.getPublishedOn());
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
    public ResponseEntity<?> updatePost(@PathVariable(value = "id") Long postId, @RequestBody PostDTO postDTO)
            throws ResourceNotFoundException, NotAuthorizedException {
        Post post = postService.getPostById(postId);
        long userId = getUserIdFromRequest();
        if (post == null) {
            throw new ResourceNotFoundException("Post not found, id = " + postId);
        } else if (userId != post.getUser().getId()) {
            throw new NotAuthorizedException("Not authorized to do this operation");
        } else {
            post.setContent(postDTO.getContent());
            post.setLastUpdated(null);
            post = postService.save(post);
            return ResponseEntity.status(HttpStatus.OK).body(PostDTO.createDTO(post));
        }

    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deletePost(@PathVariable(value = "id") Long postId)
            throws ResourceNotFoundException, NotAuthorizedException {
        Post post = postService.getPostById(postId);
        long userId = getUserIdFromRequest();
        if (post == null) {
            throw new ResourceNotFoundException("Post not found, id = " + postId);
        } else if (userId != post.getUser().getId()) {
            throw new NotAuthorizedException("Not authorized to do this operation");
        } else {
            postService.deletePost(post);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted post which id = " + postId, Boolean.TRUE);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}
