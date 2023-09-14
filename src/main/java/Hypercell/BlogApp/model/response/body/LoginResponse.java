package Hypercell.BlogApp.model.response.body;

import lombok.Data;

@Data
public class LoginResponse {
    int id;

    public LoginResponse(int id) {
        this.id = id;
    }
}
