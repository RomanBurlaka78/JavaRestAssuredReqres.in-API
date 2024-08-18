package api;

import api.pojo.*;
import io.restassured.http.ContentType;

import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;


public class ReqResInTest {
    private final static String URL_REQ_RES = "https://reqres.in/";

    @Test
    @Description("test attempt to receive list of users")
    public void testGetUsers() {
        List<UserData> user = (List<UserData>) given()
                .when()
                .contentType(ContentType.JSON)
                .get("https://reqres.in/api/users?page=2")
                .then().log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList("data", UserData.class);

        user.stream().forEach(i -> Assert.assertTrue(i.getAvatar().contains(i.getId().toString())));
        assertThat(user).extracting("email").contains("michael.lawson@reqres.in");


        String total = given()
                .when()
                .contentType(ContentType.JSON)
                .get("https://reqres.in/api/users?page=2")
                .then().log().all()
                .statusCode(200)
                .extract().asString();

        assertThat(total).contains("total");
        assertThat(total).contains("page");
        assertThat(total).contains("per_page");
        assertThat(total).contains("total_pages");

    }

    @Test
    @Description("test attempt to receive a data from single of user")
    public void testGetSingleUser() {
        Response listSingleUser = given()
                .baseUri(URL_REQ_RES)
                .when()
                .contentType(ContentType.JSON)
                .get("api/users/2")
                .then().log().all()
                .statusCode(200)
                .extract().response();

        Assert.assertEquals(listSingleUser.asString().contains("first_name"), true);
        Assert.assertEquals(listSingleUser.asString().contains("Janet"), true);
    }

    @Test
    @Description("test attempt to receive an answer about not existing user")
    public void testSingleUserNotFound() {
        UserData user = given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .get(URL_REQ_RES + "api/users/23")
                .then().log().all()
                .statusCode(404)
                .extract().as(UserData.class);

        assertThat(user.getAvatar() == null);
        assertThat(user.getFirst_name() == null);
        assertThat(user.getLast_name() == null);
        assertThat(user.getEmail() == null);
        assertThat(user.getId() == null);
    }

    @Test
    @Description("test attempt to receive list of users")
    public void testListResource() {
        List<UserDataList> user = (List<UserDataList>) given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL_REQ_RES + "api/unknow")
                .then().log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList("data", UserDataList.class);

        assertThat(user).extracting(UserDataList::getPantone_value).contains("15-4020");
        assertThat(user).extracting(UserDataList::getColor).contains("#98B2D1");
        assertThat(user).extracting(UserDataList::getName).contains("true red");
        assertThat(user).extracting(UserDataList::getYear).contains(2002);
    }

    @Test
    @Description("test attempt to receive an answer about single user")
    public void testSingleResource() {
        String singleResource = given()
                .when().contentType(ContentType.JSON)
                .get(URL_REQ_RES + "api/unknow/2")
                .then().log().all()
                .extract().body().asString();

        assertThat(singleResource).contains("fuchsia rose");
    }

    @Test
    @Description("test attempt to receive answer from unknown user")
    public void testSingleResourceNotFound() {
        Response singleResource = given()
                .when()
                .log().all()
                .baseUri(URL_REQ_RES)
                .contentType(ContentType.JSON)
                .get("api/unknow/23")
                .then().log().all()
                .statusCode(404)
                .extract().response();

        assertThat(singleResource.statusCode() == 404);
        Assert.assertEquals(singleResource.statusCode(), 404);
    }

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


