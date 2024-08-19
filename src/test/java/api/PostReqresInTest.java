package api;

import api.pojo.post.PostRegisterUser;
import api.pojo.post.ResponsePost;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class PostReqresInTest {
    private final static String URL_REQ_RES = "https://reqres.in/";
    @Test
    @Description("test attempt to post a data")
    public void testCreatePost() {
        String postUserData = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        Response createPost = given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .body(postUserData)
                .post(URL_REQ_RES + "api/users")
                .then().log().all()
                .statusCode(201)
                .extract().response();

        assertThat(createPost.statusCode() == 201);
    }

    @Test
    @Description("test attempt to post with Pojo class")
    public void testCreatePostPojo() {
        PostRegisterUser createPostUser = new PostRegisterUser("eve.holt@reqres.in", "pistol");

        ResponsePost response = given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .body(createPostUser)
                .post("https://reqres.in/api/register")
                .then().log().all()
                .statusCode(200)
                .extract().as(ResponsePost.class);

        assertThat(response.getId() == 4);
        assertThat(response.getToken() == "QpwL5tke4Pnpja7X4");
        Assert.assertEquals(response.getId(), 4);
        Assert.assertEquals(response.getToken(), "QpwL5tke4Pnpja7X4");
    }
}
