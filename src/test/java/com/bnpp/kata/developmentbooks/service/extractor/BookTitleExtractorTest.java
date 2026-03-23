package com.bnpp.kata.developmentbooks.service.extractor;

import com.bnpp.kata.developmentbooks.model.BookItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BookTitleExtractorTest {

    private BookTitleExtractor bookTitleExtractor;

    @BeforeEach
    void setup() {
        bookTitleExtractor = new BookTitleExtractor();
    }

    @Test
    @DisplayName("BookTitleExtractor should return array of titles from bookItemList")
    void shouldReturnArrayOfTitlesFromBookItem() {

        List<BookItems> bookItemsList = List.of(new BookItems("Clean Code", 3),
                new BookItems("The Clean Coder", 1));
        String[] titles = bookTitleExtractor.extractTitles(bookItemsList);

        assertArrayEquals(new String[]{
                "Clean Code",
                "The Clean Coder"
        }, titles);

    }
}
