package mk.finki.ukim.mk.lab.model.exception;

public class UsernameExistsException extends  RuntimeException {
    public UsernameExistsException(String username) {
        super(String.format("User with username %username already exist!", username));
    }
}
