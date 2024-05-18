package lt.tastybytes.receptaiserver.service;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.dto.user.PatchUserDto;
import lt.tastybytes.receptaiserver.exception.NotFoundException;
import lt.tastybytes.receptaiserver.exception.UserAlreadyExistsException;
import lt.tastybytes.receptaiserver.model.user.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Page<User> getUsers(int pageNumber);

    Number getTotalUserCount();

    Optional<User> findUserById(long id);

    User createUser(String name, String email, String password);


    User editUser(User user, @Valid PatchUserDto dto) throws NotFoundException, UserAlreadyExistsException;

    User addRoleToUser(User user, String role);


    User findUserByEmail(String email);



    User authenticate(String email, String password) throws Exception;
}
