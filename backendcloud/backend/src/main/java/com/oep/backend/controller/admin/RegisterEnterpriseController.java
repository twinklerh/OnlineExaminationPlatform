package com.oep.backend.controller.admin;

import com.oep.backend.service.admin.RegisterEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterEnterpriseController {
    @Autowired
    private RegisterEnterpriseService registerEnterpriseService;
    @PostMapping("/root/submit/info/")
    public String registerEnterpriseAccount(@RequestBody Map<String, String> map) {
        return registerEnterpriseService.registerEnterpriseAccount(map);
    }
}
