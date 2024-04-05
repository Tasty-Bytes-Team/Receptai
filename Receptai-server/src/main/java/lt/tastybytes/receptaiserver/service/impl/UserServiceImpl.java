package lt.tastybytes.receptaiserver.service.impl;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.dto.user.PatchUserDto;
import lt.tastybytes.receptaiserver.exception.NotFoundException;
import lt.tastybytes.receptaiserver.exception.UserAlreadyExistsException;
import lt.tastybytes.receptaiserver.model.user.Role;
import lt.tastybytes.receptaiserver.model.user.User;
import lt.tastybytes.receptaiserver.repository.RoleRepository;
import lt.tastybytes.receptaiserver.repository.UserRepository;
import lt.tastybytes.receptaiserver.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final int USERS_PER_PAGE = 20;


    final String DefaultRole = "ROLE_USER";

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Optional<User> findUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void createUser(String name, String email, String password) {
        var user = new User();
        user.setName(name);
        user.setEmail(email);

        var passwordEncoder = new BCryptPasswordEncoder();

        user.setPassword(passwordEncoder.encode(password));

        Role role = roleRepository.findByName(DefaultRole);
        if (role == null) {
            role = createDefaultRoleIfNotExist();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    public User editUser(User user, @Valid PatchUserDto dto) throws UserAlreadyExistsException {

        // Require valid password reauth if changing password or email
        if (dto.newEmail() != null || dto.newPassword() != null) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), dto.oldPassword()));
        }

        if (dto.newEmail() != null) {

            var userWithEmail = findUserByEmail(dto.newEmail());
            if (userWithEmail != null)
                throw new UserAlreadyExistsException();
            user.setEmail(dto.newEmail());
        }

        if (dto.newName() != null) {
            user.setName(dto.newName());
        }

        if (dto.newPassword() != null) {
            var passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(dto.newPassword()));
            // TODO: might prompt token refresh in the future
        }

        if (dto.newProfileAvatarUrl() != null) {
            user.setProfileUrl(dto.newProfileAvatarUrl());
        }

        userRepository.save(user);
        return user;
    }

    public User authenticate(String email, String password) {
        var user = userRepository.findByEmail(email);
        if (user == null) throw new BadCredentialsException("Bad credentials");


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Page<User> getUsers(int pageNumber) {
        return userRepository.findAll(PageRequest.of(pageNumber, USERS_PER_PAGE));
    }

    private Role createDefaultRoleIfNotExist() {
        Role role = new Role();
        role.setName(DefaultRole);
        return roleRepository.save(role);
    }
}
