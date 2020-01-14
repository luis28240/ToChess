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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tarde
 */
@Controller
@RequestMapping("/clubs")
public class ClubsController {

    private static final String CLUBS_VIEW_PATH = "clubs/clubs";
    private static final String CLUBS_REDIRECT = "redirect:/clubs";
    private static final String CLUB_PAGE_PATH = "clubs/club_page";
    private static final String NEW_CLUB_VIEW_PATH = "clubs/new_club_form";
    
    private final RepositoryClubs repoClub;

    @Autowired
    public ClubsController(RepositoryClubs repoClub) {
        this.repoClub = repoClub;
    }
    
    @GetMapping
    public ModelAndView startClubs(){
        ModelAndView view = new ModelAndView(CLUBS_VIEW_PATH);
        
        view.addObject("clubList", repoClub.getAllClubs());
        
        return view;
    }
    
    @GetMapping("/{id}")
    public ModelAndView clubPage(@PathVariable("id") int id){
        ModelAndView view = new ModelAndView(CLUB_PAGE_PATH);
        
        view.addObject("club", repoClub.getClub(id));
        view.addObject("memberList", repoClub.getClubMembers(id));
        view.addObject("club_leader", repoClub.getClubLeader(id));
        
        return view;
    }
    
    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteClub(@PathVariable("id") int id){
        ModelAndView view = new ModelAndView(CLUB_PAGE_PATH);
        
        System.out.println("Borrar " + id);
        repoClub.deleteClub(id);
//        view.addObject("club", repoClub.getClub(id));
//        view.addObject("memberList", repoClub.getClubMembers(id));
//        view.addObject("club_leader", repoClub.getClubLeader(id));
        
        return "success";
    }
    
    @PutMapping("/{id}/leader/{userId}")
    @ResponseBody
    public String changeLeaderClub(
            @PathVariable("id") int id,
            @PathVariable("userId") int userId
    ){
        ModelAndView view = new ModelAndView(CLUB_PAGE_PATH);
        
        System.out.println("Cambiar lider " + id);
        repoClub.changeLeaderClub(id, userId);
        
        return "success";
    }
    
    @DeleteMapping("/{id}/user/{userId}")
    @ResponseBody
    public String banUserClub(
            @PathVariable("id") int id,
            @PathVariable("userId") int userId
    ){
        ModelAndView view = new ModelAndView(CLUB_PAGE_PATH);
        
        System.out.println("Banear " + id);
        repoClub.banUserClub(id, userId);
        
        return "success";
    }
    
    @GetMapping("/create")
    public ModelAndView createClubForm(){
        return new ModelAndView(NEW_CLUB_VIEW_PATH);
    }
    
    @RequestMapping("/{id}/join")
    public ModelAndView joinClubForm(
            HttpSession session,
            @PathVariable("id") int id){
        
        User user = (User) session.getAttribute("user");
        repoClub.joinClub(user, id);
        return new ModelAndView(CLUBS_REDIRECT);
    }
    
    @RequestMapping("/leave")
    public ModelAndView leaveClubForm(
            HttpSession session){
        User user = (User) session.getAttribute("user");
        
        repoClub.leaveClub(user);
        
        return new ModelAndView(CLUBS_REDIRECT);
    }
    
    @PostMapping("/create")
    public ModelAndView createClub(
            HttpSession session,
            @RequestParam("txtName") String name,
            @RequestParam("txtMaxUsers") Integer maxUsers){
        
        User user = (User) session.getAttribute("user");
        Club club = new Club();
        
        club.setIdLeader(user.getId());
        club.setName(name);
        club.setMaxUsers(maxUsers);
        
        repoClub.createClub(club);
        
        return new ModelAndView(CLUBS_REDIRECT);
    }
    
}
