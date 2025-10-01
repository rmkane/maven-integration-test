package org.example.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @Value("${springdoc.swagger-ui.path:/swagger-ui.html}")
    private String swaggerDocPath;

    @GetMapping("/")
    public ModelAndView index(ModelMap model) {
        return new ModelAndView("redirect:" + swaggerDocPath, model);
    }
}
