package lt.tastybytes.receptaiserver.service;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.dto.user.PatchUserDto;
import lt.tastybytes.receptaiserver.exception.NotFoundException;
import lt.tastybytes.receptaiserver.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findUserById(long id);

    void createUser(String name, String email, String password);


    User editUser(User user, @Valid PatchUserDto dto) throws NotFoundException;


    User findUserByEmail(String email);

    List<User> findAllUsers();

    User authenticate(String email, String password) throws Exception;
}
