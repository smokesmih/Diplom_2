import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@DisplayName("Проверки авторизации клиента")
public class LoginUserTest {
    private User user;
    Faker faker = new Faker();

    @Before
    public  void  setup(){
        user = new User(faker.internet().emailAddress(), faker.internet().password(), String.valueOf(faker.name()));
    }

    @DisplayName("Проверка успешного логина клиента")
    @Test
    public void positiveLoginTest() {
        UsersMethods.createUser(user);
        Response response = UsersMethods.loginUser(user);
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При авторизации клиента статус Код отличается от 200", 200, statusCode);
        String body = response.then().extract().jsonPath().getString("success");
        Assert.assertEquals("Тело ответа при авторизации клиента отличается от ожидаемого", "true", body);
    }
    @DisplayName("Проверка авторизации клиента с неверным email")
    @Test
    public void loginWithWrongEmailTest() {
        UsersMethods.createUser(user);
        Response response = UsersMethods.loginUser(Credential.getDataForLoginWithWrongEmail(user));
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При  неуспешной авторизации клиента статус Код отличается от 401", 401, statusCode);
        String body = response.then().extract().jsonPath().getString("message");
        Assert.assertEquals("Тело ответа при авторазации ,клиента c неверным email, отличается от ожидаемого", "email or password are incorrect", body);
    }
    @DisplayName("Проверка авторизации клиента с неверным password")
    @Test
    public void loginWithWrongPasswordTest() {
        UsersMethods.createUser(user);
        Response response = UsersMethods.loginUser(Credential.getDataForLoginWithWrongPassword(user));
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При  неуспешной авторизации клиента статус Код отличается от 401", 401, statusCode);
        String body = response.then().extract().jsonPath().getString("message");
        Assert.assertEquals("Тело ответа при авторазации ,клиента c неверным password, отличается от ожидаемого", "email or password are incorrect", body);
    }
    @After
    public void deleteTestsClients() {
        UsersMethods.deleteUser(UsersMethods.getAuthToken(user));
    }

}
