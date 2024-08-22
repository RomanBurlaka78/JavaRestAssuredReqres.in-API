package api.pojo.post;

public class ResponsePostLogin {
    private  String token;

    public String getToken() {
        return token;
    }

    public ResponsePostLogin(String token) {
        this.token = token;
    }

    public ResponsePostLogin() {
        super();
    }
}
