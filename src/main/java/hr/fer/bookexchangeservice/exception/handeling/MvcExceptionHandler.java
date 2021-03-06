package hr.fer.bookexchangeservice.exception.handeling;

import hr.fer.bookexchangeservice.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class MvcExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Korisničko ime ili lozinka nisu točni")
    public void handleBadCredentialsException(HttpServletRequest request, Exception e) {
        log.error("Bad credentials", e);
    }

    @ExceptionHandler(IsbnAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleIsbnAlreadyExistsException(HttpServletRequest req, Exception e) {
        log.error("ISBN already exists", e);
        return  ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), "isbn"));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(HttpServletRequest request, Exception e) {
        log.error("Email already exists", e);
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), "email"));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(HttpServletRequest request, Exception e) {
        log.error("Username already exists", e);
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), "username"));
    }

    @ExceptionHandler(AdvertNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Oglas nije pronađen")
    public void handleAdvertNotFound(HttpServletRequest request, Exception e) {
        log.error("Advert does not exist", e);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "SLika nije pronađena")
    public void handleImageNotFound(HttpServletRequest request, Exception e) {
        log.error("Image does not exist", e);
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Autor nije pronađen")
    public void handleAuthorNotFound(HttpServletRequest request, Exception e) {
        log.error("Author does not exist", e);
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Knjiga nije pronađena")
    public void handleBookNotFound(HttpServletRequest request, Exception e) {
        log.error("Book does not exist", e);
    }

    @ExceptionHandler(GenreNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Žanr nije pronađen")
    public void handleGenreNotFound(HttpServletRequest request, Exception e) {
        log.error("Genre does not exist", e);
    }

}

