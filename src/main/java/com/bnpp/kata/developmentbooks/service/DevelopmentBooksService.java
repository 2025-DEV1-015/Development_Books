package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.constants.BookType;
import com.bnpp.kata.developmentbooks.model.BookItems;
import com.bnpp.kata.developmentbooks.service.pricing.PricingEngine;
import com.bnpp.kata.developmentbooks.service.validation.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.bnpp.kata.developmentbooks.constants.Constants.ZERO_DOUBLE;

@Service
@RequiredArgsConstructor
public class DevelopmentBooksService {

    private final PricingEngine pricingEngine;
    private final BookValidator bookValidator;

    public List<String> getListOfBooks() {
        return Arrays.stream(BookType.values()).map(BookType::getTitle).toList();
    }

    public double calculateBookPrice(List<BookItems> bookItemsList) {
        return bookValidator.validate(bookItemsList)
                ? ZERO_DOUBLE
                : pricingEngine.calculatePrice(bookItemsList);
    }
}
