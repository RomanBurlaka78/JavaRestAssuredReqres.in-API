package api;

import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;


public class DeleteReqresInTest {
    private final static String URL_REQ_RES = "https://reqres.in/";
    @Test
    @Description("test attempt to delete user")
    public void testDelete() {
        Response response = given()
                .baseUri(URL_REQ_RES)
                .log().all()
                .when()
                .delete("api/users/2")
                .then().log().all()
                .statusCode(204)
                .extract().response();

        assertThat(response.statusCode()).isEqualTo(204);
        Assert.assertEquals(response.statusCode(), 204);
    }

}


