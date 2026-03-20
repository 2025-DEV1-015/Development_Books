package com.bnpp.kata.developmentbooks.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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
}