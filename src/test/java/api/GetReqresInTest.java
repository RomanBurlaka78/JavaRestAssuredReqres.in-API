package api;

import api.pojo.get.UserData;
import api.pojo.get.UserDataList;
import api.pojo.specification.Specifications;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class GetReqresInTest {
    private final static String URL_REQ_RES = "https://reqres.in/";

    @Test
    @Description("test attempt to receive list of users")
    public void testGetUsers() {
        Specifications.installSpec(Specifications.requestSpecification(URL_REQ_RES), Specifications.responseSpec200());

        List<UserData> user = (List<UserData>) given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList("data", UserData.class);

        user.stream().forEach(i -> Assert.assertTrue(i.getAvatar().contains(i.getId().toString())));
        assertThat(user).extracting("email").contains("michael.lawson@reqres.in");


        String total = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .statusCode(200)
                .extract().asString();

        assertThat(total).contains("total");
        assertThat(total).contains("page");
        assertThat(total).contains("per_page");
        assertThat(total).contains("total_pages");

    }

    @Test
    @Description("test attempt to receive a data from single user")
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
    @Description("test attempt to receive list of <resource>")
    public void testListResource() {
        List<UserDataList> user = (List<UserDataList>) given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL_REQ_RES + "api/unknown")
                .then().log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList("data", UserDataList.class);

        assertThat(user).extracting(UserDataList::getPantone_value).contains("15-4020");
        assertThat(user).extracting(UserDataList::getColor).contains("#98B2D1");
        assertThat(user).extracting(UserDataList::getName).contains("true red");
        assertThat(user).extracting(UserDataList::getYear).contains(2002);
    }

    @Test
    @Description("test attempt to receive an answer about single <resource>")
    public void testSingleResource() {
        String singleResource = given()
                .when().contentType(ContentType.JSON)
                .get(URL_REQ_RES + "api/unknown/2")
                .then().log().all()
                .extract().body().asString();

        assertThat(singleResource).contains("fuchsia rose");
    }

    @Test
    @Description("test attempt to receive answer from single <resource> not found")
    public void testSingleResourceNotFound() {
        Response singleResource = given()
                .when()
                .log().all()
                .baseUri(URL_REQ_RES)
                .contentType(ContentType.JSON)
                .get("api/unknown/23")
                .then().log().all()
                .statusCode(404)
                .extract().response();

        assertThat(singleResource.statusCode() == 404);
        Assert.assertEquals(singleResource.statusCode(), 404);
    }

    @Test
    @Description("test attempt get delayed response")
    public void testDelayedResponse() {
        Specifications.installSpec(Specifications.requestSpecification(URL_REQ_RES), Specifications.responseSpec200());
        UserData delayedResponse = given()
                .when()
                .log().all()
                .get("api/users?delay=3")
                .then().log().all()
                .extract().as(UserData.class);


        List<UserData> delayedResponseList = given()
                .when()
                .log().all()
                .get("api/users?delay=3")
                .then().log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList("data", UserData.class);

        assertThat(delayedResponseList).extracting("first_name").contains("Charles");


    }
}
