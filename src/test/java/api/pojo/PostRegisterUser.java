package api.pojo;

public class PostRegisterUser {
    private  String email;

    private String password;

    public PostRegisterUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public PostRegisterUser() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
