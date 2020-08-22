package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: liujinhui
 * @Date: 2019/8/24 19:29
 */
@Controller
@RequestMapping("/html")
public class HtmlController {
    /**
     * @desc 返回templates下的版本名称
     * @param model
     * @return
     */
    @GetMapping("/show.html")
    public String sayHello(Model model){
        model.addAttribute("uid", "123456789");
        model.addAttribute("name", "Jerry");
        return "show";
    }

}
