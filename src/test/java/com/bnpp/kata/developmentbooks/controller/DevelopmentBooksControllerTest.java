package com.bnpp.kata.developmentbooks.controller;

import com.bnpp.kata.developmentbooks.constants.BookType;
import com.bnpp.kata.developmentbooks.exception.InvalidBookException;
import com.bnpp.kata.developmentbooks.model.BookItems;
import com.bnpp.kata.developmentbooks.model.Books;
import com.bnpp.kata.developmentbooks.model.GroupDetails;
import com.bnpp.kata.developmentbooks.model.OrderResponse;
import com.bnpp.kata.developmentbooks.service.DevelopmentBooksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DevelopmentBooksController.class)
class DevelopmentBooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DevelopmentBooksService developmentBooksService;

    @Test
    @DisplayName("should return list of available books")
    void shouldReturnListOfBooks() throws Exception {
        List<String> mockBooks = Arrays.stream(BookType.values())
                .map(BookType::getTitle)
                .toList();
        Mockito.when(developmentBooksService.getListOfBooks()).thenReturn(mockBooks);

        mockMvc.perform(get("/api/v1/books/getListOfBooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0]").value("Clean Code"))
                .andExpect(jsonPath("$[1]").value("The Clean Coder"))
                .andExpect(jsonPath("$[2]").value("Clean Architecture"))
                .andExpect(jsonPath("$[3]").value("Test Driven Development by Example"))
                .andExpect(jsonPath("$[4]").value("Working Effectively With Legacy Code"));
    }

    @Test
    @DisplayName("should return OrderResponse from service")
    void shouldReturnDevelopmentBooksPrice() throws Exception {

        Books books = new Books(List.of(
                new BookItems("Clean Code", 2),
                new BookItems("The Clean Coder", 2),
                new BookItems("Clean Architecture", 2),
                new BookItems("Test Driven Development by Example", 1),
                new BookItems("Working Effectively With Legacy Code", 1)));

        OrderResponse response = getOrderResponse();
        Mockito.when(developmentBooksService.calculateBookPrice(Mockito.anyList()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/books/calculatePrice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(books)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groups").isArray())
                .andExpect(jsonPath("$.totalPrice").value(response.getTotalPrice()))
                .andExpect(jsonPath("$.discountedPrice").value(response.getDiscountedPrice()));
    }

    private static OrderResponse getOrderResponse() {
        List<String> books1 = Arrays.asList(
                "Clean Code", "The Clean Coder", "Clean Architecture", "Test Driven Development by Example");
        List<String> books2 = Arrays.asList(
                "Clean Code", "The Clean Coder", "Clean Architecture", "Working Effectively With Legacy Code");

        GroupDetails groupDetails1 = new GroupDetails(books1, 4, 20.0, 160.0);
        GroupDetails groupDetails2 = new GroupDetails(books2, 4, 20.0, 160.0);

        return new OrderResponse(List.of(groupDetails1, groupDetails2),
                400.0, 320.0
        );
    }

    @Test
    @DisplayName("Should return InvalidBookException when invalid quantity passed")
    void handleInvalidBasket_InvalidBookException_Returns400() throws Exception {
        doThrow(new InvalidBookException("Invalid book items: check quantity/title"))
                .when(developmentBooksService).calculateBookPrice(any());

        mockMvc.perform(post("/api/v1/books/calculatePrice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"books\":[{\"title\":\"Clean code\",\"quantity\":0}]}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_BOOK"))
                .andExpect(jsonPath("$.message").value("Invalid book items: check quantity/title"));
    }
}
