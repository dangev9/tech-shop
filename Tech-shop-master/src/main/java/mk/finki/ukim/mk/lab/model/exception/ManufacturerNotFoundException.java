package mk.finki.ukim.mk.lab.model.exception;

public class ManufacturerNotFoundException extends RuntimeException {
    public ManufacturerNotFoundException(Long id) {
        super(String.format("Manufacturer with id: %d is not found!", id));
    }
}
