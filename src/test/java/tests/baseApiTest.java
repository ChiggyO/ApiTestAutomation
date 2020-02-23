package tests;


import com.jayway.jsonpath.JsonPath;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

//import io.restassured.path.json.JsonPath;

public class baseApiTest {

    protected final String validEmailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

    protected final static String userPath = "/users";
    protected static final String postsPath = "/posts/";
    protected final String commentsPath = "/posts/3/comments";
    protected final String invalidPath = "/userrs";
    protected final String postPath = "/search/request";

    @BeforeTest
    public void beforeTest() {
        baseURI = "https://jsonplaceholder.typicode.com";

    }


    public static String getUser(String UserName) throws RuntimeException {

        String userResponse = given().
                when().get(userPath).
                then().extract().asString();

        List<Object> userList = JsonPath.parse(userResponse)
                .read("$.[?(@.username=='" + UserName + "')].username");

        boolean validUser = userList.contains(UserName);
        if (validUser) {
            Reporter.log(UserName + " is a valid user", true);
        } else
            throw new RuntimeException("User does not exist");
        return String.valueOf(userList.get(0));
    }


    public static int getUserId(String userName) {
        int id;

        String userResponse = given().
                when().get(userPath).
                then().extract().asString();


        List<Object> userIdList = JsonPath.parse(userResponse)
                .read("$[?(@.username == '" + userName + "')].id");

        if (userIdList == null || userIdList.isEmpty()) {
            // return -1;
            Reporter.log(" User not valid ", true);
        }
        assert userIdList != null;
        id = (Integer) userIdList.get(0);

        Reporter.log(" User id for user" + userName + " is: " + id, true);

        return id;
    }

    public static Integer[] getPostId(int userId) {
        String postResponse = given().
                when().get(postsPath).
                then().extract().asString();


        List<Object> postIdsList = JsonPath.parse(postResponse)
                .read("$[?(@.userId == " + userId + ")].id");

        Integer[] postIds = postIdsList.toArray(new Integer[0]);

        Reporter.log(" Post Ids for" + userId + "  is: " + Arrays.toString(postIds), true);

        return postIds;

    }

    public static List<String> getComments(Integer[] postId) {
        ArrayList<String> postsList = new ArrayList<String>();

        String commentsResponse = given().
                when().get(postsPath).
                then().extract().asString();


        for (int ptIds : postId) {
            List<Object> commentsList = JsonPath.parse(commentsResponse)
                    .read("$[?(@.postId == " + ptIds + ")].body");
            String postComment = String.valueOf(commentsList);

            Reporter.log(" Comments for user's posts are listed  below", true);

            postsList.add(postComment);
        }
        return postsList;
    }
}