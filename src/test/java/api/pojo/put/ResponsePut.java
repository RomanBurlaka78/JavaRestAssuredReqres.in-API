package api.pojo.put;

import api.pojo.put.PutUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
