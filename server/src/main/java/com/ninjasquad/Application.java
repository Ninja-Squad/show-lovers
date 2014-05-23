package com.ninjasquad;

import net.codestory.http.WebServer;
import net.codestory.http.payload.Payload;

import java.util.*;

public class Application {

    private static WebServer webServer;

    private static Map<String, User> users = new HashMap<>();

    public static void main(String... args) {

        // launch web server
        configure();
        webServer.start(8081);
    }

    public static WebServer configure() {
        users.put(id("cedric"), new User("Cedric", "cedric", "cedric", 27));

        webServer = new WebServer(routes -> routes
                .options("/users", () -> new Payload("").withAllowMethods("GET", "POST").withAllowOrigin("*").withAllowHeaders("Content-Type"))
                .get("/users", () -> new Payload(users()).withAllowOrigin("*"))
                .post("/users", context -> {
                    User user = context.contentAs(User.class);
                    String id = id(user.getLogin());
                    if (users.get(id) != null) {
                        return Payload.badRequest();
                    } else {
                        return new Payload(createUser(user)).withAllowOrigin("*").withAllowHeaders("Content-Type");
                    }
                })
                .options("/login", () -> new Payload("").withAllowMethods("POST").withAllowOrigin("*").withAllowHeaders("Content-Type"))
                .post("/login", context -> {
                    Credentials credentials = context.contentAs(Credentials.class);
                    String id = id(credentials.getLogin());
                    if (users.get(id) == null || !users.get(id).getPassword().equals(credentials.getPassword())) {
                        return Payload.badRequest();
                    } else {
                        return new Payload(id).withAllowOrigin("*").withAllowHeaders("Content-Type");
                    }
                })
        );
        return webServer;
    }

    private static String id(String login) {
        return String.valueOf(login.hashCode());
    }

    private static List<User> users() {
        return new ArrayList<>(users.values());
    }

    private static String createUser(User user) {
        String id = id(user.getLogin());
        users.put(id, user);
        return id;
    }
}
