import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UsersMethods {
    private static final String STELLAR_BURGERS = "https://stellarburgers.nomoreparties.site";

    @Step("Создание валидного пользователя")
    public static Response createUser(User user) {
        return given()
                .baseUri(STELLAR_BURGERS)
                .body(user)
                .header("Content-Type", "application/json")
                .post("/api/auth/register");
    }

    @Step("Позитивный логин пользователя")
    public static Response loginUser(User user) {
        return given()
                .baseUri(STELLAR_BURGERS)
                .body(user)
                .header("Content-Type", "application/json")
                .post("/api/auth/login");
    }

    @Step("Авторизация пользователя с невалидными данными")
    public static Response loginUser(Credential credential) {
        return given()
                .baseUri(STELLAR_BURGERS)
                .body(credential)
                .header("Content-Type", "application/json")
                .post("/api/auth/login");
    }

    @Step("Получение токена авторизации")
    public static String getAuthToken(User user) {
        String tokenForm = loginUser(user).then().extract().jsonPath().getString("accessToken");
        return tokenForm.split(" ")[1];
    }

    @Step("Изменение данных  пользователя  с авторизацией")
    public static Response changeUser(Credential credential,String authToken) {
        return given()
                .baseUri(STELLAR_BURGERS)
                .auth().oauth2(authToken)
                .body(credential)
                .header("Content-Type", "application/json")
                .patch("/api/auth/user");
    }

    @Step("Изменение данных  пользователя без авторизации")
    public static Response changeUser(Credential credential) {
        return given()
                .baseUri(STELLAR_BURGERS)
                .body(credential)
                .header("Content-Type", "application/json")
                .patch("/api/auth/user");
    }
    @Step("Удаление пользователя")
    public static Response deleteUser(String authToken) {
        return given()
                .baseUri(STELLAR_BURGERS)
                .auth().oauth2(authToken)
                .header("Content-Type", "application/json")
                .delete("/api/auth/user");
    }


}
