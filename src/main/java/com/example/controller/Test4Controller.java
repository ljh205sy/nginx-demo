package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: liujinhui
 * @Date: 2019/8/24 21:40
 */
@Controller
public class Test4Controller {
    @RequestMapping(value = "/aaaindex.html", method = RequestMethod.GET)
    public String show(Model model) {
        model.addAttribute("uid", "aaauid");
        model.addAttribute("name", "aaaindexname");
        return "index";
    }
}
