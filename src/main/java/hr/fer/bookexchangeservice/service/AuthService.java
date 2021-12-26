package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.config.auth.JwtUtils;
import hr.fer.bookexchangeservice.model.dto.JwtResponse;
import hr.fer.bookexchangeservice.model.dto.LoginRequest;
import hr.fer.bookexchangeservice.model.entity.UserDetail;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserManagementService userManagementService;
    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;

    /**
     * Check credentials. Builds JWT access and refresh tokens.
     * @param loginRequest Contains credentials: username and password
     * @return Response containing JWT access and refresh tokens and username
     */
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication auth = this.authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String accessToken = this.jwtUtils.generateAccessToken(auth);
        String refreshToken = this.jwtUtils.generateRefreshToken(auth);

        UserDetails user = (UserDetails) auth.getPrincipal();
        return new JwtResponse(accessToken, refreshToken, user.getUsername(), user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }

    public JwtResponse refreshToken(String refreshToken) {
        if (!this.jwtUtils.validateJwtToken(refreshToken)) {
            throw new IllegalArgumentException("Refresh token is not valid");
        }
        String username = this.jwtUtils.getUsernameFromJwtToken(refreshToken);
        UserDetails user = this.userManagementService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
                user.getAuthorities());

        return new JwtResponse(this.jwtUtils.generateAccessToken(auth),
                this.jwtUtils.generateRefreshToken(auth), username, user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }

    /**
     * Tries to find user by the bearer token
     * @return UserDetail instance of logged in user
     */
    public UserDetail getCurrentUserDetail() {
        return (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
