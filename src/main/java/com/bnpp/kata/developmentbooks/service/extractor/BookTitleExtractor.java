package com.bnpp.kata.developmentbooks.service.extractor;

import com.bnpp.kata.developmentbooks.model.BookItems;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookTitleExtractor {

    public String[] extractTitles(final List<BookItems> bookItemsList) {
        return bookItemsList.stream()
                .map(BookItems::getTitle)
                .toArray(String[]::new);
    }
}
