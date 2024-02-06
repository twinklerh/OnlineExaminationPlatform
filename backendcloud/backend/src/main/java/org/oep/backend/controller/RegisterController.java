package org.oep.backend.controller;

import org.oep.backend.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;
    @PostMapping("/register/")
    Map<String, String> register(Map<String,Object> map){
    }
}
