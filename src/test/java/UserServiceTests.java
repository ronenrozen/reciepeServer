
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import recipeapplication.RecipeApplication;
import recipeapplication.boundaries.UserEditTO;
import recipeapplication.components.Name;
import recipeapplication.components.Role;
import recipeapplication.components.User;
import recipeapplication.exceptions.UserNotFoundException;
import recipeapplication.services.UserService;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Component
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RecipeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "server.port=8080")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTests {


    private int port;
    private String baseUrl;
    private RestTemplate restTemplate;
    private static String id;
    private static int count = 0;
    private boolean deleteTest = false;

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

    @AfterEach
    public void tearDown() {
        if (!deleteTest)
            this.userService.deleteUser(id);
    }

    @Test
    public void addNewUserTest() {
        deleteTest = false;
        // GIVEN the database is clean

        // WHEN I post a new user
        User newUser = new User();
        fillDetailsNewUser(newUser);
        User response = this.restTemplate
                .postForObject(
                        this.baseUrl,
                        newUser,
                        User.class);

        // THEN the database contains 1 user
        // AND the returned user is similar to the user in the database
        assertThat(response)
                .isNotNull();
        assertThat(this.userService.readUser(id))
                .isNotNull();
        assertEquals(id,this.userService.readUser(id).getId());
        assertThat(this.userService.readUser(id).getName())
                .isEqualTo(newUser.getName());
    }

    @Test
    public void readUserTest() {
        deleteTest = false;
        // GIVEN the database is include 1 user
        User newUser = new User();
        fillDetailsNewUser(newUser);
        this.userService.addUser(newUser);

        // WHEN I read a new user
        ResponseEntity<User> result=this.restTemplate
                .getForEntity(
                        this.baseUrl + "/{id}",
                        User.class,newUser.getId());

        // THEN the database contains 1 user
        // AND the returned user is similar to the user in the database
        assertThat(result)
                .isNotNull();
        assertEquals(id,result.getBody().getId());
        assertThat(result.getBody().getName())
                .isEqualTo(newUser.getName());
    }

    @Test
    public void updateUserTest() {
        deleteTest = false;
        // GIVEN the database include 1 user
        User newUser = new User();
        fillDetailsNewUser(newUser);
        this.userService.addUser(newUser);

        // WHEN I update a specific user
        UserEditTO userEdit = new UserEditTO();
        ArrayList<String> myList = new ArrayList<String>();
        String firstFavoriteRecipe = "Rice";
        myList.add(firstFavoriteRecipe);
        myList.add("Meat");
        userEdit.setFavoriteRecipes(myList);

        this.restTemplate
                .put(this.baseUrl + "/{id}",
                        userEdit,
                        newUser.getId());

        // THEN the database contains 1 update user with update favorite recipes
        assertThat(this.userService.readUser(id).getFavoriteRecipes().get(0))
                .isNotNull()
                .isEqualTo(firstFavoriteRecipe);

    }

    @Test
    public void deleteUserTest() {
        deleteTest = true;
        // GIVEN the database with 1 user
        User newUser = new User();
        fillDetailsNewUser(newUser);
        this.userService.addUser(newUser);

        // WHEN I remove a specific user
        this.restTemplate
                .delete(this.baseUrl + "/{id}",
                        newUser.getId());

        // THEN when we will try to read the specific user and we will get an Exception
        try {
            this.userService.readUser(id);
        } catch (UserNotFoundException e) {
            assertEquals("User not found", e.getMessage());
        }

    }

    private void fillDetailsNewUser(User newUser) {
        count++;
        id = "104488" + count;
        newUser.setId(id);
        Name myName = new Name("Leo", "Messi");
        newUser.setName(myName);
        newUser.setRole(Role.ADMIN);
        ArrayList<String> myList = new ArrayList<String>();
        myList.add("Cake");
        myList.add("Malbi");
        newUser.setFavoriteRecipes(myList);
    }
}
