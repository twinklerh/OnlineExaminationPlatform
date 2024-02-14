package com.oep.backend.controller.account;

import com.oep.backend.service.account.GetAccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetAccountInfoController {
    @Autowired
    private GetAccountInfo getAccountInfo;
    @PostMapping("/account/user/info/")
    public Map<String, String> getAccountInfo() {
        return getAccountInfo.getAccountInfo();
    }
}
