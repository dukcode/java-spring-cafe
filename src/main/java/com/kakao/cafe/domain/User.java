package com.kakao.cafe.domain;

import com.kakao.cafe.controller.UserDto;

public class User {

    private Long id;
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(UserDto form) {
        userId = form.getUserId();
        password = form.getPassword();
        name = form.getName();
        email = form.getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}