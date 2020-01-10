/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
    @GetMapping
    public ModelAndView startClubs(){
        return new ModelAndView(CLUBS_VIEW_PATH);
    }
    
    @GetMapping("/create")
    public ModelAndView createClub(){
        return new ModelAndView(NEW_CLUB_VIEW_PATH);
    }
    
}
