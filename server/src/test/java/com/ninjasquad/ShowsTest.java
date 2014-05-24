package com.ninjasquad;

import com.jayway.restassured.RestAssured;
import net.codestory.http.WebServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class ShowsTest {

    private static WebServer webServer = Application.configure();

    @BeforeClass
    public static void setUp() throws Exception {
        webServer.start(4242);
    }

    @Test
    public void shows() {
        RestAssured.get("http://localhost:4242/shows")
                .then().assertThat()
                .body("[0].name", equalTo("Game of Thrones"))
                .body("[0].score", equalTo(0))
                .body("[0].id", equalTo(1));
    }

    @Test
    public void get() {
        RestAssured.get("http://localhost:4242/shows/1")
                .then().assertThat()
                .body("name", equalTo("Game of Thrones"))
                .body("score", equalTo(0))
                .body("userAlreadyVoted", equalTo(false))
                .body("image", equalTo("http://ia.media-imdb.com/images/M/MV5BMTk0NDg4NjQ5N15BMl5BanBnXkFtZTgwMzkzNTgyMTE@._V1_SX214_AL_.jpg"))
                .body("id", equalTo(1));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        webServer.stop();
    }
}