package api.tests;

import api.models.Courier;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTests extends CourierTestBase {

    @Test
    public void createCourierSuccessfulTest() {

        ValidatableResponse createCourierResponse = step("Создание курьера", () -> given()
                .filter(new AllureRestAssured())
                .log().uri()
                .log().method()
                .log().body()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(courier)
                .post("/api/v1/courier")
                .then()
                .log().body());

        step("Проверка ответа", () -> {
            createCourierResponse.assertThat()
                    .statusCode(201)
                    .body("ok", equalTo(true));
        });
    }

    @Test
    public void shouldNotCreateDuplicateCourierTest() {

        step("Создание курьера", () -> given()
                .filter(new AllureRestAssured())
                .log().uri()
                .log().method()
                .log().body()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(courier)
                .post("/api/v1/courier")
                .then()
                .log().body());

        Courier duplicateCourier = courier;

        ValidatableResponse createCourierResponse = step("Создание курьера с логином, который уже есть в базе", () -> given()
                .filter(new AllureRestAssured())
                .log().uri()
                .log().method()
                .log().body()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(duplicateCourier)
                .post("/api/v1/courier")
                .then()
                .log().body());


        step("Проверка ответа", () -> {
            createCourierResponse.assertThat()
                    .statusCode(409)
                    .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        });
    }

    @Test
    public void shouldNotCreateCourierWithoutLoginTest() {

        Courier courierWithoutLogin = new Courier(
                null,
                courier.getPassword(),
                courier.getFirstName()
        );

        ValidatableResponse createCourierResponse = step("Создание курьера", () -> given()
                .filter(new AllureRestAssured())
                .log().uri()
                .log().method()
                .log().body()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(courierWithoutLogin)
                .post("/api/v1/courier")
                .then()
                .log().body());

        step("Проверка ответа", () -> {
            createCourierResponse.assertThat()
                    .statusCode(400)
                    .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        });
    }

    @Test
    public void shouldNotCreateCourierWithoutPasswordTest() {

        Courier courierWithoutPassword = new Courier(
                courier.getLogin(),
                null,
                courier.getFirstName()
        );

        ValidatableResponse createCourierResponse = step("Создание курьера", () -> given()
                .filter(new AllureRestAssured())
                .log().uri()
                .log().method()
                .log().body()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(courierWithoutPassword)
                .post("/api/v1/courier")
                .then()
                .log().body());

        step("Проверка ответа", () -> {
            createCourierResponse.assertThat()
                    .statusCode(400)
                    .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        });
    }
}
