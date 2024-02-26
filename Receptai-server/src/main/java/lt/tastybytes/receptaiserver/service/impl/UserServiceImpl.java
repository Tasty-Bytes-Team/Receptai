package lt.tastybytes.receptaiserver.service.impl;

import lt.tastybytes.receptaiserver.model.Role;
import lt.tastybytes.receptaiserver.model.User;
import lt.tastybytes.receptaiserver.repository.RoleRepository;
import lt.tastybytes.receptaiserver.repository.UserRepository;
import lt.tastybytes.receptaiserver.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

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
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    public User authenticate(String email, String password) {
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
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    private Role createDefaultRoleIfNotExist() {
        Role role = new Role();
        role.setName(DefaultRole);
        return roleRepository.save(role);
    }
}
