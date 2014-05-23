package com.ninjasquad;

import net.codestory.http.WebServer;
import net.codestory.http.payload.Payload;

import java.util.Arrays;
import java.util.List;

public class Application {

    public static void main(String... args) {

        // launch web server
        User user = new User("Cedric", "cedric", "cedric", 27);
        List<User> users = Arrays.asList(user);
        new WebServer(routes -> routes
                .get("/users", () -> new Payload(users).withAllowOrigin("*"))
        ).start(8081);
    }
}
