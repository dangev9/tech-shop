package mk.finki.ukim.mk.lab.model.exception;

public class OrderAlreadyExistsException extends RuntimeException {
    public OrderAlreadyExistsException(Long id) {
        super(String.format("Order with id %id already exist!", id));
    }
}
