package uk.co.shopping.discount;

import uk.co.shopping.basket.BasketItem;

import java.math.BigDecimal;


public interface Discount {

    BigDecimal applyDiscount(BasketItem basketItem);
    BigDecimal getDiscount(BasketItem basketItem);
}
