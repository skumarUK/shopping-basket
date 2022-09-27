package uk.co.shopping.products;

import java.math.BigDecimal;

public record Product(String productCode, ProductType productType,
                      BigDecimal unitPrice) {
}
