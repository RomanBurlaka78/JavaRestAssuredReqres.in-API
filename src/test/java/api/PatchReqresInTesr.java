package api;

import api.pojo.put.PutUser;
import api.pojo.put.ResponsePut;
import io.restassured.http.ContentType;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PatchReqresInTesr {
    private final static String URL_REQ_RES = "https://reqres.in/";

    @Test
    @Description("test attempt to patch  a request")
    public void testPatch() {
        PutUser patchUser = new PutUser("morpheus", "zion resident");

        ResponsePut patch = given()
                .baseUri(URL_REQ_RES)
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .body(patchUser)
                .patch("api/users/2")
                .then().log().all()
                .statusCode(200)
                .extract().as(ResponsePut.class);

        Assert.assertEquals(patch.getJob(), "zion resident");
    }
}
