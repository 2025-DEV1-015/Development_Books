package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.constants.BookType;
import com.bnpp.kata.developmentbooks.model.BookItems;
import com.bnpp.kata.developmentbooks.service.pricing.PricingEngine;
import com.bnpp.kata.developmentbooks.service.validation.BookValidator;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.bnpp.kata.developmentbooks.constants.Constants.ZERO_DOUBLE;

@Service
public class DevelopmentBooksService {

    private final PricingEngine pricingEngine = new PricingEngine();
    private final BookValidator bookValidator = new BookValidator();

    public List<String> getListOfBooks() {
        return Arrays.stream(BookType.values()).map(BookType::getTitle).toList();
    }

    public double calculateBookPrice(List<BookItems> bookItemsList){
        if(bookValidator.validate(bookItemsList))
        {
            return ZERO_DOUBLE;
        }
        return pricingEngine.calculatePrice(bookItemsList);
    }
}
