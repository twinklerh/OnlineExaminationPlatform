package com.oep.backend.controller.account;

import com.oep.backend.service.account.GetAccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetAccountInfoController {
    @Autowired
    private GetAccountInfoService getAccountInfoService;
    @PostMapping("/account/user/info/")
    public String getAccountInfo() {
        return getAccountInfoService.getAccountInfo();
    }
}
