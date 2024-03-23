package com.oep.backend.controller.grade;

import com.oep.backend.service.grade.GetGradeByEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetGradeByEnterpriseController {
    @Autowired
    private GetGradeByEnterpriseService getGradeByEnterpriseService;
    @PostMapping("/enterprise/get/candidate/grade/")
    public String getGrade(@RequestParam Map<String, String> map)    {
        Integer page = Integer.valueOf(map.get("page"));
        String testPaperTitle = map.get("testPaperTitle");
        String candidateName = map.get("candidateName");
        return getGradeByEnterpriseService.getGrade(page, testPaperTitle);
    }
}
