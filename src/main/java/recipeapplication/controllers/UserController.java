package recipeapplication.controllers;

import recipeapplication.Exceptions.BadRequestException;
import recipeapplication.Exceptions.UserNotFoundException;
import recipeapplication.boundries.UserEditTO;
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

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test() {
        User a = new User();
        a.setId("1");
        a.setName(new Name("Sagiv", "Asraf"));
        a.setRole(Role.ADMIN);

        User b = new User();
        b.setId("2");
        b.setName(new Name("Ruby", "Kozel"));
        b.setRole(Role.ADMIN);

        User c = new User();
        c.setId("3");
        c.setName(new Name("Shimon", "Banana"));
        c.setRole(Role.USER);

        users.addUser(a);
        users.addUser(b);
        users.addUser(c);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User readUser(@PathVariable @Valid String id) {
        return this.users.readUser(id);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User addUser(@RequestBody @Valid User user) {
        return this.users.addUser(user);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User deleteUser(@PathVariable @Valid String id) {
        return this.users.deleteUser(id);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@PathVariable String id, @RequestBody UserEditTO user) {
        User convertedUser = UserTOtoUserEntity(user, id);
        return this.users.updateUser(convertedUser);
    }

    private User UserTOtoUserEntity(UserEditTO user, String id) {
        return new User(user.getFavoriteRecipes(), id);
    }

}
