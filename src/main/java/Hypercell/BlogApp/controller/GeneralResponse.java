package Hypercell.BlogApp.controller;

import lombok.Data;

@Data
public class GeneralResponse<T> {
    boolean success;
    T data;
    String message;
    int pageNumber;
    int totPages;
    int itemsPerPage;
}

