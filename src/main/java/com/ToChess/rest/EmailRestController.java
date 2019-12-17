/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.rest;

import com.ToChess.models.user.User;
import com.ToChess.repository.RepositoryUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tarde
 */
@RestController
@RequestMapping("emails")
public class EmailRestController {
    
    private final RepositoryUsers userRepo;

    @Autowired
    public EmailRestController(RepositoryUsers userRepo) {
        this.userRepo = userRepo;
    }
    
    @GetMapping("/{email}")
    public User getUser(@PathVariable String email){
        return userRepo.getUserByEmail(email);
    }
    
}
