package hr.fer.bookexchangeservice.controller;

import hr.fer.bookexchangeservice.model.entity.UserDetail;
import hr.fer.bookexchangeservice.service.UserManagementService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/user")
@CrossOrigin("*")
public class UserManagementController {

    private final UserManagementService userManagementService;

    @PostMapping
    public UserDetail saveUser(@RequestBody UserDetail user) {
        return this.userManagementService.saveUser(user);
    }

    @GetMapping("{username}")
    public UserDetail getUserByUsername(@PathVariable String username) {
        return this.userManagementService.getUserByUsername(username);
    }
}
