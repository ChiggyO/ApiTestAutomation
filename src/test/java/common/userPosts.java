package common;

public class userPosts {
    private String postTitle;
    private int userPostId;
    private String postBody;
    private int userId;


    public int getId() {
        return userPostId;
    }
    public void setPostId(int id) {
        this.userPostId = id;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getTitle() {
        return postTitle;
    }
    public void setTitle(String title) {
        this.postTitle = title;
    }
    public String getPostBody() {
        return postBody;
    }
    public void getPostBody(String body) {
        this.postBody = body;
    }

}