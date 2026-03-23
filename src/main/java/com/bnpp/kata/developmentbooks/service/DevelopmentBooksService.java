package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.constants.BookType;
import com.bnpp.kata.developmentbooks.exception.InvalidBookException;
import com.bnpp.kata.developmentbooks.model.BookItems;
import com.bnpp.kata.developmentbooks.model.OrderResponse;
import com.bnpp.kata.developmentbooks.model.PriceResult;
import com.bnpp.kata.developmentbooks.service.extractor.BookQuantityExtractor;
import com.bnpp.kata.developmentbooks.service.extractor.BookTitleExtractor;
import com.bnpp.kata.developmentbooks.service.pricing.PricingEngine;
import com.bnpp.kata.developmentbooks.service.validation.BookValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.bnpp.kata.developmentbooks.constants.Constants.BASE_PRICE;

@Service
@RequiredArgsConstructor
public class DevelopmentBooksService {

    private final PricingEngine pricingEngine;
    private final BookValidator bookValidator;
    private final BookQuantityExtractor bookQuantityExtractor;
    private final BookTitleExtractor bookTitleExtractor;

    public List<String> getListOfBooks() {
        return Arrays.stream(BookType.values()).map(BookType::getTitle).toList();
    }

    public OrderResponse calculateBookPrice(List<@Valid BookItems> bookItemsList) {

        if (bookValidator.validate(bookItemsList)) {
            throw new InvalidBookException("Invalid book items: check quantity/title");
        }

        int[] quantities = bookQuantityExtractor.extractQuantities(bookItemsList);
        String[] titles = bookTitleExtractor.extractTitles(bookItemsList);

        PriceResult result = pricingEngine.calculatePrice(quantities, titles);

        int totalBooks = Arrays.stream(quantities).sum();

        double totalPrice = totalBooks * BASE_PRICE;

        return OrderResponse.builder()
                .groups(result.getGroups())
                .totalPrice(totalPrice)
                .discountedPrice(result.getPrice())
                .build();
    }
}
