package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.model.BookItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.bnpp.kata.developmentbooks.constants.Constants.ZERO_DOUBLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DevelopmentBooksServiceTest {
    private DevelopmentBooksService developmentBooksService;

    @BeforeEach
    void setup() {
        developmentBooksService = new DevelopmentBooksService();
    }

    @Test
    @DisplayName("Should contain all expected development books")
    void shouldContainsAllBooks() {

        List<String> expectedBooks = List.of(
                "Clean Code",
                "The Clean Coder",
                "Clean Architecture",
                "Test Driven Development by Example",
                "Working Effectively With Legacy Code"
        );
        List<String> actualBooks = developmentBooksService.getListOfBooks();

        assertEquals(expectedBooks, actualBooks);

    }

    @Test
    @DisplayName("should return base price for a single book without discount")
    void calculateSingleBookPrice(){

        List<BookItems> bookItemsList = Collections.singletonList(new BookItems("Clean Code",1));

        double price = developmentBooksService.calculateBookPrice(bookItemsList);

        assertEquals(50.0,price);
    }

    @Test
    @DisplayName("should return zero when book title is empty")
    void calculateSingleBookWithEmptyData(){

        List<BookItems> bookItemsList = Collections.singletonList(new BookItems("",1));
        double price = developmentBooksService.calculateBookPrice(bookItemsList);

        assertEquals(ZERO_DOUBLE,price);
    }
}