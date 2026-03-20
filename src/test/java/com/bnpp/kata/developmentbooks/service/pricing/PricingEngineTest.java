package com.bnpp.kata.developmentbooks.service.pricing;

import com.bnpp.kata.developmentbooks.model.BookItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingEngineTest {

    private PricingEngine pricingEngine;

    @BeforeEach
    void setup() {
        pricingEngine = new PricingEngine();
    }

    @Test
    @DisplayName("PricingEngine should return base price for a single book")
    void calculateSingleBookPrice(){

        List<BookItems> bookItemsList = Collections.singletonList(new BookItems("Clean Code",1));

        double price = pricingEngine.calculatePrice(bookItemsList);

        assertEquals(50.0,price);
    }
}
