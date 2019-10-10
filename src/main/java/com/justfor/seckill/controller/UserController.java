package com.justfor.seckill.controller;

import com.justfor.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author lth
 * @version 1.0.0
 * @date
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/db/get")
    @ResponseBody
    public List<User> findUser(){
       return userService.getUser();
    }

}
