package recipeapplication.controllers;

import recipeapplication.boundaries.UserEditTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import recipeapplication.components.Name;
import recipeapplication.components.Role;
import recipeapplication.components.User;
import recipeapplication.services.UserService;

import javax.validation.Valid;

@RestController
public class UserController {

    private UserService users;

    @Autowired
    public UserController(UserService users) {
        super();
        this.users = users;
    }

    @GetMapping(
            value = "/user/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User readUser(@PathVariable @Valid String id) {
        return this.users.readUser(id);
    }

    @PostMapping(
            value = "/user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User addUser(@RequestBody @Valid User user) {
        return this.users.addUser(user);
    }

    @DeleteMapping(
            value = "/user/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User deleteUser(@PathVariable @Valid String id) {
        return this.users.deleteUser(id);
    }

    @PutMapping(
            value = "/user/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@PathVariable String id, @RequestBody UserEditTO user) {
        return this.users.updateUser(new User(user.getFavoriteRecipes(), id));
    }
}
