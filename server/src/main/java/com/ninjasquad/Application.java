package com.ninjasquad;

import net.codestory.http.WebServer;
import net.codestory.http.payload.Payload;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class Application {

    private static WebServer webServer;

    private static Map<String, User> users = new HashMap<>();

    private static Map<String, Show> shows = new HashMap<>();

    public static void main(String... args) {

        // launch web server
        configure();
        webServer.start(80);
    }

    public static WebServer configure() {
        init();

        webServer = new WebServer(routes -> routes
                .options("/users", () -> new Payload("").withAllowMethods("GET", "POST").withAllowOrigin("*").withAllowHeaders("Content-Type", "token"))
                .get("/users", () -> new Payload(users()).withAllowOrigin("*").withAllowHeaders("token"))
                .post("/users", context -> {
                    User user = context.contentAs(User.class);
                    System.out.println("user = " + user);
                    String id = id(user.getLogin());
                    if (users.get(id) != null) {
                        return Payload.badRequest().withAllowOrigin("*").withAllowHeaders("token");
                    } else {
                        return new Payload(createUser(user)).withAllowOrigin("*").withAllowHeaders("Content-Type", "token");
                    }
                })
                .options("/login", () -> new Payload("").withAllowMethods("POST").withAllowOrigin("*").withAllowHeaders("Content-Type", "token"))
                .post("/login", context -> {
                    Credentials credentials = context.contentAs(Credentials.class);
                    System.out.println("credentials = " + credentials);
                    String id = id(credentials.getLogin());
                    if (users.get(id) == null || !users.get(id).getPassword().equals(credentials.getPassword())) {
                        return Payload.badRequest().withAllowOrigin("*").withAllowHeaders("token");
                    } else {
                        return new Payload(id).withAllowOrigin("*").withAllowHeaders("Content-Type");
                    }
                })
                .options("/shows", () -> new Payload("").withAllowMethods("GET").withAllowOrigin("*").withAllowHeaders("Content-Type", "token"))
                .get("/shows", () -> new Payload(shows()).withAllowOrigin("*").withAllowHeaders("token"))
                .options("/shows/:id", (context, id) -> new Payload("").withAllowMethods("GET", "PUT", "DELETE").withAllowOrigin("*").withAllowHeaders("Content-Type", "token"))
                .get("/shows/:id", (context, id) -> {
                    String token = context.header("token");
                    System.out.println("user " + token + " wants the show " + id);
                    if (shows.get(id) == null) {
                        return Payload.notFound().withAllowOrigin("*").withAllowHeaders("token");
                    } else if (token == null || users.get(token) == null) {
                        return new Payload(show(id, null)).withAllowOrigin("*").withAllowHeaders("token");
                    } else {
                        return new Payload(show(id, users.get(token))).withAllowOrigin("*").withAllowHeaders("token");
                    }
                })
                .put("/shows/:id", (context, id) -> {
                    String token = context.header("token");
                    System.out.println("user " + token + " votes for the show " + id);
                    if (shows.get(id) == null) {
                        return Payload.notFound().withAllowOrigin("*").withAllowHeaders("token");
                    } else if (token == null || users.get(token) == null) {
                        return Payload.forbidden().withAllowOrigin("*").withAllowHeaders("token");
                    } else if (users.get(token).getShows().contains(id)) {
                        return Payload.methodNotAllowed().withAllowOrigin("*").withAllowHeaders("token");
                    } else {
                        return new Payload(voteFor(id, users.get(token))).withAllowOrigin("*").withAllowHeaders("token");
                    }
                })
                .delete("/shows/:id", (context, id) -> {
                    String token = context.header("token");
                    System.out.println("user " + token + " downvotes for the show " + id);
                    if (shows.get(id) == null) {
                        return Payload.notFound().withAllowOrigin("*").withAllowHeaders("token");
                    } else if (token == null || users.get(token) == null) {
                        return Payload.forbidden().withAllowOrigin("*").withAllowHeaders("token");
                    } else if (!users.get(token).getShows().contains(id)) {
                        return Payload.methodNotAllowed().withAllowOrigin("*").withAllowHeaders("token");
                    } else {
                        return new Payload(downvoteFor(id, users.get(token))).withAllowOrigin("*").withAllowHeaders("token");
                    }
                })
        );
        return webServer;
    }

    private static void init() {
        User cedric = new User("Cedric", "cedric", "cedric", 27);
        users.put(id("cedric"), cedric);
        shows.put("1", new Show(1, "Game of Thrones", "http://ia.media-imdb.com/images/M/MV5BMTk0NDg4NjQ5N15BMl5BanBnXkFtZTgwMzkzNTgyMTE@._V1_SX214_AL_.jpg"));
        shows.put("2", new Show(2, "Breaking Bad", "http://ia.media-imdb.com/images/M/MV5BMTU2MTgzMzQxNV5BMl5BanBnXkFtZTcwODg4NTQ3OQ@@._V1_SX214_AL_.jpg"));
        shows.put("3", new Show(3, "The Wire", "http://ia.media-imdb.com/images/M/MV5BNjc1NzYwODEyMV5BMl5BanBnXkFtZTcwNTcxMzU1MQ@@._V1_SY317_CR9,0,214,317_AL_.jpg"));
        shows.put("4", new Show(4, "House of Cards", "http://ia.media-imdb.com/images/M/MV5BMjI2NjkxMjYwM15BMl5BanBnXkFtZTgwNzc4MTE5MDE@._V1_SX214_AL_.jpg"));
        shows.put("5", new Show(5, "The Sopranos", "http://ia.media-imdb.com/images/M/MV5BMTIxMjc4NTA2Nl5BMl5BanBnXkFtZTYwNTU2MzU5._V1_SX214_AL_.jpg"));
        shows.put("6", new Show(6, "Dexter", "http://ia.media-imdb.com/images/M/MV5BMTM5MjkwMTI0MV5BMl5BanBnXkFtZTcwODQwMTc0OQ@@._V1_SY317_CR9,0,214,317_AL_.jpg"));
        shows.put("7", new Show(7, "How I met your mother", "http://ia.media-imdb.com/images/M/MV5BMTA5MzAzNTcyNjZeQTJeQWpwZ15BbWU3MDUyMzE1MTk@._V1_SY317_CR0,0,214,317_AL_.jpg"));
        shows.put("8", new Show(8, "The Big Bang Theory", "http://ia.media-imdb.com/images/M/MV5BMjQzMTYyODM4M15BMl5BanBnXkFtZTcwMTAxMDU1MQ@@._V1_SY317_CR10,0,214,317_AL_.jpg"));
        shows.put("9", new Show(9, "House M.D", "http://ia.media-imdb.com/images/M/MV5BMjA4NTkzNjg1OF5BMl5BanBnXkFtZTcwNjg3MTI1Ng@@._V1_SX214_AL_.jpg"));
        shows.put("10", new Show(10, "Lost", "http://ia.media-imdb.com/images/M/MV5BMjA3NzMyMzU1MV5BMl5BanBnXkFtZTcwNjc1ODUwMg@@._V1_SY317_CR17,0,214,317_AL_.jpg"));
        shows.put("11", new Show(11, "Mad Men", "http://ia.media-imdb.com/images/M/MV5BMTg1MTIwODYwMl5BMl5BanBnXkFtZTgwNjAwNzQzMTE@._V1_SY317_CR2,0,214,317_AL_.jpg"));
        voteFor("3", cedric);
        voteFor("4", cedric);
    }

    private static Show voteFor(String id, User user) {
        Show show = shows.get(id);
        show.getScore().incrementAndGet();
        user.addShow(id);
        return show(id, user);
    }

    private static Show downvoteFor(String id, User user) {
        Show show = shows.get(id);
        show.getScore().decrementAndGet();
        user.removeShow(id);
        return show(id, user);
    }

    private static Show show(String id, User user) {
        Show show = shows.get(id);
        boolean alreadyVotedFor = user != null && user.getShows().contains(id);
        return new Show(show.getId(), show.getName(), show.getImage(), alreadyVotedFor, show.getScore());
    }

    private static List<LightShow> shows() {
        return shows.values().stream().map(Application::toLightShow).collect(toList());
    }

    private static LightShow toLightShow(Show show) {
        return new LightShow(show.getId(), show.getName(), show.getScore());
    }

    private static String id(String login) {
        return String.valueOf(login.hashCode());
    }

    private static List<User> users() {
        return users.values().stream().map(Application::removePasswords).collect(toList());
    }

    private static User removePasswords(User user) {
        return new User(user.getName(), user.getLogin(), "", user.getAge());
    }

    private static String createUser(User user) {
        String id = id(user.getLogin());
        users.put(id, user);
        return id;
    }
}
