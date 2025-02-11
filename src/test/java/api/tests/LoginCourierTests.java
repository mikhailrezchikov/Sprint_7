package api.tests;

import api.models.Credentials;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.security.SecureRandom;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTests extends CourierTestBase {

    @Test
    public void successfulCourierAuthorizeTest() {
        client.createCourier(courier);

        Credentials credentials = new Credentials(
                courier.getLogin(),
                courier.getPassword()
        );

        ValidatableResponse loginCourierResponse = client.loginCourier(credentials);

        step("Проверка ответа", () -> {
            loginCourierResponse.assertThat()
                    .statusCode(200)
                    .body("id", notNullValue());
        });
    }

    @Test
    public void shouldNotAuthorizeCourierWithoutLoginTest() {
        client.createCourier(courier);

        Credentials credentials = new Credentials(
                null,
                courier.getPassword()
        );

        ValidatableResponse loginCourierResponse = client.loginCourier(credentials);

        step("Проверка ответа", () -> {
            loginCourierResponse.assertThat()
                    .statusCode(400)
                    .body("message", equalTo("Недостаточно данных для входа"));
        });
    }

    @Test
    public void shouldNotAuthorizeCourierWithoutPasswordTest() {
        client.createCourier(courier);

        Credentials credentials = new Credentials(
                courier.getLogin(),
                ""
        );

        ValidatableResponse loginCourierResponse = client.loginCourier(credentials);

        step("Проверка ответа", () -> {
            loginCourierResponse.assertThat()
                    .statusCode(400)
                    .body("message", equalTo("Недостаточно данных для входа"));
        });
    }

    @Test
    public void shouldNotAuthorizeCourierWithIncorrectPasswordTest() {
        client.createCourier(courier);

        SecureRandom random = new SecureRandom();

        Credentials credentials = new Credentials(
                courier.getLogin(),
                courier.getPassword() + random.nextInt(100)
        );

        ValidatableResponse loginCourierResponse = client.loginCourier(credentials);

        step("Проверка ответа", () -> {
            loginCourierResponse.assertThat()
                    .statusCode(404)
                    .body("message", equalTo("Учетная запись не найдена"));
        });
    }

    @Test
    public void shouldNotAuthorizeCourierWithIncorrectLoginTest() {
        client.createCourier(courier);

        SecureRandom random = new SecureRandom();

        Credentials credentials = new Credentials(
                courier.getLogin() + random.nextInt(100),
                courier.getPassword()
        );

        ValidatableResponse loginCourierResponse = client.loginCourier(credentials);

        step("Проверка ответа", () -> {
            loginCourierResponse.assertThat()
                    .statusCode(404)
                    .body("message", equalTo("Учетная запись не найдена"));
        });
    }
}
