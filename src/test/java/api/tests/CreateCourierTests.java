package api.tests;

import api.models.Courier;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.apache.http.HttpStatus.*;

public class CreateCourierTests extends CourierTestBase {

    @Test
    public void createCourierSuccessfulTest() {

        ValidatableResponse createCourierResponse = client.createCourier(courier);

        step("Проверка ответа", () -> {
            createCourierResponse.assertThat()
                    .statusCode(SC_CREATED)
                    .body("ok", equalTo(true));
        });
    }

    @Test
    public void shouldNotCreateDuplicateCourierTest() {

        client.createCourier(courier);
        ValidatableResponse createDuplicateCourierResponse = client.createCourier(courier);

        step("Проверка ответа", () -> {
            createDuplicateCourierResponse.assertThat()
                    .statusCode(SC_CONFLICT)
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

        ValidatableResponse createCourierResponse = client.createCourier(courierWithoutLogin);

        step("Проверка ответа", () -> {
            createCourierResponse.assertThat()
                    .statusCode(SC_BAD_REQUEST)
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

        ValidatableResponse createCourierResponse = client.createCourier(courierWithoutPassword);

        step("Проверка ответа", () -> {
            createCourierResponse.assertThat()
                    .statusCode(SC_BAD_REQUEST)
                    .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        });
    }
}
