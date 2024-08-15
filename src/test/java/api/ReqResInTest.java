package api;

import api.pojo.UserData;
import api.pojo.UserDataList;
import api.pojo.UserDataRoot;
import io.restassured.http.ContentType;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;


public class ReqResInTest {
    private final static String URL_REQ_RES = "https://reqres.in/";


    @Test
    public void testRequestListUsers() {
        List<UserData> user = (List<UserData>) given()
                .when()
                .contentType(ContentType.JSON)
                .get("https://reqres.in/api/users?page=2")
                .then().log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList("data", UserData.class);

        user.stream().forEach(i -> Assert.assertTrue(i.getAvatar().contains(i.getId().toString())));
        assertThat(user).extracting("email").contains("michael.lawson@reqres.in");


        String total =  given()
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
    public void testSingleUser() {
        String listSingleUser = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL_REQ_RES + "api/users/2")
                .then().log().all()
                .statusCode(200)
                .extract().body().asString();
    }

    @Test
    public void testSingleUserNotFound() {
        List<UserData> user = (List<UserData>) given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL_REQ_RES + "api/users/23")
                .then().log().all()
                .statusCode(404)
                .extract().response().jsonPath().getList("data", UserData.class);
    }

    @Test
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
    public void testSingleResource() {
        String singleResource = given()
                .when().contentType(ContentType.JSON)
                .get(URL_REQ_RES + "api/unknow/2")
                .then().log().all()
                .extract().body().asString();

        assertThat(singleResource).contains("fuchsia rose");
    }


}


