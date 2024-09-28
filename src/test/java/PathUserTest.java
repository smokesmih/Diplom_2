import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.aspectj.weaver.ast.Var;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

@DisplayName("Проверка обновления данных клиента")
public class PathUserTest {
    private User user;
    Faker faker = new Faker();

    @Before
    public void setup() {
        user = new User(faker.internet().emailAddress(), faker.internet().password(), String.valueOf(faker.name()));
    }

    @Test
    @DisplayName("Проверка обновления поля email с авторизацией")
    public void autPathEmail() {
        UsersMethods.createUser(user);
        String authToken = UsersMethods.getAuthToken(user);
        Response response = UsersMethods.changeUser(Credential.getDataForPathEmail(user), authToken);
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При изменение  email клиента статус Код отличается от 200", 200, statusCode);
        Map<Var, Var> body = response.then().extract().jsonPath().getJsonObject("user");
        Assert.assertNotNull(body);
        UsersMethods.deleteUser(authToken);
    }

    @Test
    @DisplayName("Проверка обновления поля password с авторизацией")
    public void autPathPassword() {
        UsersMethods.createUser(user);
        String authToken = UsersMethods.getAuthToken(user);
        Response response = UsersMethods.changeUser(Credential.getDataForPathPassword(user), authToken);
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При изменение  password клиента статус Код отличается от 200", 200, statusCode);
        Map<Var, Var> body = response.then().extract().jsonPath().getJsonObject("user");
        Assert.assertNotNull(body);
        UsersMethods.deleteUser(authToken);
    }

    @Test
    @DisplayName("Проверка обновления поля name с авторизацией")
    public void autPathName() {
        UsersMethods.createUser(user);
        String authToken = UsersMethods.getAuthToken(user);
        Response response = UsersMethods.changeUser(Credential.getDataForPathName(user), authToken);
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При изменение  name клиента статус Код отличается от 200", 200, statusCode);
        Map<Var, Var> body = response.then().extract().jsonPath().getJsonObject("user");
        Assert.assertNotNull(body);
        UsersMethods.deleteUser(authToken);
    }

    @Test
    @DisplayName("Проверка обновления поля email без авторизации")
    public void withoutAutPathEmail() {
        UsersMethods.createUser(user);
        Response response = UsersMethods.changeUser(Credential.getDataForPathEmail(user));
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При изменение  email клиента без авторизации статус Код отличается от 401", 401, statusCode);
        String body = response.then().extract().jsonPath().getString("message");
        Assert.assertEquals("При изменение  email, клиента без авторизации, неверное тело ответа", "You should be authorised", body);
        UsersMethods.deleteUser(UsersMethods.getAuthToken(user));
    }

    @Test
    @DisplayName("Проверка обновления поля password без авторизации")
    public void withoutAutPathPassword() {
        UsersMethods.createUser(user);
        Response response = UsersMethods.changeUser(Credential.getDataForPathPassword(user));
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При изменение  password клиента без авторизации статус Код отличается от 401", 401, statusCode);
        String body = response.then().extract().jsonPath().getString("message");
        Assert.assertEquals("При изменение  password, клиента без авторизации, неверное тело ответа", "You should be authorised", body);
        UsersMethods.deleteUser(UsersMethods.getAuthToken(user));
    }

    @Test
    @DisplayName("Проверка обновления поля name без авторизации")
    public void withoutAutPathName() {
        UsersMethods.createUser(user);
        Response response = UsersMethods.changeUser(Credential.getDataForPathName(user));
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При изменение  name клиента без авторизации статус Код отличается от 401", 401, statusCode);
        String body = response.then().extract().jsonPath().getString("message");
        Assert.assertEquals("При изменение  name, клиента без авторизации, неверное тело ответа", "You should be authorised", body);
        UsersMethods.deleteUser(UsersMethods.getAuthToken(user));
    }
}
