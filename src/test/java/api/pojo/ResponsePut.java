package api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponsePut extends PutUser {
    private String updateAt;
    public ResponsePut(String name, String job, String updateAt) {
        super(name, job);
        this.updateAt = updateAt;
    }
    public ResponsePut() {
        super();
    }

    public String getUpdateAt() {
        return updateAt;
    }
}
