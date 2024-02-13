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
    public Map<String, String> login(@RequestParam Map<String,String> map) {
        Map<String, String> m =loginService.getToken(map.get("account_id"), map.get("password"));
        System.out.println(m);
        return m;
    }
}
