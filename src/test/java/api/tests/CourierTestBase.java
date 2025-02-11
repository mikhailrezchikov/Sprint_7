package api.tests;

import api.client.ScooterServiceClient;
import api.data.TestData;
import api.models.Courier;
import api.models.Credentials;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;

import static io.qameta.allure.Allure.step;

public class CourierTestBase {
    protected Courier courier;
    protected int courierId;
    protected static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";
    protected final ScooterServiceClient client = new ScooterServiceClient(BASE_URI);

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
            if (loginCourierResponse.extract().statusCode() == 200) {
                courierId = loginCourierResponse.extract().path("id");
                client.deleteCourier(String.valueOf(courierId));
            } else {
                return;
            }
        });
    }
}
