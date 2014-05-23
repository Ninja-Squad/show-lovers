package com.ninjasquad;

import com.jayway.restassured.RestAssured;
import net.codestory.http.WebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UsersTest {

    private WebServer webServer = Application.configure();

    @Before
    public void setUp() throws Exception {
        webServer.start(4242);
    }

    @Test
    public void get() {
        RestAssured.get("http://localhost:4242/users").then().assertThat()
                .body("[0].name", equalTo("Cedric"));
    }

    @After
    public void tearDown() throws Exception {
        webServer.stop();
    }
}