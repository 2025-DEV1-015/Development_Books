package com.bnpp.kata.developmentbooks.service.pricing;

import com.bnpp.kata.developmentbooks.model.BookItems;

import java.util.List;
import java.util.stream.Collectors;

import static com.bnpp.kata.developmentbooks.constants.Constants.*;

public class PricingEngine {

    public double calculatePrice(List<BookItems> bookItemsList) {
        long uniqueBooks = bookItemsList.stream()
                .map(BookItems::getTitle)
                .collect(Collectors.toSet())
                .size();
        double totalBooks = bookItemsList.stream()
                .mapToDouble(BookItems::getQuantity)
                .sum();
        double discount = DISCOUNT.getOrDefault((int) uniqueBooks, ZERO_DOUBLE);
        double discountedPrice = uniqueBooks * BASE_PRICE *(ONE-discount);
        return (totalBooks-uniqueBooks) * BASE_PRICE + discountedPrice;    }
}
