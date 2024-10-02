package api;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class AutomationExerciseTest {
    private final static String URL = "https://automationexercise.com/api/";

    @Test
    public void testApiGetAllProductsList() {
        String response = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "productsList")
                .then()
                .statusCode(200)
                .extract().response()
                .getBody().asString();

      assertThat(response).contains("name");
    }


    @Test
    public void testRequestProductList() {
        String product = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "productsList")
                .then().log().body()
                .statusCode(200)
                .extract().body().asString();

        assertThat(product).contains("Tshirts");
    }
}



