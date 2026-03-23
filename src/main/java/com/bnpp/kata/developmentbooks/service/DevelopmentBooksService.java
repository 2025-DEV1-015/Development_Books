package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.constants.BookType;
import com.bnpp.kata.developmentbooks.model.BookItems;
import com.bnpp.kata.developmentbooks.model.OrderResponse;
import com.bnpp.kata.developmentbooks.model.PriceResult;
import com.bnpp.kata.developmentbooks.service.extractor.BookQuantityExtractor;
import com.bnpp.kata.developmentbooks.service.extractor.BookTitleExtractor;
import com.bnpp.kata.developmentbooks.service.pricing.PricingEngine;
import com.bnpp.kata.developmentbooks.service.validation.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.bnpp.kata.developmentbooks.constants.Constants.BASE_PRICE;
import static com.bnpp.kata.developmentbooks.constants.Constants.ZERO_DOUBLE;

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

    public OrderResponse calculateBookPrice(List<BookItems> bookItemsList) {
        if (bookValidator.validate(bookItemsList)) {
            return OrderResponse.builder()
                    .totalPrice(ZERO_DOUBLE)
                    .discountedPrice(ZERO_DOUBLE)
                    .groups(List.of())
                    .build();
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
