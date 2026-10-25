package br.com.greedzxn.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/firstRoute")
// http://localhost:8080/firstRoute
public class FirstController {

    @GetMapping("/hello")
    public String message() {
        return "Hello World!";
    }
}
