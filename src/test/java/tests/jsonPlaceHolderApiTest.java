package tests;

import com.google.gson.Gson;
import common.userPostComments;
import common.userPosts;
import common.userModel;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.validator.routines.EmailValidator;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertTrue;

public class jsonPlaceHolderApiTest {

    private int userFoundId;

    @BeforeTest
    public void beforeTest() {
        baseURI="https://jsonplaceholder.typicode.com/";

    }
    @ Test (priority=1)
    void apiValidation()
    {

        RequestSpecification HttpRequest= given();
        Response response=HttpRequest.request(Method.GET,"/");
        String responseBody= response.getBody().asString();
        int statusCode= response.getStatusCode();
        Assert.assertEquals(statusCode, 200);


    }


    @Test (priority=2)
    void nameVerification() {
        boolean userFound=false;
        RequestSpecification HttpRequest= given();

        Response response=HttpRequest.request(Method.GET,"/users");

        String responseBody= response.getBody().asString();
        Gson gson = new Gson();
        userModel[] users;
        users = gson.fromJson(responseBody, userModel[].class);
        for (userModel user : users) {
            if (user.getUsername().equals("Samantha")) {
                userFound = true;
                userFoundId = user.getId();
            }
        }
        assertTrue(userFound);

    }

    @Test (priority=3)
    void searchPostVerification()  {

        RequestSpecification HttpRequest= given();
        Response response=HttpRequest.request(Method.GET,"posts?userId="+userFoundId);

        String responseBody= response.getBody().asString();

        Gson gson = new Gson();
        userPosts[] posts;
        posts = gson.fromJson(responseBody, userPosts[].class);
        for (userPosts post : posts) {
            Assert.assertEquals(post.getUserId(), userFoundId);
        }
    }


}