package com.oep.backend.controller.userInfo;

import com.oep.backend.service.user_info.UpdateCandidateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateCandidateInfoController {
    @Autowired
    private UpdateCandidateInfoService updateCandidateInfoService;
    @PostMapping("/candidate/set/information/")
    public String updateCandidateInfo(@RequestParam Map<String, String> map)    {
        return updateCandidateInfoService.updateCandidateInfo(map);
    }
}
