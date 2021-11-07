package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.repository.UserRepository;
import hr.fer.bookexchangeservice.exception.EmailAlreadyExistsException;
import hr.fer.bookexchangeservice.exception.UsernameAlreadyExistsException;
import hr.fer.bookexchangeservice.model.entity.UserDetail;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserManagementService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Save new user. User's password will be hashed upon saving in database.
     * If id is null, then the repository will create a new user.
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
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    /**
     * Return a list of all existing users saved in the database
     * @return List of all users in the database
     */
    public List<UserDetail> getAllUsers() {
        return this.userRepository.findAll();
    }

    /**
     * Searches the database for user with the given username.
     * @param username Unique username by which the database is queried
     * @return UserDetails class with data of the user with the given username
     * @throws UsernameNotFoundException Throws if database does not contain user with the username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException("Korisnik s korisničkim imenom " + username + " nije pronađen");
        });
    }
}
