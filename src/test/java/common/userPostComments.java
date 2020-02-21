package common;

public class userPostComments {
    private String commentBody;
    private String email;
    private String name;
    private int id;
    private int commentPostId;




    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPostid() {
        return commentPostId;
    }
    public void setPostid(int postid) {
        this.commentPostId = postid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getBody() {
        return commentBody;
    }
    public void setBody(String body) {
        this.commentBody = body;
    }

}