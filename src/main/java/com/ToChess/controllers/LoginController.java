/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.controllers;

import com.ToChess.models.user.User;
import com.ToChess.repository.RepositoryUsers;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tarde
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    
    private final RepositoryUsers repoUsers;

    @Autowired
    public LoginController(RepositoryUsers repoUsers) {
        this.repoUsers = repoUsers;
    }
    
    @GetMapping
    public ModelAndView loginPage(){
        return new ModelAndView("register/login");
    }
    
    @PostMapping
    public ModelAndView loginUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session){
        
        if(repoUsers.userExists(username, password)){
            User user = repoUsers.getUser(username);
            session.setAttribute("user", user);
            ModelAndView view = new ModelAndView("register/success");
            view.addObject("mensaje", "ingresado");
            return view;
        }else{
            return new ModelAndView("register/login");
        }
        
    }
    
}
