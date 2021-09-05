package mk.finki.ukim.mk.lab.model.exception;

public class NoItemsInCartException  extends RuntimeException{
    public NoItemsInCartException(Long id){
        super(String.format("No items in cart with id: %d", id));
    }
}
