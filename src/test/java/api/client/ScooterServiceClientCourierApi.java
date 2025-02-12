package api.client;

import api.models.Courier;
import api.models.Credentials;
import api.specs.ApiSpecs;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;


public class ScooterServiceClientCourierApi {

    private String baseURI;
    private final static String CREATE_COURIER_ENDPOINT = "/api/v1/courier";
    private final static String LOGIN_COURIER_ENDPOINT = "/api/v1/courier/login";
    private final static String DELETE_COURIER_ENDPOINT = "/api/v1/courier/";

    public ScooterServiceClientCourierApi(String baseURI) {
        this.baseURI = baseURI;
    }

    @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return ApiSpecs.requestSpec(baseURI)
                .body(courier)
                .post(CREATE_COURIER_ENDPOINT)
                .then()
                .log().body();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(Credentials credentials) {
        return ApiSpecs.requestSpec(baseURI)
                .body(credentials)
                .post(LOGIN_COURIER_ENDPOINT)
                .then()
                .log().body();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(String id) {
        return ApiSpecs.requestSpec(baseURI)
                .delete(DELETE_COURIER_ENDPOINT + id)
                .then()
                .log().body();
    }
}
