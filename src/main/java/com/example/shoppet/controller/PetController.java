package com.example.shoppet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController()
@RequestMapping("/Pet")
public class PetController {

    @GetMapping("")
    public ModelAndView showView(){
        ModelAndView modelAndView = new ModelAndView("home");
        return modelAndView;
    }

}
