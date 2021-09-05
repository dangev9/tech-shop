package mk.finki.ukim.mk.lab.model.exception;

public class OrderDoesNotExistException extends RuntimeException {
    public OrderDoesNotExistException(Long id) {
        super(String.format("Order with id %d does not exist!", id));
    }
}
