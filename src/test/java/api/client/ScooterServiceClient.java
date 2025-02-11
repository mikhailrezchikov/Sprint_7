package api.client;

import api.models.Courier;
import api.models.Credentials;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ScooterServiceClient {

    private String baseURI;

    public ScooterServiceClient(String baseURI) {
        this.baseURI = baseURI;
    }

    private RequestSpecification requestSpec() {
        return given()
                .filter(new AllureRestAssured())
                .log().uri()
                .log().method()
                .log().body()
                .baseUri(baseURI)
                .header("Content-Type", "application/json");
    }

    @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return requestSpec()
                .body(courier)
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .log().body();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(Credentials credentials) {
        return requestSpec()
                .body(credentials)
                .post("/api/v1/courier/login")
                .then()
                .log().body();
    }

    @Step("Получение заказов")
    public ValidatableResponse getOrders() {
        return requestSpec()
                .queryParam("limit", "2")
                .get("/api/v1/orders")
                .then()
                .log().body();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(String id) {

        return requestSpec()
                .delete("/api/v1/courier/" + id)
                .then()
                .log().body();
    }

    @Step
    public ValidatableResponse cancelOrderByTrackId(int trackId) {
        return requestSpec()
                .put("/api/v1/orders/cancel?track=" + trackId)
                .then()
                .log().body();
    }

}
