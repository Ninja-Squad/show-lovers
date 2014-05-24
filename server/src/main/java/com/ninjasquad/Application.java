package com.ninjasquad;

import net.codestory.http.WebServer;
import net.codestory.http.payload.Payload;

import java.util.*;

public class Application {

    private static WebServer webServer;

    private static Map<String, User> users = new HashMap<>();

    private static Map<String, Show> shows = new HashMap<>();

    public static void main(String... args) {

        // launch web server
        configure();
        webServer.start(8081);
    }

    public static WebServer configure() {
        users.put(id("cedric"), new User("Cedric", "cedric", "cedric", 27));
        shows.put("got", new Show(1, "Game of Thrones"));

        webServer = new WebServer(routes -> routes
                .options("/users", () -> new Payload("").withAllowMethods("GET", "POST").withAllowOrigin("*").withAllowHeaders("Content-Type", "token"))
                .get("/users", () -> new Payload(users()).withAllowOrigin("*").withAllowHeaders("token"))
                .post("/users", context -> {
                    User user = context.contentAs(User.class);
                    String id = id(user.getLogin());
                    if (users.get(id) != null) {
                        return Payload.badRequest();
                    } else {
                        return new Payload(createUser(user)).withAllowOrigin("*").withAllowHeaders("Content-Type", "token");
                    }
                })
                .options("/login", () -> new Payload("").withAllowMethods("POST").withAllowOrigin("*").withAllowHeaders("Content-Type", "token"))
                .post("/login", context -> {
                    Credentials credentials = context.contentAs(Credentials.class);
                    String id = id(credentials.getLogin());
                    if (users.get(id) == null || !users.get(id).getPassword().equals(credentials.getPassword())) {
                        return Payload.badRequest();
                    } else {
                        return new Payload(id).withAllowOrigin("*").withAllowHeaders("Content-Type");
                    }
                })
                .options("/shows", () -> new Payload("").withAllowMethods("GET").withAllowOrigin("*").withAllowHeaders("Content-Type", "token"))
                .get("/shows", () -> new Payload(shows()).withAllowOrigin("*").withAllowHeaders("token"))
        );
        return webServer;
    }

    private static List<Show> shows() {
        return new ArrayList<>(shows.values());
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
