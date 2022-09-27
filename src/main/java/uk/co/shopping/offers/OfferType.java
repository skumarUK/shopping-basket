package uk.co.shopping.offers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.shopping.discount.Discount;
import uk.co.shopping.discount.PercentageDiscount;

@Getter
@RequiredArgsConstructor
public enum OfferType {
    BUY_ONE_GET_ONE_FREE (new PercentageDiscount(0.50f)),
    BUY_THREE_FOR_THE_PRICE_OF_TWO (new PercentageDiscount(0.333333f));

    private final Discount discount;
}
