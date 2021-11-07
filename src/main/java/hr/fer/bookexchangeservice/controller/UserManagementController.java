package hr.fer.bookexchangeservice.controller;

import hr.fer.bookexchangeservice.model.entity.UserDetail;
import hr.fer.bookexchangeservice.service.UserManagementService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/users")
public class UserManagementController {

    private final UserManagementService userManagementService;

    @PostMapping
    public UserDetail saveUser(@RequestBody UserDetail user) {
        return this.userManagementService.saveUser(user);
    }
}
