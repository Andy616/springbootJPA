package com.andy.springbootjpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.andy.springbootjpa.model.User;
import com.andy.springbootjpa.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser(){
        User user = new User();
        user.setEmail("andygyr0616@gmail.com");
        user.setPassword("P@$$W0RD");
        user.setName("Andy");

        User savedUser = userRepo.save(user);

        User existUser = entityManager.find( User.class, savedUser.getId());

        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void testFindUserByEmail(){
        String email = "andygyr0616@gmail.com";
        User user = userRepo.findByEmail(email);
        assertThat(user).isNotNull();
    }

    @Test
    public void testFindUserByEmailAndPassword(){
        String email = "andygyr0616@gmail.com";
        String password = "P@$$W0RD";
        User user = userRepo.findByEmailAndPassword(email, password);
        assertThat(user).isNotNull();
    }

    @Test
    public void testFindUserById(){
        long userId = 1;
        String email = "andygyr616@gmail.com";
        User user = userRepo.findById(userId);
        assertThat(user.getEmail()).isEqualTo(email);
    }

}
