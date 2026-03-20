package com.bnpp.kata.developmentbooks.service.validation;

import com.bnpp.kata.developmentbooks.model.BookItems;

import java.util.List;
import java.util.Objects;

public class BookValidator {

    public boolean validate(List<BookItems> bookItemsList) {

        return bookItemsList.stream()
                .anyMatch(item ->
                        Objects.isNull(bookItemsList) ||
                                Objects.isNull(item.getTitle()) ||
                                item.getTitle().isBlank() ||
                                item.getQuantity()<=0);

    }
}
