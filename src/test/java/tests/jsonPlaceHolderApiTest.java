package tests;

import com.google.gson.Gson;
import common.userPostComments;
import common.userPosts;
import common.users;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.poi.xssf.model.Comments;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class jsonPlaceHolderApiTest extends baseApiTest {

    int userIdFound;
    protected final String userUnderTest = "Samantha";
    protected final String userPath = "/users";
    protected final String postsPath = "/posts/";
    protected final String commentsPath = "/posts/3/comments";
    protected final String invalidPath = "/userrs";
    protected final String postPath = "/search/request";



    @Test(priority = 1)
    public void testApiReachable() {
        given().contentType(ContentType.JSON).
                when().get(userPath).
                then().statusCode(200);

        Reporter.log("The Api Enpoint ( " + baseURI + userPath + " ) is reachable.", true);
    }


    @Test(priority = 2)
    public void confirmUserExists()  {
        boolean userFound = false;

        String response = given().
                when().get(userPath).
                then().extract().asString();

        Reporter.log("The response returned is: " + response, true);

        Gson responseBody = new Gson();
        users[] userObj;
        userObj = responseBody.fromJson(response, users[].class);

        for (common.users users : userObj) {
            if (users.getUsername().equals(userUnderTest)) {
                //userFound=true;
                userIdFound = users.getId();
                System.out.println(userIdFound);

            }
        }
        Reporter.log("The userId for " + userUnderTest + " is :" + userIdFound, true);
    }

    @Test(priority = 3)
    public void confirmUserPosts() {

        String response = given().param("userId", 3).
                when().get(postsPath).
                then().extract().asString();

        Gson responseBody = new Gson();
        userPosts[] posts;
        posts = responseBody.fromJson(response, userPosts[].class);
        for (userPosts post : posts) {
            assertEquals(post.getUserId(), 3);
        }
        Reporter.log("The posts for "+userUnderTest+ " are: " + response, true);

    }


}