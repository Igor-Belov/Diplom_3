package site.nomoreparties.stellarburgers;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class StaffApi {
    public static String BASE_URL = "https://stellarburgers.nomoreparties.site";

    @Step("StaffApi - действие, создаем пользователя через API и возвращаем accessToken")
    public static String createUser(String email, String password, String name) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(Map.of("email", email, "password", password, "name", name))
                .when()
                .post(BASE_URL + "/api/auth/register")
                .then().log().all()
                .extract()
                .path("accessToken");
    }

    @Step("StaffApi - действие, получаем accessToken залогинившись через API")
    public static String getAccessToken(String email, String password) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(Map.of( "password", password, "email", email))
                .when()
                .post(BASE_URL + "/api/auth/login")
                .then().log().all()
                .extract()
                .path("accessToken");
    }

    @Step("StaffApi - действие, получаем все Token залогинившись через API")
    public static Map<String, String> getTokensAndLogin(String email, String password) {
        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .body(Map.of("password", password, "email", email))
                .when()
                .post(BASE_URL + "/api/auth/login")
                .then().log().all()
                .extract()
                .response();
        String accessToken = response.path("accessToken");
        String refreshToken = response.path("refreshToken");
        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    @Step("StaffApi - действие, удаление пользователя")
    public static ValidatableResponse deleteUser(String accessToken) {
        return given()
                .auth().oauth2(accessToken)
                .when()
                .delete(BASE_URL + "/api/auth/user")
                .then()
                .log().all();
    }
}
