package com.ninjasquad;

import net.codestory.http.WebServer;

public class Application {

    public static void main(String... args) {

        // launch web server
        new WebServer(routes -> routes.get("/", "hello")).start(8081);
    }
}
