package br.com.greedzxn.todolist.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/users")
public class UsersControllers {

    @Autowired
    private IUserRepository userRepository;

    @RequestMapping("/create") 
    public UserModel create(@RequestBody UserModel userModel) {
        var UserCreated = this.userRepository.save(userModel);
        return UserCreated;
    }
}
