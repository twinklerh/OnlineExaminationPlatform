package com.oep.backend.controller.account;

import com.oep.backend.service.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;
    @PostMapping("/account/register/")
    public Map<String, String> register(@RequestParam Map<String,String> map){
        return registerService.addAccount(map.get("account_id"), map.get("password"), map.get("confirmPassword"));
    }
}
