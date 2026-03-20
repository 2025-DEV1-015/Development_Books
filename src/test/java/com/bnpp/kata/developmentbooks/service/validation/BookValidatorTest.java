package com.bnpp.kata.developmentbooks.service.validation;

import com.bnpp.kata.developmentbooks.model.BookItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookValidatorTest {

    private BookValidator bookValidator;

    @BeforeEach
    void setup() {
        bookValidator = new BookValidator();
    }

    @Test
    @DisplayName("BookValidator should return true when book title is empty")
    void validateSingleBookWithEmptyData(){

        List<BookItems> bookItemsList = Collections.singletonList(new BookItems("",1));
        boolean hasInvalidData = bookValidator.validate(bookItemsList);

        assertEquals(true,hasInvalidData);

    }

    @Test
    @DisplayName("BookValidator should return true when book quantity is ZERO")
    void validateSingleBookWithZeroQuantity(){

        List<BookItems> bookItemsList = Collections.singletonList(new BookItems("Clean Code",0));
        boolean hasInvalidData = bookValidator.validate(bookItemsList);

        assertEquals(true,hasInvalidData);

    }
}
