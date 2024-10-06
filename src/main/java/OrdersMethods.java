import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OrdersMethods {
    private static final String STELLAR_BURGERS = "https://stellarburgers.nomoreparties.site";

    @Step("Получение актульного списка  хешей ингридиетов")
    public static List<String> getIngredientsList() {
        Response response = given()
                .baseUri(STELLAR_BURGERS)
                .get("/api/ingredients");
        return response.then().extract().jsonPath().getList("data._id");
    }
    @Step("Создание заказа под авторизированным пользователем ")
    public static Response createOrder(IngredientsForOrder ingredients, String authToken){
        return  given()
                .baseUri(STELLAR_BURGERS)
                .auth().oauth2(authToken)
                .body(ingredients)
                .header("Content-Type", "application/json")
                .post("/api/orders");
    }
    @Step("Создание заказа без авторизации")
    public static Response createOrderWithoutAuth(IngredientsForOrder ingredients){
        return  given()
                .baseUri(STELLAR_BURGERS)
                .body(ingredients)
                .header("Content-Type", "application/json")
                .post("/api/orders");
    }
    /* Решил для проверки разных вариантов сделать сделать возможность  заказа различных бургеров,
       но так как есть  требования о хранении всех проверок на ручку в отдельном классе,
       отказался от параметризации.Варианты сделать все одном параметризованном тесте есть, но они кажутся менее удобными для восприятия
     */
    @Step("Получение различных комбинаций ингридиетов на основе действующего списка ингридиетов")
    public static List <String> getRandomIngredients(List list){
        List <String> ingredientsList = list;
        List <String>  data = new ArrayList<>();
        for (int i = 0; i < Math.random() *4; i++) {
          data.add(ingredientsList.get((int) (Math.random() * 12)));
        }
        return   data;
    }
    @Step("Получение ингридиентов с неверным хэшем")
    public static List <String> getWrongHashIngredients(List list){
        List <String> ingredientsList = list;
        List <String>  data = new ArrayList<>();
        for (int i = 0; i < Math.random() *4; i++) {
            data.add(ingredientsList.get((int) (Math.random() * 12))+ "23");
        }
        return   data;
    }
    @Step("Получение списка заказов для клиента c авторизацией")
    public  static Response getOrderList(String authToken){
        return  given()
                .baseUri(STELLAR_BURGERS)
                .auth().oauth2(authToken)
                .get("/api/orders");
    }
    @Step("Получение списка заказов для клиента без авторизации")
    public  static Response getOrderListWithoutAuth(){
        return  given()
                .baseUri(STELLAR_BURGERS)
                .get("/api/orders");
    }
}

