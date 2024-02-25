package lt.tastybytes.receptaiserver.controller;

import lt.tastybytes.receptaiserver.model.User;
import lt.tastybytes.receptaiserver.service.UserService;
import lt.tastybytes.receptaiserver.service.impl.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtServiceImpl jwtService;

    @PostMapping(path="/register") // Map ONLY POST Requests
    public ResponseEntity registerNewUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password
    ) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        var user = userService.findUserByEmail(email);
        if (user != null)
            return ResponseEntity.badRequest().build();

        userService.createUser(username, email, password);

        return ResponseEntity.ok("User created");
    }

    @GetMapping("/list")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userService.findAllUsers();
    }

    // TODO: figure out
    @GetMapping("/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public @ResponseBody String userProfile() {
        return "Welcome to User Profile";
    }

    // TODO: figure out
    @GetMapping("/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public @ResponseBody String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(String email, String password) {
        User authenticatedUser = userService.authenticate(email, password);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        return ResponseEntity.ok(new LoginResponse(jwtToken, jwtService.getExpirationTime()));
    }
}
