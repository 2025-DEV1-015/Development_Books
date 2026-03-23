package com.bnpp.kata.developmentbooks.service.pricing;

import com.bnpp.kata.developmentbooks.model.BookItems;
import com.bnpp.kata.developmentbooks.model.GroupDetails;
import com.bnpp.kata.developmentbooks.model.PriceResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PricingEngineTest {

    private PricingEngine pricingEngine;

    @BeforeEach
    void setup() {
        pricingEngine = new PricingEngine();
    }

    @Test
    @DisplayName("PricingEngine should return base price for a single book")
    void calculateSingleBookPrice() {

        List<BookItems> bookItemsList = Collections.singletonList(new BookItems("Clean Code", 1));
        int[] quantities = bookItemsList.stream().mapToInt(BookItems::getQuantity).toArray();
        String[] titles = bookItemsList.stream().map(BookItems::getTitle).toArray(String[]::new);

        PriceResult price = pricingEngine.calculatePrice(quantities, titles);

        assertEquals(50.0, price.getPrice());
    }

    @Test
    @DisplayName("PricingEngine should return price based on discount logic")
    void calculateMultipleQuantityOfDifferentBookPrice() {
        List<BookItems> bookItemsList = List.of(new BookItems("Clean Code", 1),
                new BookItems("The Clean Coder", 1));
        int[] quantities = bookItemsList.stream().mapToInt(BookItems::getQuantity).toArray();
        String[] titles = bookItemsList.stream().map(BookItems::getTitle).toArray(String[]::new);

        PriceResult price = pricingEngine.calculatePrice(quantities, titles);

        assertEquals(95.0, price.getPrice());
    }

    @Test
    @DisplayName("should return correct grouped books and price")
    void shouldReturnCorrectGroupedBooksAndPrice() {
        int[] quantities = {1, 1};
        String[] titles = {"Clean Code", "The Clean Coder"};

        PriceResult result = pricingEngine.calculatePrice(quantities, titles);

        assertNotNull(result);
        assertEquals(1, result.getGroups().size());

        GroupDetails group = result.getGroups().get(0);

        assertAll(
                () -> assertEquals(2, group.getGroupSize()),
                () -> assertEquals(List.of("Clean Code", "The Clean Coder"), group.getBooks()),
                () -> assertFalse(group.getBooks().contains("")),
                () -> assertEquals(2 * 50 * (1 - 0.05), group.getAfterdiscountPrice()),
                () -> assertEquals(5.0, group.getDiscountPercentage())
        );
    }
}
