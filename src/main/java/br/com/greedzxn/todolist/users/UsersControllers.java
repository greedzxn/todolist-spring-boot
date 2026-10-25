package br.com.greedzxn.todolist.users;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/users")
public class UsersControllers {

    @RequestMapping("/create") 
    public void create(@RequestBody UserModel userModel) {
        System.out.println(userModel.getName());
    }
}
