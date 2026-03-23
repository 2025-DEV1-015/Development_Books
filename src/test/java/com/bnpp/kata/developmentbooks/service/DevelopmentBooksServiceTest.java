package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.exception.InvalidBookException;
import com.bnpp.kata.developmentbooks.model.BookItems;
import com.bnpp.kata.developmentbooks.model.OrderResponse;
import com.bnpp.kata.developmentbooks.service.extractor.BookQuantityExtractor;
import com.bnpp.kata.developmentbooks.service.extractor.BookTitleExtractor;
import com.bnpp.kata.developmentbooks.service.pricing.PricingEngine;
import com.bnpp.kata.developmentbooks.service.validation.BookValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DevelopmentBooksServiceTest {
    private DevelopmentBooksService developmentBooksService;

    @BeforeEach
    void setup() {
        PricingEngine pricingEngine = new PricingEngine();
        BookValidator bookValidator = new BookValidator();
        BookQuantityExtractor bookQuantityExtractor = new BookQuantityExtractor();
        BookTitleExtractor bookTitleExtractor = new BookTitleExtractor();

        developmentBooksService =
                new DevelopmentBooksService(pricingEngine, bookValidator, bookQuantityExtractor, bookTitleExtractor);
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
    void calculateSingleBookPrice() {

        List<BookItems> bookItemsList = Collections.singletonList(new BookItems("Clean Code", 1));

        OrderResponse response = developmentBooksService.calculateBookPrice(bookItemsList);

        assertEquals(50.0, response.getDiscountedPrice());
    }

    @Test
    @DisplayName("should return zero when book title is empty")
    void calculateSingleBookWithEmptyData() {

        List<BookItems> bookItemsList = Collections.singletonList(new BookItems("", 1));
        assertThrows(InvalidBookException.class, () ->
                developmentBooksService.calculateBookPrice(bookItemsList)
        );
    }

    @Test
    @DisplayName("should return zero when book quantity is invalid")
    void calculateSingleBookWithZeroQuantity() {

        List<BookItems> bookItemsList = Collections.singletonList(new BookItems("Clean Code", 0));
        assertThrows(InvalidBookException.class, () ->
                developmentBooksService.calculateBookPrice(bookItemsList)
        );
    }

    @Test
    @DisplayName("should return total price for two different books without discount")
    void calculateTwoDifferentBooksPrice() {

        List<BookItems> bookItemsList = List.of(new BookItems("Clean Code", 1),
                new BookItems("The Clean Coder", 1));
        OrderResponse response = developmentBooksService.calculateBookPrice(bookItemsList);

        assertEquals(95.0, response.getDiscountedPrice());
    }

    @Test
    @DisplayName("Should return total price for multiple quantity of different books with discount")
    void calculateMultipleQuantityOfDifferentBookPrice() {
        List<BookItems> bookItemsList = List.of(new BookItems("Clean Code", 2),
                new BookItems("The Clean Coder", 3));
        OrderResponse response = developmentBooksService.calculateBookPrice(bookItemsList);

        assertEquals(240.0, response.getDiscountedPrice());
    }

    @Test
    @DisplayName("Should return best discounted price for book combination")
    void calculateBestDiscountPrice() {
        List<BookItems> bookItemsList = List.of(new BookItems("Clean code", 2),
                new BookItems("The Clean Coder", 2),
                new BookItems("Clean Architecture", 2),
                new BookItems("Test Driven Development by Example", 1),
                new BookItems("Working Effectively With Legacy Code", 1));
        OrderResponse response = developmentBooksService.calculateBookPrice(bookItemsList);

        assertEquals(320.0, response.getDiscountedPrice());
    }

    @ParameterizedTest
    @DisplayName("should return total price for three,four & five different books without discount")
    @MethodSource("bookDataProvider")
    void calculateMultipleBooksPriceWithoutDiscount(List<BookItems> bookItemsList, double expectedPrice) {

        OrderResponse response = developmentBooksService.calculateBookPrice(bookItemsList);

        assertEquals(expectedPrice, response.getDiscountedPrice());
    }

    static Stream<Arguments> bookDataProvider() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new BookItems("Clean Code", 1),
                                new BookItems("The Clean Coder", 1),
                                new BookItems("Clean Architecture", 1)
                        ),
                        135.0
                ),
                Arguments.of(
                        List.of(
                                new BookItems("Clean Code", 1),
                                new BookItems("The Clean Coder", 1),
                                new BookItems("Clean Architecture", 1),
                                new BookItems("Test Driven Development by Example", 1)
                        ),
                        160.0
                ),
                Arguments.of(
                        List.of(
                                new BookItems("Clean Code", 1),
                                new BookItems("The Clean Coder", 1),
                                new BookItems("Clean Architecture", 1),
                                new BookItems("Test Driven Development by Example", 1),
                                new BookItems("Working Effectively With Legacy Code", 1)
                        ),
                        187.5
                )
        );
    }
}