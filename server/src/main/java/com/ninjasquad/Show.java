package com.ninjasquad;

import java.util.concurrent.atomic.AtomicInteger;

public class Show {

    private int id;

    private String name;

    private String image;

    private boolean userAlreadyVoted;

    private AtomicInteger score = new AtomicInteger(0);

    public Show() {
    }

    public Show(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Show(int id, String name, String image, boolean userAlreadyVoted, AtomicInteger score) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.userAlreadyVoted = userAlreadyVoted;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AtomicInteger getScore() {
        return score;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isUserAlreadyVoted() {
        return userAlreadyVoted;
    }

    public void setUserAlreadyVoted(boolean userAlreadyVoted) {
        this.userAlreadyVoted = userAlreadyVoted;
    }

}
