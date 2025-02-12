package api.tests;

import api.client.ScooterServiceClientOrderApi;
import api.models.OrderResponse;
import org.junit.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.apache.http.HttpStatus.*;


public class GetOrderTests {

    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";

    @Test
    public void getOrdersTest() {

        ScooterServiceClientOrderApi client = new ScooterServiceClientOrderApi(BASE_URI);

        List<OrderResponse> orderResponse = client
                .getOrders()
                .statusCode(SC_OK)
                .extract()
                .jsonPath().getList("orders", OrderResponse.class);


        step("Проверка ответа", () -> {

            assertThat(orderResponse, notNullValue());

            orderResponse.forEach(order -> {
                assertThat(order.getId(), greaterThan(0));
                assertThat(order.getTrack(), greaterThan(0));
            });
        });
    }
}
