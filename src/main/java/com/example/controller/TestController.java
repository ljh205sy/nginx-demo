package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: liujinhui
 * @Date: 2019/8/24 19:33
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/html/index.html", method = RequestMethod.GET)
    public String show(Model model) {
        model.addAttribute("uid", "index");
        model.addAttribute("name", "index");
        return "index";
    }
}