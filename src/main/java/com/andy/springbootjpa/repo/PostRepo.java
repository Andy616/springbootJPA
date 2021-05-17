package com.andy.springbootjpa.repo;

import com.andy.springbootjpa.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {

    Post findById(long id);

    List<Post> findAllByUserId(long user_id);

    Page<Post> findByUserId(long user_id, Pageable pageable);

}
