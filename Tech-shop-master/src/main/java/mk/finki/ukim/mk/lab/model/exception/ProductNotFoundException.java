package mk.finki.ukim.mk.lab.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(Long id) {
        super(String.format("Product with id: %d was not found", id));
    }
}
