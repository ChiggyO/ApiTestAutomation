package tests;

import common.endPoints;
import io.restassured.http.ContentType;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class jsonPlaceHolderApiTest extends baseApiTest {

    protected final String userUnderTest = "Samantha";
    protected int userUnderTestId;



    @Test(priority = 1)
    public void testApiReachable() {
        given().contentType(ContentType.JSON).
                when().get().
                then().statusCode(200);

        Reporter.log("The Api Endpoint ( " + baseURI + " ) is reachable.", true);
    }


    @Test(priority = 2)
    public void getUserDetails() {
        String confirmUser = getUser(userUnderTest);
        int userId = getUserId(userUnderTest);
        //int userId = getUserId(userUnderTestId);
        assertNotNull(confirmUser);
        assertEquals(confirmUser, userUnderTest);

        Reporter.log(userUnderTest + " is a valid user name", true);

        Reporter.log("User id for user name " + userUnderTest + " is: " + userId, true);

        Reporter.log("User details successfully returned", true);
    }

    @Test(priority = 3)
    public void getUserPosts() {

        int userId = getUserId(userUnderTest);
        Integer[] postId = getPostId(userId);
        assertNotNull(postId);

        //Reporter.log(" Post Ids for" + userId + "  is: " + Arrays.toString(postId), true);

        Reporter.log("User posts successfully returned", true);

    }


    @Test(priority = 4)
    public void getPostComments() {

        int userId = baseApiTest.getUserId(userUnderTest);
        Integer[] postId = baseApiTest.getPostId(userId);
        assertNotNull(getComments(postId));
        Reporter.log("User post comments successfully returned", true);


    }

    @Test(priority = 5)
    public void validateCommentsEmail() {

        int userId = baseApiTest.getUserId(userUnderTest);
        Integer[] postId = baseApiTest.getPostId(userId);
        ArrayList<String> emailList = getValidEmailAddress(postId);
        assertNotNull(emailList);
        Reporter.log("Email format successfully confirmed", true);
    }
}
