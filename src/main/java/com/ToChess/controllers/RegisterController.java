/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.controllers;

import com.ToChess.exceptions.UsernameRepeatedException;
import com.ToChess.models.user.User;
import com.ToChess.repository.RepositoryUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tarde
 */
@Controller
public class RegisterController {

    private final RepositoryUsers userRepo;

    @Autowired
    public RegisterController(RepositoryUsers userRepo) {
        this.userRepo = userRepo;
    }

    @RequestMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register/register");
    }

    @PostMapping("/users")
    public ModelAndView register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email
    ) {

        ModelAndView backView = new ModelAndView("register/register");
        User registeredUser = new User(username);
        try {
            registeredUser.setPassword(password);
            registeredUser.setEmail(email);

            userRepo.insertarUsuario(registeredUser);
            ModelAndView view = new ModelAndView("register/success");
            view.addObject("mensaje", "ingresado");
            return view;
        } catch (UsernameRepeatedException ex) {
            backView.addObject("user", registeredUser);
            backView.addObject("error", "username");
        }
        return backView;
    }

}
