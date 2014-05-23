package com.ninjasquad;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {

    private String name;

    private String login;

    @JsonIgnore
    private String password;

    private int age;

    public User(String name, String login, String password, int age) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }
}
