package recipeapplication.services;

import TransferObjects.UserEditTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import recipeapplication.components.User;
import recipeapplication.database.UserFirebaseCrud;

@Service
public class UserServiceImpl implements UserService {

    private UserFirebaseCrud users;

    @Autowired
    public UserServiceImpl(UserFirebaseCrud users) {
        this.users = users;
    }

    @Override
    public User addUser(User user) {
        this.users.create(user);
        return user;
    }

    @Override
    public User readUser(String id) {
        return this.users.read(id);
    }

    @Override
    public User updateUser(User user) throws Exception {
       return this.users.update(user);
    }

    @Override
    public User deleteUser(String id) {
        return this.users.delete(id);
    }


}
