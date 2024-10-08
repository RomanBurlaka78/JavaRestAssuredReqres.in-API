package api;

import api.pojo.post.PostRegisterUser;
import api.pojo.post.ResponsePost;
import api.pojo.post.ResponsePostError;
import api.pojo.post.ResponsePostLogin;
import api.pojo.specification.Specifications;
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
    @Description("test attempt to post a data - create")
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
    @Description("test attempt to post with Pojo class - register")
    public void testCreatePostPojo() {
        PostRegisterUser createPostUser = new PostRegisterUser("eve.holt@reqres.in", "pistol");
        Specifications.installSpec(Specifications.requestSpecification(URL_REQ_RES),
                Specifications.responseSpec200());

        ResponsePost response = given()
                .log().all()
                .when()
                .body(createPostUser)
                .post("api/register")
                .then().log().all()
                .statusCode(200)
                .extract().as(ResponsePost.class);

        assertThat(response.getId() == 4);
        assertThat(response.getToken() == "QpwL5tke4Pnpja7X4");
        Assert.assertEquals(response.getId(), 4);
        Assert.assertEquals(response.getToken(), "QpwL5tke4Pnpja7X4");
    }

    @Test
    @Description("test attempt to post with Pojo class - register unsuccessful")
    public void testRegisterUnsuccessful() {
        PostRegisterUser createPostUser = new PostRegisterUser("sydney@fife", "");
        Specifications.installSpec(Specifications.requestSpecification(URL_REQ_RES),
                Specifications.responseSpec400());

        ResponsePostError response = given()
                .log().all()
                .when()
                .body(createPostUser)
                .post("api/register")
                .then().log().all()
                .extract().as(ResponsePostError.class);

        assertThat(response.getError() == "Missing password");

    }

    @Test
    @Description("test attempt to post with Pojo class - login")
    public void testLoginSuccessful() {
        PostRegisterUser createPostUser = new PostRegisterUser("eve.holt@reqres.in", "cityslicka");
        Specifications.installSpec(Specifications.requestSpecification(URL_REQ_RES),
                Specifications.responseSpec200());

        ResponsePostLogin response = given()
                .log().all()
                .when()
                .body(createPostUser)
                .post("api/login")
                .then().log().all()
                .extract().as(ResponsePostLogin.class);

        assertThat(response.getToken() == "QpwL5tke4Pnpja7X4");

    }



    @Test
    @Description("test attempt to post with Pojo class - login unsuccessful")
    public void testLoginUnSuccessFul() {
        PostRegisterUser createPostUser = new PostRegisterUser("peter@klaven", "");
        Specifications.installSpec(Specifications.requestSpecification(URL_REQ_RES),
                Specifications.responseSpec400());

        ResponsePostError response = given()
                .log().all()
                .when()
                .body(createPostUser)
                .post("api/login")
                .then().log().all()
                .statusCode(400)
                .extract().as(ResponsePostError.class);

        assertThat(response.getError() == "Missing password");

    }

}
