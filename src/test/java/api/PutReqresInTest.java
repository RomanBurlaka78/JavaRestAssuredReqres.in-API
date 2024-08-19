package api;

import api.pojo.put.PutUser;
import api.pojo.put.ResponsePut;
import io.restassured.http.ContentType;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PutReqresInTest {
    private final static String URL_REQ_RES = "https://reqres.in/";
    @Test
    @Description("test attempt to post a dataPut request with body : String ")
    public void testPutUpdate() {
        String users = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";

        ResponsePut item = given()
                .baseUri(URL_REQ_RES)
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .body(users)
                .put("api/users/2")
                .then().log().all()
                .statusCode(200)
                .extract().as(ResponsePut.class);

        Assert.assertEquals(item.getName(), "morpheus");
    }

    @Test
    @Description("test attempt to post a data with body: PutUser.class")
    public void testPutUpdateRequest() {

        PutUser putUser = new PutUser("morpheus", "zion resident");

        ResponsePut item = given()
                .baseUri(URL_REQ_RES)
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .body(putUser)
                .put("api/users/2")
                .then().log().all()
                .statusCode(200)
                .extract().as(ResponsePut.class);

        Assert.assertEquals(item.getName(), "morpheus");
        Assert.assertEquals(item.getJob(), "zion resident");
    }
}
