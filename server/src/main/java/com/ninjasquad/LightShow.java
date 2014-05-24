package com.ninjasquad;

import java.util.concurrent.atomic.AtomicInteger;

public class LightShow {

    private int id;

    private String name;

    private AtomicInteger score;

    public LightShow() {
    }

    public LightShow(int id, String name, AtomicInteger score) {
        this.id = id;
        this.name = name;
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

    public void setScore(AtomicInteger score) {
        this.score = score;
    }
}
