package lt.tastybytes.receptaiserver.service;

import lt.tastybytes.receptaiserver.model.User;

import java.util.List;

public interface UserService {
    void createUser(String name, String email, String password);

    User findUserByEmail(String email);

    List<User> findAllUsers();

    User authenticate(String email, String password);
}
