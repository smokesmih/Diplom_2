import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


@DisplayName("Проверка получения списка заказов по клиенту")
public class GetOrdersListTest {
    private  User user;
    private Faker faker = new Faker();
    private IngredientsForOrder ingredientsForOrder;
    @Before
    public void setUp() {
        user = new User(faker.internet().emailAddress(), faker.internet().password(), String.valueOf(faker.name()));
        ingredientsForOrder = new IngredientsForOrder(OrdersMethods.getRandomIngredients(OrdersMethods.getIngredientsList()));
    }
    @After
    public void deleteTestsClients() {
        UsersMethods.deleteUser(UsersMethods.getAuthToken(user));
    }

    @Test
    @DisplayName("Проверка получения списка заказов по клиенту с авторизацией")
    public void getOrderList(){
        UsersMethods.createUser(user);
        String authToken = UsersMethods.getAuthToken(user);
        OrdersMethods.createOrder(ingredientsForOrder, authToken);
        OrdersMethods.createOrder(ingredientsForOrder, authToken);
        Response response = OrdersMethods.getOrderList(authToken);
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При получении списка заказов Код отличается от 200", 200, statusCode);
        String body = response.then().extract().jsonPath().getString("success");
        Assert.assertEquals("Тело ответа при получении списка заказов отличается от ожидаемого", "true", body);
    }
    @Test
    @DisplayName("Проверка получения списка заказов по клиенту без авторизации")
    public void getOrderListWithoutAuth(){
        UsersMethods.createUser(user);
        String authToken = UsersMethods.getAuthToken(user);
        OrdersMethods.createOrder(ingredientsForOrder, authToken);
        Response response = OrdersMethods.getOrderListWithoutAuth();
        int statusCode = response.then().extract().statusCode();
        Assert.assertEquals("При получении списка заказов Код отличается от 401", 401, statusCode);
        String body = response.then().extract().jsonPath().getString("message");
        Assert.assertEquals("Тело ответа при получении списка заказов отличается от ожидаемого", "You should be authorised", body);
    }

}
