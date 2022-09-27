package uk.co.shopping.discount;

import uk.co.shopping.basket.BasketItem;
import uk.co.shopping.offers.Offer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import static java.math.BigDecimal.ZERO;

public class DiscountCalculator {

    public BigDecimal calculateDiscount(final Map<String, Offer> availableOffers, final List<BasketItem> basketItems) {
        AtomicReference<BigDecimal> totalDiscount = new AtomicReference<>(ZERO);
        basketItems
                   .forEach(basketItem -> {
                           Offer offer = availableOffers.get(basketItem.getProduct().productCode());
                           if(checkOfferEligibility(offer, basketItem)) {
                               totalDiscount.accumulateAndGet(calculateOfferDiscount(offer, basketItem), BigDecimal::add);
                           }
                   });
        return totalDiscount.get().setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calculateOfferDiscount(Offer offer, BasketItem basketItem){
        switch (offer.offerType()) {
            case BUY_ONE_GET_ONE_FREE, BUY_THREE_FOR_THE_PRICE_OF_TWO -> {
                    int quantityOnWhichOfferIsApplied = calculateEligibleQuantityForOfferDiscount(offer, basketItem);
                    return offer.offerType().getDiscount().getDiscount(new BasketItem(basketItem.getProduct(), quantityOnWhichOfferIsApplied));
                }
            default -> throw new IllegalStateException("Unexpected value: " + offer.offerType());
        }
    }

    private boolean checkOfferEligibility(Offer offer, BasketItem basketItem) {
        return offer != null
                && basketItem.getProduct().productCode().equals(offer.product().productCode())
                && basketItem.getQuantity() >= offer.quantity();
    }

    private int calculateEligibleQuantityForOfferDiscount(Offer offer, BasketItem basketItem) {
        int mod = basketItem.getQuantity() % offer.quantity();
        return mod == 0 ? basketItem.getQuantity() : basketItem.getQuantity() - mod;
    }
}