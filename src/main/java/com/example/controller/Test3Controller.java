package com.example.controller;

import com.example.vo.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: liujinhui
 * @Date: 2019/8/24 21:40
 */
@Controller
@RequestMapping("/aaa")
public class Test3Controller {
    private static final Logger logger = LoggerFactory.getLogger(Test3Controller.class);
    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String show(Model model) {
        model.addAttribute("uid", "aaaaaaaaaindex");
        model.addAttribute("name", "index");
        return "index";
    }
    @PostMapping
    @ResponseBody
    public Student show(@RequestBody Student student) {
        logger.info("--------------------进入control------------------------------");
        student.setAge(10);
        return student;
    }

    @GetMapping
    @ResponseBody
    public String show1(HttpServletRequest request) {
        String id = request.getParameter("id");
        logger.info(String.format("-进入control,id %s", id));
        return id;
    }

    @GetMapping("/{id:[\\d]+}")
    @ResponseBody
    public String show2(@PathVariable String id) {
        logger.info(String.format("-进入control,id %s", id));
        return id;
    }

    @GetMapping("/{id}/{name}")
    @ResponseBody
    public String show3(@PathVariable String id, @PathVariable String name) {
        String format = String.format("-进入control,id:%s , name: %s", id, name);
        logger.info(format);
        return format;
    }

    @PostMapping(value = "/x")
    @ResponseBody
    public Student showException(@RequestBody Student student) {
//        throw new RuntimeException("studuent error!..");
        student.setAge(11);
        return student;
    }
}
