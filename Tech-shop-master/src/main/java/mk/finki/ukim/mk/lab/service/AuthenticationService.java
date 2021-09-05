package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.User;

public interface AuthenticationService {
    User login(String username, String password);

    User register(String username, String password, String repeatPassword, String name, String surname);
}
