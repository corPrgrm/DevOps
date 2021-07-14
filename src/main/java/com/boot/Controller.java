package com.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping("/test")
public class Controller {
    @Autowired
    private UserJpaRepostry userJpaRepostry;
    @Autowired
    private UserJpaMapper mapper;

    @RequestMapping("/jpa")
    public void testjpa(){
        System.out.println(mapper.findAll());
    }
}
