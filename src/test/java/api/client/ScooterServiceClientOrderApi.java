package api.client;

import api.specs.ApiSpecs;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.Map;

public class ScooterServiceClientOrderApi {

    private String baseURI;
    private final static String ORDERS_ENDPOINT = "/api/v1/orders";
    private final static String CANCEL_ORDER_ENDPOINT = "/api/v1/orders/cancel?track=";

    public ScooterServiceClientOrderApi(String baseURI) {
        this.baseURI = baseURI;
    }

    @Step("Получение заказов")
    public ValidatableResponse getOrders() {
        return ApiSpecs.requestSpec(baseURI)
                .queryParam("limit", "2")
                .get(ORDERS_ENDPOINT)
                .then()
                .log().body();
    }

    @Step("Отмена заказа по TrackId")
    public ValidatableResponse cancelOrderByTrackId(int trackId) {
        return ApiSpecs.requestSpec(baseURI)
                .put(CANCEL_ORDER_ENDPOINT + trackId)
                .then()
                .log().body();
    }

    @Step("Создание заказов")
    public ValidatableResponse createOrders(Map<String, Object> Data) {
        return ApiSpecs.requestSpec(baseURI)
                .body(Data)
                .post(ORDERS_ENDPOINT)
                .then()
                .log().body();
    }
}
