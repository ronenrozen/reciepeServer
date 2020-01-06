import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import recipeapplication.RecipeApplication;
import recipeapplication.components.Name;
import recipeapplication.components.Role;
import recipeapplication.components.User;
import recipeapplication.controllers.UserController;
import recipeapplication.database.UserFirebaseCrud;
import recipeapplication.services.UserService;
import recipeapplication.services.UserServiceImpl;
import org.junit.jupiter.api.Test;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Component
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RecipeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "server.port=8080")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTests {

    private int port;
    private String baseUrl;
    private RestTemplate restTemplate;
    String id = "2044";

    @Autowired
    private UserService userService;

    public UserServiceTests() {

    }

    @LocalServerPort
    public void setPort(int port) {
        this.port = port;
    }

    @BeforeAll
    public void init() {
        this.baseUrl = "http://localhost:" + port + "/user";
        this.restTemplate = new RestTemplate();
    }

   /* @After
    public void tearDown() {
        this.usersImpl.deleteUser(id);

    }*/

    @Test
    public void test() {
        // GIVEN the database is clean

        // WHEN I post a new user
        User newUser = new User();
        String id = "2044";
        newUser.setId(id);
        Name myName = new Name("Ruby", "Machine");
        newUser.setName(myName);
        newUser.setRole(Role.ADMIN);
        ArrayList<String> myList = new ArrayList<String>();
        myList.add("Cake");
        newUser.setFavoriteRecipes(myList);
        System.out.println(this.baseUrl);
        System.out.println(newUser);
        User response = this.restTemplate
                .postForObject(
                        this.baseUrl,
                        newUser,
                        User.class);

        // THEN the database contains 1 user
        // AND the returned user is similar to the user in the database

        assertThat(response)
                .isNotNull();

    }
}
