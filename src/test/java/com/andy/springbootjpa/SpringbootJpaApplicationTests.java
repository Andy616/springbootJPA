package com.andy.springbootjpa;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SpringbootJpaApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createUser() throws Exception {
        String s = Long.toString(System.currentTimeMillis() / 1000L);
        String newUser = "{\"name\":\"user\", \"email\":\"user" + s + "@gmail.com\", \"password\":\"password\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUser)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void publishPost() throws Exception {
        String post = "{\"user_id\":\"1\", \"content\":\"Test publishing\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(post)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void updatePost() throws Exception {
        String post = "{\"content\":\"updating post~~\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/post/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(post)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void deletePost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/post/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getPostInPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/")
                .param("page", "1")
                .param("asc", "true")
                .param("user_id", "1") // user_id is not required.
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }


    // create sample data, delete repoTests
}
