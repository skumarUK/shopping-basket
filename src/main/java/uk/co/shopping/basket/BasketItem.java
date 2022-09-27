package uk.co.shopping.basket;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import uk.co.shopping.products.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.valueOf;

@Getter
@EqualsAndHashCode(exclude = {"quantity", "weight"})
public class BasketItem {

    private final Product product;

    private int quantity;

    private float weight ;

    public BasketItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public BasketItem(Product product) {
        this(product, 1);
    }

    public BasketItem(Product product, float weight) {
        this(product);
        this.weight = weight;
    }

    public BigDecimal getBasketItemTotal() {
        return switch (product.productType()) {
            case LOOSE -> product.unitPrice().multiply(valueOf(weight)).setScale(2, RoundingMode.HALF_EVEN);
            default -> product.unitPrice().multiply(valueOf(quantity)).setScale(2,RoundingMode.HALF_EVEN);
        };
    }

    public int increaseQuantity() {
        return ++quantity;
    }

    public int decreaseQuantity() {
        return --quantity;
    }

}
