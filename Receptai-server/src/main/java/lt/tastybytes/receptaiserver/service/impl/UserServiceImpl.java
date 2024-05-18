package lt.tastybytes.receptaiserver.service.impl;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.dto.user.PatchUserDto;
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

import java.util.List;
import java.util.Optional;

import static lt.tastybytes.receptaiserver.model.RolesKt.ROLE_USER;

@Service
public class UserServiceImpl implements UserService {

    private static final int USERS_PER_PAGE = 20;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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
    public User createUser(String name, String email, String password) {
        var user = new User();
        user.setName(name);
        user.setEmail(email);

        var passwordEncoder = new BCryptPasswordEncoder();

        user.setPassword(passwordEncoder.encode(password));

        var role = getOrCreateRole(ROLE_USER);
        user.setRoles(List.of(role));
        userRepository.save(user);
        return user;
    }

    private Role getOrCreateRole(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role != null) return role;
        var newRole = new Role();
        newRole.setName(roleName);
        return roleRepository.save(newRole);
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

        return userRepository.save(user);
    }

    @Override
    public User addRoleToUser(User user, String role) {
        user.addRole(getOrCreateRole(role));
        return userRepository.save(user);
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

    @Override
    public Number getTotalUserCount() {
        return userRepository.count();
    }
}
