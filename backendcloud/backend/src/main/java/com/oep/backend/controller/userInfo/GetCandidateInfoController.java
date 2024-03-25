package com.oep.backend.controller.userInfo;

import com.oep.backend.service.user_info.GetCandidateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetCandidateInfoController {
    @Autowired
    private GetCandidateInfoService getCandidateInfoService;
    @GetMapping("/get/candidate/information/")
    private String getCandidateInfo()   {
        return getCandidateInfoService.getCandidateInfo();
    }
}
