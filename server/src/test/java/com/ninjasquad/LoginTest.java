package com.ninjasquad;

import net.codestory.http.WebServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest {

    private static WebServer webServer = Application.configure();

    @BeforeClass
    public static void setUp() throws Exception {
        webServer.start(4242);
    }

    @Test
    public void login() {
        given().contentType("application/json")
                .body(new Credentials("cedric", "cedric"))
                .post("http://localhost:4242/login")
                .then().assertThat()
                .body(equalTo(String.valueOf("cedric".hashCode())));
    }

    @Test
    public void badPassword() {
        given().contentType("application/json")
                .body(new Credentials("cedric", "bad"))
                .post("http://localhost:4242/login")
                .then().assertThat()
                .statusCode(400);
    }

    @Test
    public void badLogin() {
        given().contentType("application/json")
                .body(new Credentials("bad", "bad"))
                .post("http://localhost:4242/login")
                .then().assertThat()
                .statusCode(400);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        webServer.stop();
    }
}