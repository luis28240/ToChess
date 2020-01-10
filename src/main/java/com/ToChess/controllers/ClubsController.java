/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.controllers;

import com.ToChess.models.clubs.Club;
import com.ToChess.models.user.User;
import com.ToChess.repository.RepositoryClubs;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
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
@RequestMapping("/clubs")
public class ClubsController {

    private static final String CLUBS_VIEW_PATH = "clubs/clubs";
    private static final String NEW_CLUB_VIEW_PATH = "clubs/new_club_form";
    
    private final RepositoryClubs repoClub;

    @Autowired
    public ClubsController(RepositoryClubs repoClub) {
        this.repoClub = repoClub;
    }
    
    @GetMapping
    public ModelAndView startClubs(){
        return new ModelAndView(CLUBS_VIEW_PATH);
    }
    
    @GetMapping("/create")
    public ModelAndView createClubForm(){
        return new ModelAndView(NEW_CLUB_VIEW_PATH);
    }
    
    @PostMapping("/create")
    public ModelAndView createClub(
            HttpSession session,
            @RequestParam("txtName") String name,
            @RequestParam("txtMaxUsers") Integer maxUsers){
        
        User user = (User) session.getAttribute("user");
        Club club = new Club();
        
        club.setId(user.getId());
        club.setName(name);
        club.setMaxUsers(maxUsers);
        
        repoClub.createClub(club);
        
        return new ModelAndView(NEW_CLUB_VIEW_PATH);
    }
    
}
