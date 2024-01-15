package Hypercell.BlogApp.model.response.body;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GeneralResponse<T> {
    boolean success;
    T data;
    String message;
    int pageNumber;
    int totPages;
    int itemsPerPage;

    public GeneralResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public GeneralResponse() {
    }
}

