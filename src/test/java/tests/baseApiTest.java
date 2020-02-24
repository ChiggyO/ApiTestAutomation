package tests;

import com.jayway.jsonpath.JsonPath;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static common.endPoints.*;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;


@SuppressWarnings("SuspiciousToArrayCall")
public class baseApiTest {

    protected final static String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    @BeforeTest
    public void beforeTest() {
        baseURI = "https://jsonplaceholder.typicode.com";

    }


    public static String getUser(String testUser) throws RuntimeException {

        String userResponse = given().
                when().get(userPath).
                then().extract().asString();

        List<Object> userList = JsonPath.parse(userResponse).
                read("$.[?(@.username=='" + testUser + "')].username");

        return String.valueOf(userList.get(0));
    }


    public static int getUserId(String userName) {
        int id;

        String userResponse = given().
                when().get(userPath).
                then().extract().asString();

        List<Object> userIdList = JsonPath.parse(userResponse)
                .read("$[?(@.username == '" + userName + "')].id");

        id = (Integer) userIdList.get(0);


        return id;
    }

    public static Integer[] getPostId(int userId) {
        String postResponse = given().
                when().get(postsPath).
                then().extract().asString();

        ArrayList<String> postsList = new ArrayList<String>();

        List<Object> postIdsList = JsonPath.parse(postResponse)
                .read("$[?(@.userId == " + userId + ")].id");

        //Reporter.log(" Posts for " + userId + " : " + postResponse, true);


        return postIdsList.toArray(new Integer[0]);

    }

    public static ArrayList<String> getComments(Integer[] postId) {

        //Reporter.log(" The response returned is:" + commentsResponse, true);

        String commentsResponse = given().
                when().get(commentsPath).
                then().extract().asString();


        ArrayList<String> commentsList = new ArrayList<String>();

        for (int pIds : postId) {
            List<Object> fetchComments = com.jayway.jsonpath.JsonPath.parse(commentsResponse)
                    .read("$[?(@.postId == " + pIds + ")].body");

            String postComments = String.valueOf(fetchComments);

            commentsList.add(postComments);
            System.out.println("List of Post Ids" + pIds);
        }


        return commentsList;
    }


    public static ArrayList<String> getValidEmailAddress(Integer[] PostId) {

        ArrayList<String> emailList = new ArrayList<String>();

        String emailResponse = given().
                when().get(commentsPath).
                then().extract().asString();




        for (int pIds : PostId) {
            List<String> getEmailAddress = com.jayway.jsonpath.JsonPath.parse(emailResponse)
                    .read("$[?(@.postId == " + pIds + ")].email");
            Pattern pattern = Pattern.compile(regex);

           // String postEmails = String.valueOf(getEmailAddress);

            for (String email : getEmailAddress) {
                Matcher matcher = pattern.matcher(email);
                System.out.println(email + " is valid:  " + matcher.matches());
            }
        }
        return emailList;
    }
}
