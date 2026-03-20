package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.constants.BookType;
import com.bnpp.kata.developmentbooks.model.BookItems;
import com.bnpp.kata.developmentbooks.service.pricing.PricingEngine;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DevelopmentBooksService {

    private final PricingEngine pricingEngine = new PricingEngine();

    public List<String> getListOfBooks() {
        return Arrays.stream(BookType.values()).map(BookType::getTitle).toList();
    }

    public double calculateBookPrice(List<BookItems> bookItemsList){
        return pricingEngine.calculatePrice(bookItemsList);
    }
}
