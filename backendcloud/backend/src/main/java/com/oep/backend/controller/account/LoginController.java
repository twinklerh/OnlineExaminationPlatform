package com.oep.backend.controller.account;

import com.oep.backend.service.account.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/account/token/")
    public String login(@RequestParam Map<String,String> map) {
        return loginService.getToken(map.get("account_id"), map.get("password"));
    }
}
