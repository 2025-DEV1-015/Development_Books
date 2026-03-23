package com.bnpp.kata.developmentbooks.service.extractor;

import com.bnpp.kata.developmentbooks.model.BookItems;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookQuantityExtractor {

    public int[] extractQuantities(final List<BookItems> bookItemsList) {
        return bookItemsList.stream()
                .mapToInt(BookItems::getQuantity)
                .toArray();
    }
}
