package com.bnpp.kata.developmentbooks.service.pricing;

import com.bnpp.kata.developmentbooks.model.BookItems;

import java.util.List;

import static com.bnpp.kata.developmentbooks.constants.Constants.BASE_PRICE;

public class PricingEngine {

    public double calculatePrice(List<BookItems> bookItemsList) {
        return bookItemsList.stream().mapToDouble(item-> item.getQuantity() * BASE_PRICE).sum();
    }
}
