package com.ninjasquad;

import net.codestory.http.WebServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class VotesTest {

    private static WebServer webServer = Application.configure();

    @BeforeClass
    public static void setUp() throws Exception {
        webServer.start(4245);
    }

    @Test
    public void vote() {
        given().header("token", token())
                .put("http://localhost:4245/shows/1")
                .then().assertThat()
                .body("name", equalTo("Game of Thrones"))
                .body("score", equalTo(1))
                .body("userAlreadyVoted", equalTo(true))
                .body("image", equalTo("http://ia.media-imdb.com/images/M/MV5BMTk0NDg4NjQ5N15BMl5BanBnXkFtZTgwMzkzNTgyMTE@._V1_SX214_AL_.jpg"))
                .body("id", equalTo(1));
    }

    @Test
    public void voteTwice() {
        given().header("token", token())
                .put("http://localhost:4245/shows/2")
                .then().assertThat()
                .body("name", equalTo("Breaking Bad"))
                .body("score", equalTo(1))
                .body("userAlreadyVoted", equalTo(true))
                .body("image", equalTo("http://ia.media-imdb.com/images/M/MV5BMTU2MTgzMzQxNV5BMl5BanBnXkFtZTcwODg4NTQ3OQ@@._V1_SX214_AL_.jpg"))
                .body("id", equalTo(2));

        given().header("token", token())
                .put("http://localhost:4245/shows/2")
                .then().assertThat()
                .statusCode(405);
    }

    @Test
    public void voteWithoutToken() {
        given().put("http://localhost:4245/shows/1")
                .then().assertThat()
                .statusCode(403);
    }

    @Test
    public void voteWithWrongToken() {
        given().header("token", "hello")
                .put("http://localhost:4245/shows/1")
                .then().assertThat()
                .statusCode(403);
    }

    @Test
    public void downvote() {
        given().header("token", token())
                .delete("http://localhost:4245/shows/3")
                .then().assertThat()
                .body("name", equalTo("The Wire"))
                .body("score", equalTo(0))
                .body("userAlreadyVoted", equalTo(false))
                .body("image", equalTo("http://ia.media-imdb.com/images/M/MV5BNjc1NzYwODEyMV5BMl5BanBnXkFtZTcwNTcxMzU1MQ@@._V1_SY317_CR9,0,214,317_AL_.jpg"))
                .body("id", equalTo(3));
    }

    @Test
    public void downvoteTwice() {
        given().header("token", token())
                .delete("http://localhost:4245/shows/4")
                .then().assertThat()
                .body("name", equalTo("House of Cards"))
                .body("score", equalTo(0))
                .body("userAlreadyVoted", equalTo(false))
                .body("image", equalTo("http://ia.media-imdb.com/images/M/MV5BMjI2NjkxMjYwM15BMl5BanBnXkFtZTgwNzc4MTE5MDE@._V1_SX214_AL_.jpg"))
                .body("id", equalTo(4));

        given().header("token", token())
                .delete("http://localhost:4245/shows/4")
                .then().assertThat()
                .statusCode(405);
    }

    @Test
    public void downvoteWithoutToken() {
        given().delete("http://localhost:4245/shows/1")
                .then().assertThat()
                .statusCode(403);
    }

    @Test
    public void downvoteWithWrongToken() {
        given().header("token", "hello")
                .delete("http://localhost:4245/shows/1")
                .then().assertThat()
                .statusCode(403);
    }

    private String token() {
        return String.valueOf("cedric".hashCode());
    }

    @AfterClass
    public static void tearDown() throws Exception {
        webServer.stop();
    }
}