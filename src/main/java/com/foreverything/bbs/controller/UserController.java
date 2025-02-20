package com.foreverything.bbs.controller;


import com.foreverything.bbs.entities.User;
import com.foreverything.bbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.PrintWriter;
import java.util.List;


/**
 * @ClassName UserController
 * @Author LiuJingxin
 * @Date Created in 15:10 2019/12/17
 * @Description
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    /*@PostMapping("/login")
    public String userLogin(@RequestParam("username") String username,@RequestParam("password") String password){
        if(userService.getID(Integer.parseInt(username))){
            if(password.equals(userService.getPas(Integer.parseInt(username)))) {
                session.setAttribute("name", username);
                return "index";
            }
            else {
                return "login";
            }
        }
        else
            return "login";
    }*/
    @PostMapping("/login")
    public ModelAndView userLogin(@RequestParam("username") String username,@RequestParam("password") String password){
        ModelAndView mv=new ModelAndView();
        if(userService.getID(Integer.parseInt(username))){
            if(password.equals(userService.getPas(Integer.parseInt(username)))) {
                session.setAttribute("name", username);
                mv.addObject("msg","right");
                mv.setViewName("index");
            }
            else {
                mv.addObject("msg","error1");
                mv.setViewName("login");
            }
        }
        else
        {
            mv.addObject("msg","error2");
            mv.setViewName("login");
        }
        return mv;
    }


    @PostMapping("/register")
    public String createUser(User user,@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("mail") String mail){
        userService.insertUser(username,password,mail);
        return "login";
    }


    @PostMapping("/accountUpdate")                            //更改用户名
    public String accountUpdate(@RequestParam("new_account")String new_account) {
        String username = (String) session.getAttribute("name");
            userService.updateAcc(Integer.parseInt(username), new_account);
            return "index";
    }


    @PostMapping("/userUpdate")
    public String userUpdate(@RequestParam("oldpassword") String oldpassword, @RequestParam("newpassword") String newpassword, @RequestParam("newpassword1") String newpassword1) {
        String username = (String) session.getAttribute("name");
        if (oldpassword.equals(userService.getPas(Integer.parseInt(username))) && newpassword.equals(newpassword1)) {
            userService.updatePas(Integer.parseInt(username), newpassword1);
            return "index";
        } else if (oldpassword.equals(userService.getPas(Integer.parseInt(username))) && !newpassword.equals(newpassword1)) {
            return "userUpdate";
        } else
            return "userUpdate";

    }
}
