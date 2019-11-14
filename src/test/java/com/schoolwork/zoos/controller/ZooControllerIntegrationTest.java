package com.schoolwork.zoos.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.number.OrderingComparison.lessThan;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZooControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp(){
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    public void whenMeasuredResponseTime(){
        given().when().get("/zoos/zoos").then().time(lessThan(5000L));
    }

    //    GET /restaurants/restaurant/{restaurantId}
    @Test
    public void givenFoundZooId() throws Exception
    {
        long aZoo = 1L;
        given().when()
                .get("/zoos/zoo/" + aZoo)
                .then()
                .statusCode(200)
                .and()
                .body(containsString("Zoo"));
    }
}
