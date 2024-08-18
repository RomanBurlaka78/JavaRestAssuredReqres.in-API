package api.pojo;

public class PutUser {
    private  String name;
    private String job;

    public PutUser(String name, String job) {
        this.name = name;
        this.job = job;
    }
    public PutUser() {
        super();
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}
