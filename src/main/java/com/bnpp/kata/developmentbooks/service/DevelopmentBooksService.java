package com.bnpp.kata.developmentbooks.service;

import com.bnpp.kata.developmentbooks.constants.Constants;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevelopmentBooksService {

    public List<String> getListOfBooks() {
        return Constants.BOOK_LIST;
    }
}
