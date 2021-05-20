package com.andy.springbootjpa.repo;

import com.andy.springbootjpa.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepo extends JpaRepository<Post, Long> {

    Post findById(long id);

    Page<Post> findByUserId(long user_id, Pageable pageable);

}
