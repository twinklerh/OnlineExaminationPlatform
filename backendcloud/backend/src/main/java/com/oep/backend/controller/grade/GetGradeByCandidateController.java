package com.oep.backend.controller.grade;

import com.oep.backend.service.grade.GetGradeByCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetGradeByCandidateController {
    @Autowired
    private GetGradeByCandidateService getGradeByCandidateService;
    @PostMapping("/candidate/get/grade/")
    public String candidateGetGrade(@RequestParam Map<String, String> map)   {
        Integer currentPage = Integer.valueOf(map.get("page"));
        String testPaperTitle = map.get("testPaperTitle");
        return getGradeByCandidateService.candidateGetGrade(testPaperTitle, currentPage);
    }
}
