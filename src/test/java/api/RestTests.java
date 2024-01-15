package api;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;

public class RestTests {

    @Test
    void reqresCreateUsersTest() {
        String body = "{ \"name\": \"Ivan\", \"job\": \"QA_Automation\" }";

        given()
                .log().uri()
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Ivan"))
                .body("job", is("QA_Automation"));

    }

    @Test
    void reqresUpdateUsersTest() {
        String body = "{ \"name\": \"Petr\", \"job\": \"Developer\" }";

        given()
                .log().uri()
                .body(body)
                .contentType(JSON)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("Petr"))
                .body("job", is("Developer"));
    }
    @Test
    void reqresUnSuccessfulLoginUsersTest() {
        String requestBody = "{\"email\": \"test@mail.ru\", \"password\": \"\" }";
        given()
                .log().uri()
                .body(requestBody)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));

    }

    @Test
    void reqresSuccessfulLoginUsersTest() {
        String requestBody = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";
        given()
                .log().uri()
                .body(requestBody)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemes/successfulLogin.json"));
    }

    @Test
    void reqresSingleUserNotFoundTest() {
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }
}