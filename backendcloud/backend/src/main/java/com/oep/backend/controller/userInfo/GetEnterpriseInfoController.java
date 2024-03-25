package com.oep.backend.controller.userInfo;

import com.oep.backend.service.user_info.GetEnterpriseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetEnterpriseInfoController {
    @Autowired
    private GetEnterpriseInfoService getEnterpriseInfoService;
    @GetMapping("/get/enterprise/information/")
    public String getEnterpriseInfo()   {
        return getEnterpriseInfoService.getEnterpriseInfo();
    }
}
