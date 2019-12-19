/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ToChess.controllers;

import com.ToChess.models.user.User;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tarde
 */
@Controller
@RequestMapping("logout")
public class LogOutController {

    @RequestMapping()
    public ModelAndView logOut(HttpSession session) {
//        String username = "user-" + session.getId().substring(0, User.USERNAME_LENGHT);
        String username = "anonymous";
        User user = new User(username);
        session.setAttribute("user", user);
        return new ModelAndView("register/logout");
    }

}
