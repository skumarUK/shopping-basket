package uk.co.shopping.basket;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import uk.co.shopping.discount.DiscountCalculator;
import uk.co.shopping.offers.Offer;
import uk.co.shopping.offers.OfferType;
import uk.co.shopping.products.Product;
import uk.co.shopping.products.ProductType;
import static org.junit.Assert.assertEquals;


public class ShoppingBasketTest {

    private ShoppingBasket basket;
    private final Map<String, Offer> availableOffers = new HashMap<>();

    private  final BigDecimal price_12p =  new BigDecimal("12.0");
    private  final BigDecimal price_32p =  new BigDecimal("32.0");
    private  final BigDecimal price_51p =  new BigDecimal("51.0");
    private  final BigDecimal price_95p =  new BigDecimal("95.0");

    private final Product apple = new Product("APPLE", ProductType.BARCODED, price_12p);
    private final Product orange = new Product("ORANGE", ProductType.BARCODED, price_32p);
    private final Product banana = new Product("BANANA", ProductType.BARCODED, price_51p);
    private final Product pineapple = new Product("PINEAPPLE", ProductType.BARCODED, price_95p);

    @Before
    public void setup() {
        availableOffers.put("APPLE", new Offer(apple,2, OfferType.BUY_ONE_GET_ONE_FREE));
        availableOffers.put("BANANA", new Offer(banana,3,OfferType.BUY_THREE_FOR_THE_PRICE_OF_TWO));
        DiscountCalculator discountCalculator = new DiscountCalculator();
        basket = new ShoppingBasket(availableOffers, discountCalculator);
    }

    @After
    public void cleanUp() {
        availableOffers.clear();
    }

    @Test
    public void testOffersBuyOneGetOneFree() {
        BasketItem basketItem_1 = new BasketItem(apple);
        BasketItem basketItem_2 = new BasketItem(apple);
        BasketItem basketItem_3 = new BasketItem(orange);
        BasketItem basketItem_4 = new BasketItem(pineapple);
        BasketItem basketItem_5 = new BasketItem(banana);
        BasketItem basketItem_6 = new BasketItem(orange);
        BasketItem basketItem_7 = new BasketItem(banana);
        BasketItem basketItem_8 = new BasketItem(apple);

        basket.addBasketItem(basketItem_1);
        basket.addBasketItem(basketItem_2);
        basket.addBasketItem(basketItem_3);
        basket.addBasketItem(basketItem_4);
        basket.addBasketItem(basketItem_5);
        basket.addBasketItem(basketItem_6);
        basket.addBasketItem(basketItem_7);
        basket.addBasketItem(basketItem_8);

        assertEquals(4 , basket.getTotalLineItem());
        assertEquals(3 , basket.getBasketItems().get(0).getQuantity());
        assertEquals(2 , basket.getBasketItems().get(1).getQuantity());
        assertEquals(0 , basket.getTotalPriceBeforeDiscount().compareTo(new BigDecimal("297.0")));
        assertEquals(0 , basket.getTotalPriceAfterDiscount().compareTo(new BigDecimal("285.0")));
        assertEquals(0 , basket.getTotalDiscount().compareTo(new BigDecimal("12")));

    }


    @Test
    public void testOffersBuyThreeForThePriceOfTwo() {
        BasketItem basketItem_1 = new BasketItem(apple);
        BasketItem basketItem_2 = new BasketItem(banana);
        BasketItem basketItem_3 = new BasketItem(orange);
        BasketItem basketItem_4 = new BasketItem(pineapple);
        BasketItem basketItem_5 = new BasketItem(banana);
        BasketItem basketItem_6 = new BasketItem(orange);
        BasketItem basketItem_7 = new BasketItem(banana);

        basket.addBasketItem(basketItem_1);
        basket.addBasketItem(basketItem_2);
        basket.addBasketItem(basketItem_3);
        basket.addBasketItem(basketItem_4);
        basket.addBasketItem(basketItem_5);
        basket.addBasketItem(basketItem_6);
        basket.addBasketItem(basketItem_7);

        assertEquals(4 , basket.getTotalLineItem());
        assertEquals(3 , basket.getBasketItems().get(1).getQuantity());
        assertEquals(0 , basket.getTotalPriceBeforeDiscount().compareTo(new BigDecimal("324.0")));
        assertEquals(0 , basket.getTotalDiscount().compareTo(new BigDecimal("51")));
        assertEquals(0 , basket.getTotalPriceAfterDiscount().compareTo(new BigDecimal("273.0")));

    }

    @Test
    public void testMultipleOffersOnBasketItems() {
        BasketItem basketItem_1 = new BasketItem(apple);
        BasketItem basketItem_2 = new BasketItem(banana);
        BasketItem basketItem_3 = new BasketItem(orange);
        BasketItem basketItem_4 = new BasketItem(pineapple);
        BasketItem basketItem_5 = new BasketItem(banana);
        BasketItem basketItem_6 = new BasketItem(orange);
        BasketItem basketItem_7 = new BasketItem(banana);
        BasketItem basketItem_8 = new BasketItem(apple);

        basket.addBasketItem(basketItem_1);
        basket.addBasketItem(basketItem_2);
        basket.addBasketItem(basketItem_3);
        basket.addBasketItem(basketItem_4);
        basket.addBasketItem(basketItem_5);
        basket.addBasketItem(basketItem_6);
        basket.addBasketItem(basketItem_7);
        basket.addBasketItem(basketItem_8);

        assertEquals(4 , basket.getTotalLineItem());
        assertEquals(3 , basket.getBasketItems().get(1).getQuantity());
        assertEquals(2 , basket.getBasketItems().get(0).getQuantity());
        assertEquals(0 , basket.getTotalPriceBeforeDiscount().compareTo(new BigDecimal("336.0")));
        assertEquals(0 , basket.getTotalDiscount().compareTo(new BigDecimal("63")));
        assertEquals(0 , basket.getTotalPriceAfterDiscount().compareTo(new BigDecimal("273.0")));
    }

    @Test
    public void testNoOffersOnBasketItems() {
        BasketItem basketItem_1 = new BasketItem(apple);
        BasketItem basketItem_2 = new BasketItem(banana);
        BasketItem basketItem_3 = new BasketItem(orange);
        BasketItem basketItem_4 = new BasketItem(pineapple);
        BasketItem basketItem_5 = new BasketItem(banana);
        BasketItem basketItem_6 = new BasketItem(orange);
        BasketItem basketItem_7 = new BasketItem(orange);
        BasketItem basketItem_8 = new BasketItem(pineapple);

        basket.addBasketItem(basketItem_1);
        basket.addBasketItem(basketItem_2);
        basket.addBasketItem(basketItem_3);
        basket.addBasketItem(basketItem_4);
        basket.addBasketItem(basketItem_5);
        basket.addBasketItem(basketItem_6);
        basket.addBasketItem(basketItem_7);
        basket.addBasketItem(basketItem_8);

        assertEquals(4 , basket.getTotalLineItem());
        assertEquals(2 , basket.getBasketItems().get(1).getQuantity());
        assertEquals(1 , basket.getBasketItems().get(0).getQuantity());
        assertEquals(0 , basket.getTotalPriceBeforeDiscount().compareTo(new BigDecimal("400.0")));
        assertEquals(0 , basket.getTotalDiscount().compareTo(BigDecimal.ZERO));
        assertEquals(0 , basket.getTotalPriceAfterDiscount().compareTo(new BigDecimal("400.0")));
    }

}
