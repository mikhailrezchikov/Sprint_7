package api.data;

import com.github.javafaker.Faker;

import java.security.SecureRandom;

public class TestData {

    Faker faker = new Faker();
    SecureRandom random = new SecureRandom();

    public String login = faker.internet().emailAddress();
    public String firstName = faker.name().firstName();
    public String password = faker.number().digits(10);
    public String lastName = faker.name().lastName();
    public String address = faker.address().fullAddress();
    public int metroStation = random.nextInt(10);
    public String phone = faker.phoneNumber().cellPhone();
    public int rentTime = random.nextInt(10);
    public String deliveryDate = faker.date().future(10, java.util.concurrent.TimeUnit.DAYS).toString();
    public String comment = faker.chuckNorris().fact();

}
