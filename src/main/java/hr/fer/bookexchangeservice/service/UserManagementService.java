package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.exception.EmailAlreadyExistsException;
import hr.fer.bookexchangeservice.exception.UsernameAlreadyExistsException;
import hr.fer.bookexchangeservice.model.constant.Role;
import hr.fer.bookexchangeservice.model.entity.UserDetail;
import hr.fer.bookexchangeservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserManagementService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReviewService reviewService;

    /**
     * Save new user. User's password will be hashed upon saving in database.
     * If id is null, then the repository will create a new user.
     *
     * @param user Data that will be saved in repo
     * @return Saved user
     */
    public UserDetail saveUser(UserDetail user) {
        if (this.userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email adresa se već koristi");
        }
        if (this.userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException("Korisničko ime se već koristi");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setRoles(roles);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    /**
     * Searches the database for user with the given username. Used only for auth filtering.
     *
     * @param username Unique username by which the database is queried
     * @return UserDetails class with data of the user with the given username
     * @throws UsernameNotFoundException Throws if database does not contain user with the username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.getUserByUsername(username);
    }

    /**
     * Return user with given username. Sets average review. If no reviews, defaults to -1.
     * @param username Unique username of user.
     * @return UserDetail object
     * @throws UsernameNotFoundException
     */
    public UserDetail getUserByUsername(String username) throws UsernameNotFoundException {
        UserDetail user = this.userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Korisnik s korisničkim imenom " + username + " nije pronađen"));
        this.reviewService.getAverageUserReview(user);
        return user;
    }
}
