package api.tests;

import api.client.ScooterServiceClientOrderApi;
import api.data.TestData;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;
import java.util.Map;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.*;


@RunWith(Parameterized.class)
public class CreateOrderTests {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final int metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> color;
    protected static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";
    private int trackId;
    protected final ScooterServiceClientOrderApi client = new ScooterServiceClientOrderApi(BASE_URI);

    public CreateOrderTests(String firstName, String lastName, String address, int metroStation,
                            String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][] {
                {new TestData().firstName, new TestData().lastName, new TestData().address, new TestData().metroStation,
                        new TestData().phone, new TestData().rentTime, new TestData().deliveryDate, new TestData().comment, List.of("BLACK")},
                {new TestData().firstName, new TestData().lastName, new TestData().address, new TestData().metroStation,
                        new TestData().phone, new TestData().rentTime, new TestData().deliveryDate, new TestData().comment, List.of("GREY")},
                {new TestData().firstName, new TestData().lastName, new TestData().address, new TestData().metroStation,
                        new TestData().phone, new TestData().rentTime, new TestData().deliveryDate, new TestData().comment, List.of("GREY", "BLACK")},
                {new TestData().firstName, new TestData().lastName, new TestData().address, new TestData().metroStation,
                        new TestData().phone, new TestData().rentTime, new TestData().deliveryDate, new TestData().comment, List.of()}
        };
    }

    private Map<String, Object> getOrdersData() {
        return Map.of(
                "firstName", firstName,
                "lastName", lastName,
                "address", address,
                "metroStation", metroStation,
                "phone", phone,
                "rentTime", rentTime,
                "deliveryDate", deliveryDate,
                "comment", comment,
                "color", color
        );
    }

    @Test
    public void createOrderSuccessfulTest() {

        ValidatableResponse createOrderResponse = client.createOrders(getOrdersData());

        step("Проверка ответа", () -> {
            createOrderResponse.assertThat()
                    .statusCode(SC_CREATED)
                    .body("track", is(notNullValue()));

            trackId = createOrderResponse.extract().path("track");
        });
    }

    @After
    public void cancelOrder() {
        if (trackId != 0) {
            step("Отмена заказа", () -> client.cancelOrderByTrackId(trackId));
        }
    }
}
