package api.pojo;

public class ResponsePost {
    private Integer id;
    private String token;

    public ResponsePost(Integer id, String token) {
        this.id = id;
        this.token = token;
    }

    public ResponsePost() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
