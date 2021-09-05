package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.enumerations.Role;
import mk.finki.ukim.mk.lab.model.exception.InavlidUserCredentialsExceptions;
import mk.finki.ukim.mk.lab.model.exception.InvalidArgumentException;
import mk.finki.ukim.mk.lab.model.exception.PasswordsDoNotMatchException;
import mk.finki.ukim.mk.lab.model.exception.UsernameExistsException;
import mk.finki.ukim.mk.lab.repository.UserRepository;
import mk.finki.ukim.mk.lab.service.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new InvalidArgumentException();
        }
        return this.userRepository.
                findByUsernameAndPassword(username, passwordEncoder.encode(password)).
                orElseThrow(InavlidUserCredentialsExceptions::new);
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentException();
        }
        if (!password.equals(repeatPassword)) {
            throw new PasswordsDoNotMatchException();
        }
        if (this.userRepository.existsByUsername(username)) {
            throw new UsernameExistsException(username);
        }
        User user = new User(username, passwordEncoder.encode(password), Role.ROLE_USER, name, surname);
        return this.userRepository.save(user);
    }

    @PostConstruct
    public void init() {
        User admin = new User("admin", passwordEncoder.encode("admin"), Role.ROLE_ADMIN, "admin", "admin");
        this.userRepository.save(admin);
        User user = new User("daniel.ilievski", passwordEncoder.encode("di"), Role.ROLE_USER, "Daniel", "Ilievski");
        this.userRepository.save(user);
    }
}
