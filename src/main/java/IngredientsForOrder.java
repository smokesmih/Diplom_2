import java.util.List;

public class IngredientsForOrder {
    private final List<String> ingredients;

    public IngredientsForOrder(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}
