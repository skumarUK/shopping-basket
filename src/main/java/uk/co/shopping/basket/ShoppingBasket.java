package uk.co.shopping.basket;

import uk.co.shopping.discount.DiscountCalculator;
import uk.co.shopping.offers.Offer;

import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ZERO;

public class ShoppingBasket {

    private final List<BasketItem> basketItems = new ArrayList<>();

    private BigDecimal totalBeforeDiscount = ZERO;

    private BigDecimal totalAfterDiscount = ZERO;

    private BigDecimal totalDiscount = ZERO;

    /* Key Value Pair of product code and Offer. */
    private final Map<String, Offer> availableOffers;

    private final DiscountCalculator discountCalculator;


    public ShoppingBasket(Map<String, Offer> availableOffers, DiscountCalculator discountCalculator) {
        this.availableOffers = availableOffers;
        this.discountCalculator = discountCalculator;
    }

    public void addBasketItem(BasketItem basketItem) {
        int basketItemIndex = basketItems.indexOf(basketItem);
        if( basketItemIndex != -1) {
            basketItem = basketItems.get(basketItemIndex);
            basketItem.increaseQuantity();
        }else {
            basketItems.add(basketItem);
        }
        refreshTotal();
    }

    public int getTotalLineItem() {
        return basketItems.size();
    }

    public boolean removeBasketItem(BasketItem basketItem) {
        boolean isLineItemRemoved = basketItems.remove(basketItem);
        refreshTotal();
        return isLineItemRemoved;
    }


    public boolean reduceQuantity(BasketItem basketItem) {
        boolean isLineItemReduced = false;
        int basketItemIndex = basketItems.indexOf(basketItem);
        if( basketItemIndex != -1) {
            basketItem = basketItems.get(basketItemIndex);
            int qty = basketItem.decreaseQuantity();
            if(qty == 0) {
                basketItems.remove(basketItem);
            }
            isLineItemReduced = true;
            refreshTotal();
        }
        return isLineItemReduced  ;
    }

    private void refreshTotal() {
        totalBeforeDiscount = ZERO;
        basketItems.forEach(item -> totalBeforeDiscount = totalBeforeDiscount.add(item.getBasketItemTotal()));
        totalDiscount = discountCalculator.calculateDiscount(this.availableOffers, this.basketItems);
        totalAfterDiscount = totalBeforeDiscount.subtract(totalDiscount);
    }

    public List<BasketItem> getBasketItems() {
        return Collections.unmodifiableList(basketItems);
    }

    public BigDecimal getTotalPriceBeforeDiscount() {
        return totalBeforeDiscount;
    }

    public BigDecimal getTotalPriceAfterDiscount() {
        return totalAfterDiscount;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }
}
