package com.bnpp.kata.developmentbooks.service.extractor;

import com.bnpp.kata.developmentbooks.model.BookItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookQuantityExtractorTest {

    private BookQuantityExtractor bookQuantityExtractor;

    @BeforeEach
    void setup() {
        bookQuantityExtractor = new BookQuantityExtractor();
    }

    @Test
    @DisplayName("BookQuantityExtractor should return array of quantity from bookItemList")
    void shouldReturnArrayOfQuantityFromBookItem() {

        List<BookItems> bookItemsList = List.of(new BookItems("Clean Code", 2),
                new BookItems("The Clean Coder", 3));
        int[] expectedQuantities = bookItemsList.stream().mapToInt(BookItems::getQuantity).toArray();
        int[] quantities = bookQuantityExtractor.extractQuantities(bookItemsList);

        assertEquals(expectedQuantities.length, quantities.length);

    }
}
