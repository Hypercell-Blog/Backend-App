package Hypercell.BlogApp.model.response.body;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response {
    private String code;
    private Object msg;
}
