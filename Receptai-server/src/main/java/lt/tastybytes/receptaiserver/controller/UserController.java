package lt.tastybytes.receptaiserver.controller;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.exception.UserAlreadyExistsException;
import lt.tastybytes.receptaiserver.dto.ShortUserDto;
import lt.tastybytes.receptaiserver.dto.user.FullUserDto;
import lt.tastybytes.receptaiserver.dto.user.LoginRequestDto;
import lt.tastybytes.receptaiserver.dto.user.LoginResponseDto;
import lt.tastybytes.receptaiserver.dto.user.RegisterRequestDto;
import lt.tastybytes.receptaiserver.model.user.User;
import lt.tastybytes.receptaiserver.service.UserService;
import lt.tastybytes.receptaiserver.service.impl.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtServiceImpl jwtService;

    @PostMapping(path="/register") // Map ONLY POST Requests
    public ResponseEntity<ShortUserDto> registerNewUser(@Valid @RequestBody RegisterRequestDto dto) throws Exception {
        var user = userService.findUserByEmail(dto.email());
        if (user != null)
            throw new UserAlreadyExistsException();

        userService.createUser(dto.name(), dto.email(), dto.password());

        return ResponseEntity.ok(new ShortUserDto(dto.name(), dto.email()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@Valid @RequestBody LoginRequestDto dto) {
        User authenticatedUser = userService.authenticate(dto.email(), dto.password());
        String jwtToken = jwtService.generateToken(authenticatedUser);
        return ResponseEntity.ok(new LoginResponseDto(jwtToken, jwtService.getExpirationTime()));
    }

    @GetMapping("/list")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        // TODO: secure behind
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/me")
    public ResponseEntity<FullUserDto> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(new FullUserDto(user.getName(), user.getEmail()));
    }
}
