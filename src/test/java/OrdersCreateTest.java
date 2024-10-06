import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Проверка создания заказов")
public class OrdersCreateTest {
    private List<String> ingredientsList = OrdersMethods.getIngredientsList();
    private User user;
    private Faker faker = new Faker();

    @Before
    public void setUp() {
        user = new User(faker.internet().emailAddress(), faker.internet().password(), String.valueOf(faker.name()));
    }

    @DisplayName("Позитивная проверка создания заказа (с авторизацией и набором ингридиентов)")
    @Test
    public void createOrder() {
        IngredientsForOrder ingredientsForOrder = new IngredientsForOrder(OrdersMethods.getRandomIngredients(ingredientsList));
        UsersMethods.createUser(user);
        String authToken = UsersMethods.getAuthToken(user);
        Response response = OrdersMethods.createOrder(ingredientsForOrder, authToken);
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При создание заказа статус Код отличается от 200", 200, statusCode);
        String body = response.then().extract().jsonPath().getString("success");
        Assert.assertEquals("Тело ответа при авторизации клиента отличается от ожидаемого", "true", body);
    }

    @DisplayName("Проверка создания заказа без авторизации")
    @Test
    public void createOrderWihtoutAuth() {
        IngredientsForOrder ingredientsForOrder = new IngredientsForOrder(OrdersMethods.getRandomIngredients(ingredientsList));
        UsersMethods.createUser(user);
        Response response = OrdersMethods.createOrderWithoutAuth(ingredientsForOrder);
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При попытке заказа  без авторизации статус Код отличается от 200", 200, statusCode);
        String body = response.then().extract().jsonPath().getString("success");
        Assert.assertEquals("При попытке заказа  без авторизации неверное тело ответа", "true", body);
    }

    @DisplayName("Проверка создания заказа без ингридиентов")
    @Test
    public void createOrderWithoutIngredients() {
        UsersMethods.createUser(user);
        String authToken = UsersMethods.getAuthToken(user);
        Response response = OrdersMethods.createOrder(new IngredientsForOrder(new ArrayList<>()), authToken);
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При при создании заказа без ингридиентов статус Код отличается от 400", 400, statusCode);
        String body = response.then().extract().jsonPath().getString("message");
        Assert.assertEquals("Тело ответа при создании заказа без ингридиентов отличается от ожидаемого", "Ingredient ids must be provided", body);
    }

    @DisplayName("Проверка создания заказа c неверными хэш ингридиентов")
    @Test
    public void createOrderWithWrongIngredients() {
        UsersMethods.createUser(user);
        String authToken = UsersMethods.getAuthToken(user);
        Response response = OrdersMethods.createOrder(new IngredientsForOrder(OrdersMethods.getWrongHashIngredients(ingredientsList)), authToken);
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При при создании заказа, с неверными хэш ингридиентов, статус Код отличается от 500", 500, statusCode);
    }

    @After
    public void deleteTestsClients() {
        UsersMethods.deleteUser(UsersMethods.getAuthToken(user));
    }

}
