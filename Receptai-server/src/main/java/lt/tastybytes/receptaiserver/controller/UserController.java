package lt.tastybytes.receptaiserver.controller;

import lt.tastybytes.receptaiserver.dto.PublicUserDto;
import lt.tastybytes.receptaiserver.dto.ShortUserDto;
import lt.tastybytes.receptaiserver.dto.user.LoginRequestDto;
import lt.tastybytes.receptaiserver.dto.user.LoginResponseDto;
import lt.tastybytes.receptaiserver.dto.user.RegisterRequestDto;
import lt.tastybytes.receptaiserver.model.User;
import lt.tastybytes.receptaiserver.service.UserService;
import lt.tastybytes.receptaiserver.service.impl.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtServiceImpl jwtService;

    @PostMapping(path="/register") // Map ONLY POST Requests
    public ResponseEntity<?> registerNewUser(@RequestBody RegisterRequestDto dto) {
        var user = userService.findUserByEmail(dto.email());
        if (user != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vartotojas tokiu el. pastu jau egzistuoja");

        userService.createUser(dto.name(), dto.email(), dto.password());

        return ResponseEntity.ok(new ShortUserDto(dto.name(), dto.email()));
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    // TODO: figure out
    @GetMapping("/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<PublicUserDto> userProfile() {
        //return "Welcome to User Profile";
        return ResponseEntity.ok(new PublicUserDto("Sample User"));
    }

    // TODO: figure out
    @GetMapping("/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public @ResponseBody String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginRequestDto dto) {
        User authenticatedUser = userService.authenticate(dto.email(), dto.password());
        String jwtToken = jwtService.generateToken(authenticatedUser);
        return ResponseEntity.ok(new LoginResponseDto(jwtToken, jwtService.getExpirationTime()));
    }
}
