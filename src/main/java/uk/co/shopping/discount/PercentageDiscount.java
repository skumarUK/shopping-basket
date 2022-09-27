package uk.co.shopping.discount;

import uk.co.shopping.basket.BasketItem;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

public record PercentageDiscount(float discountPercent) implements Discount {

    @Override
    public BigDecimal applyDiscount(BasketItem basketItem) {
        return basketItem.getBasketItemTotal()
                .subtract((basketItem.getBasketItemTotal().multiply(valueOf(discountPercent))));
    }

    @Override
    public BigDecimal getDiscount(BasketItem basketItem) {
        return basketItem.getBasketItemTotal().multiply(valueOf(discountPercent));
    }
}

