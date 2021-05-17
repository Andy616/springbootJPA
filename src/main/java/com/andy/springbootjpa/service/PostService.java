package com.andy.springbootjpa.service;

import com.andy.springbootjpa.model.Post;
import com.andy.springbootjpa.model.User;
import com.andy.springbootjpa.repo.PostRepo;
import com.andy.springbootjpa.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    private static final String sortBy = "publishedOn";
    private static final int pageSize = 5;

    private Pageable getPageable(int page, boolean ascending){
        // helper function for paging
        Sort sort;
        if (ascending){
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        return PageRequest.of(page, pageSize, sort);
    }

    public User getUser(long userId){
        return userRepo.findById(userId);
    }

    // create, update
    public Post save(Post post){
        return postRepo.save(post);
    }

    // read
    public Post getPostById(long id){
        // returns a single post
        return postRepo.findById(id);
    }


    public Page<Post> getAllPosts(int page, boolean ascending){
        Pageable pageable = getPageable(page, ascending);
        return postRepo.findAll(pageable);
    }

    public Page<Post> getPostsByUserId(long user_id, int page, boolean ascending){
        Pageable pageable = getPageable(page, ascending);
        return postRepo.findByUserId(user_id, pageable);
    }

    // delete
    public void deletePost(Post post){
        postRepo.delete(post);
    }

}
