package cu.academy.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Request bad.")
public class ArgumentException extends RuntimeException {
    private final String msg;

    public ArgumentException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
