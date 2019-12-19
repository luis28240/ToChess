/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.controllers;

import com.ToChess.repository.RepositoryUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tarde
 */
@Controller
@RequestMapping("user")
public class ProfileController {
    
    private final RepositoryUsers repoUsers;

    @Autowired
    public ProfileController(RepositoryUsers repoUsers) {
        this.repoUsers = repoUsers;
    }
    
    @RequestMapping("/{username}")
    public ModelAndView userProfile(@PathVariable("username") String username){
        ModelAndView view = new ModelAndView("profile-view");
        view.addObject("userProfile", repoUsers.getUser(username));
        return view;
    }
    
}
