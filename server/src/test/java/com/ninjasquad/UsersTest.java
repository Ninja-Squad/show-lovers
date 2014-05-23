package com.ninjasquad;

import com.jayway.restassured.RestAssured;
import net.codestory.http.WebServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UsersTest {

    private static WebServer webServer = Application.configure();

    @BeforeClass
    public static void setUp() throws Exception {
        webServer.start(4242);
    }

    @Test
    public void get() {
        RestAssured.get("http://localhost:4242/users")
                .then().assertThat()
                .body("[0].name", equalTo("Cedric"));
    }

    @Test
    public void post() {
        given().contentType("application/json")
                .body(new User("JB", "jb", "jb", 38))
                .post("http://localhost:4242/users")
                .then().assertThat()
                .body(equalTo(String.valueOf("jb".hashCode())));

        given().contentType("application/json")
                .body(new User("JB", "jb", "jb", 38))
                .post("http://localhost:4242/users")
                .then().assertThat()
                .statusCode(400);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        webServer.stop();
    }
}