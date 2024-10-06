import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

@DisplayName("Проверка создания клиента")
public class CreateUserTest {
    private Faker faker = new Faker();
    private User user;

    @After
    public void deleteTestsClients() {
        UsersMethods.deleteUser(UsersMethods.getAuthToken(user));
    }

    @DisplayName("Проверка создания валидного клиента")
    @Test
    public void createUserPositiveTest() {
        user = new User(faker.internet().emailAddress(), faker.internet().password(), String.valueOf(faker.name()));
        Response response = UsersMethods.createUser(user);
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При регистрации клиента статус Код отличается от 200", 200, statusCode);
        String body = response.then().extract().jsonPath().getString("success");
        Assert.assertEquals("Тело ответа при создание клиента отличается от ожидаемого", "true", body);
    }

    @DisplayName("Проверка создания уже существующего клиента")
    @Test
    public void createUserTwice() {
        user = new User(faker.internet().emailAddress(), faker.internet().password(), String.valueOf(faker.name()));
        UsersMethods.createUser(user);
        Response response = UsersMethods.createUser(user);
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При регистрации, уже существующего клиента, клиента статус Код отличается от 403", 403, statusCode);
        String body = response.then().extract().jsonPath().getString("message");
        Assert.assertEquals("Тело ответа при создание, уже существующего клиента, отличается от ожидаемого", "User already exists", body);
    }

    @DisplayName("Проверка создания клиента без поля email")
    @Test
    public void createUserWithoutEmail() {
        user = new User(faker.internet().password(), String.valueOf(faker.name()));
        Response response = UsersMethods.createUser(user);
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При регистрации клиента, без поля email, статус Код отличается от 403", 403, statusCode);
        String body = response.then().extract().jsonPath().getString("message");
        Assert.assertEquals("Тело ответа при создание,клиента без поля email, отличается от ожидаемого", "Email, password and name are required fields", body);
    }

    @DisplayName("Проверка создания клиента без поля password")
    @Test
    public void createUserWithoutPassword() {
        user = new User(faker.internet().emailAddress(), Optional.ofNullable(null), String.valueOf(faker.name()));
        Response response = UsersMethods.createUser(user);
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При регистрации клиента, без поля password, статус Код отличается от 403", 403, statusCode);
        String body = response.then().extract().jsonPath().getString("message");
        Assert.assertEquals("Тело ответа при создание,клиента без поля password, отличается от ожидаемого", "Email, password and name are required fields", body);
    }

    @DisplayName("Проверка создания клиента без поля name")
    @Test
    public void createUserWithoutName() {
        user = new User(faker.internet().emailAddress(), faker.internet().password(), Optional.ofNullable(null));
        Response response = UsersMethods.createUser(user);
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При регистрации клиента, без поля name, статус Код отличается от 403", 403, statusCode);
        String body = response.then().extract().jsonPath().getString("message");
        Assert.assertEquals("Тело ответа при создание,клиента без поля name, отличается от ожидаемого", "Email, password and name are required fields", body);
    }

}
