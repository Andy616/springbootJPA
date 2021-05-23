package com.andy.springbootjpa;

import com.andy.springbootjpa.model.User;
import com.andy.springbootjpa.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SpringbootJpaApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private void createTestUser() {
        User user = new User();
        user.setName("Andy");
        user.setEmail("andy@gmail.com");
        user.setPassword("password");
        try {
            userService.register(user);
        } catch (DataIntegrityViolationException ignored) {
        }
    }

    @Test
    public void createUser() throws Exception {
        createTestUser();
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
        String post = "{\"content\":\"Test publishing\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(post)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithUserDetails("andy@gmail.com")
    public void LoginAndPublishPost() throws Exception {
        String post = "{\"content\":\"Test publishing\"}";
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
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithUserDetails("andy@gmail.com")
    public void loginAndUpdatePost() throws Exception {
        String post = "{\"content\":\"updating post~~\"}";
        // post id=1 is not posted by this user
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/post/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(post)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithUserDetails("andy@gmail.com")
    public void LoginPublishAndUpdatePost() throws Exception {
        String post = "{\"content\":\"Test publishing\"}";
        // publish post
        MvcResult mock = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(post)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String publishedPostId = mock.getResponse().getContentAsString().split(",")[0].split(":")[1].substring(1, 3);
        // update post
        String updatePost = "{\"content\":\"Test updating\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/post/" + publishedPostId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatePost)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deletePost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/post/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithUserDetails("andy@gmail.com")
    public void loginAndDeletePost() throws Exception {
        // post id=1 is not posted by this user
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/post/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithUserDetails("andy@gmail.com")
    public void LoginPublishAndDeletePost() throws Exception {
        String post = "{\"content\":\"Test publishing\"}";
        // publish post
        MvcResult mock = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(post)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String publishedPostId = mock.getResponse().getContentAsString().split(",")[0].split(":")[1].substring(1, 3);
        // delete post
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/post/" + publishedPostId)
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

}
