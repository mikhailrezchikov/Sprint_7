package api.tests;

import api.client.ScooterServiceClientCourierApi;
import api.data.TestData;
import api.models.Courier;
import api.models.Credentials;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import static org.apache.http.HttpStatus.*;
import static io.qameta.allure.Allure.step;

public class CourierTestBase {
    protected Courier courier;
    protected int courierId;
    protected static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";
    protected final ScooterServiceClientCourierApi client = new ScooterServiceClientCourierApi(BASE_URI);

    @Before
    public void before() {
        TestData testData = new TestData();
        courier = new Courier(
                testData.login,
                testData.password,
                testData.firstName
        );
    }

    @After
    public void after() {

        step("Логин и удаление курьера если он был создан", () -> {
            Credentials credentials = Credentials.fromCourier(courier);
            ValidatableResponse loginCourierResponse = client.loginCourier(credentials);
            if (loginCourierResponse.extract().statusCode() == SC_OK) {
                courierId = loginCourierResponse.extract().path("id");
                client.deleteCourier(String.valueOf(courierId));
            } else {
                return;
            }
        });
    }
}
