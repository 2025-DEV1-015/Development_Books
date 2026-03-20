package com.bnpp.kata.developmentbooks.service.validation;

import com.bnpp.kata.developmentbooks.model.BookItems;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.bnpp.kata.developmentbooks.constants.Constants.ZERO_INT;

@Service
public class BookValidator {

    public boolean validate(List<BookItems> bookItemsList) {

        return bookItemsList.stream()
                .anyMatch(item ->
                        Objects.isNull(item.getTitle()) ||
                                item.getTitle().isBlank() ||
                                item.getQuantity() <= ZERO_INT);

    }
}
