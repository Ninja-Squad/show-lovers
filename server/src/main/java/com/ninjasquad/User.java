package com.ninjasquad;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

public class User {

    private String name;

    private String login;

    private String password;

    private int age;

    @JsonIgnore
    private Set<String> shows = new HashSet<>();

    public User() {
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<String> getShows() {
        return shows;
    }

    public synchronized void addShow(String id) {
        shows.add(id);
    }

    public synchronized void removeShow(String id) {
        shows.remove(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", shows=" + shows +
                '}';
    }
}
