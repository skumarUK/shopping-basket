package uk.co.shopping.offers;

import uk.co.shopping.products.Product;


public record Offer(Product product, int quantity,
                    OfferType offerType) {


}
