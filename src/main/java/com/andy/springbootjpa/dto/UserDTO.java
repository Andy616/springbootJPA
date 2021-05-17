package com.andy.springbootjpa.dto;

import com.andy.springbootjpa.model.User;

public class UserDTO {

    private String id;
    private String email;
    private String name;
    private String password;

    public UserDTO() {
    }

    public static UserDTO createDTO(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId().toString());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
