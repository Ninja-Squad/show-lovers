package com.ninjasquad;

import net.codestory.http.WebServer;
import net.codestory.http.payload.Payload;

import java.util.Arrays;
import java.util.List;

public class Application {

    private static WebServer webServer;

    public static void main(String... args) {

        // launch web server
        webServer.start(8081);
    }

    public static WebServer configure() {
        User user = new User("Cedric", "cedric", "cedric", 27);
        List<User> users = Arrays.asList(user);
        webServer = new WebServer(routes -> routes
                .get("/users", () -> new Payload(users).withAllowOrigin("*"))
        );
        return webServer;
    }
}
