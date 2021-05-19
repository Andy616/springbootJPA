package com.andy.springbootjpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.andy.springbootjpa.model.Post;
import com.andy.springbootjpa.model.User;
import com.andy.springbootjpa.repo.PostRepo;
import com.andy.springbootjpa.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class PostRepoTest {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testPublishPost() {
        Post post = new Post();
        User user = userRepo.findByEmail("andygyr0616@gmail.com");
        post.setUser(user);
        post.setContent("hi there!");
        Timestamp time = new Timestamp(new Date().getTime());
        post.setPublishedOn(time);
        post.setLastUpdated(time);
        Post savedPost = postRepo.save(post);

        Post existPost = entityManager.find(Post.class, savedPost.getId());

        assertThat(existPost.getContent()).isEqualTo(post.getContent());
    }

    @Test
    public void testFindById() {
        Post post = postRepo.findById(1);
        assertThat(post).isNotNull();
    }


    @Test
    public void testFindAllByUserId() {
        List<Post> posts = postRepo.findAllByUserId(1);
        for (Post p : posts) {
            System.out.println(p.getContent());
            System.out.println(p.getPublishedOn());
            System.out.println(p.getLastUpdated());
            System.out.println("------------------");
        }
        assertThat(posts).isNotNull();
    }

    @Test
    public void testFindByUserId() {
        int page=1, size=5;
        Sort sort = Sort.by("publishedOn").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> posts = postRepo.findAll(pageable);
        System.out.println("*********************");
        for (Post post: posts){
            System.out.println("--------------");
            System.out.println(post.getContent());
        }
    }

    @Test
    public void testUpdatePost() {
        Post post = postRepo.findById(1);
        post.setContent("Test updating post.");
        post.setLastUpdated(null);
        postRepo.save(post);
        assertThat(post.getPublishedOn().compareTo(post.getLastUpdated())).isNegative();
    }

}
