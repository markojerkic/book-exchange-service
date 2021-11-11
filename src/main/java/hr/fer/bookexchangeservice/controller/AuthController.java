package hr.fer.bookexchangeservice.controller;

import hr.fer.bookexchangeservice.model.dto.JwtResponse;
import hr.fer.bookexchangeservice.model.dto.LoginRequest;
import hr.fer.bookexchangeservice.model.entity.UserDetail;
import hr.fer.bookexchangeservice.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    @GetMapping
    public UserDetail getCurrentUserDetail() {
        return this.authService.getCurrentUserDetail();
    }

    @GetMapping("refresh/{token}")
    public JwtResponse refreshToken(@PathVariable String token) {
        return this.authService.refreshToken(token);
    }

    @PutMapping()
    public JwtResponse logIn(@RequestBody LoginRequest loginRequest) {
        return this.authService.login(loginRequest);
    }
}
