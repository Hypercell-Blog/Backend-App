package Hypercell.BlogApp.exceptions;

import lombok.Getter;

@Getter
public class GeneralException extends Exception {
    public record ErrorData(String code, String msg) {}

    private ErrorData errorData;
    public GeneralException(String code, String msg) {
        this.errorData = new ErrorData(code, msg);
    }
}
