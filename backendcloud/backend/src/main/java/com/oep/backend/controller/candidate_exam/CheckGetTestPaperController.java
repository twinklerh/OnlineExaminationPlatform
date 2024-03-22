package com.oep.backend.controller.candidate_exam;

import com.oep.backend.service.candidate_exam.CheckGetTestPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CheckGetTestPaperController {
    @Autowired
    private CheckGetTestPaperService checkGetTestPaperService;
    @PostMapping("/enterprise/get/unchecked/tesepaper/")
    public String getTestPaper(@RequestParam Map<String,String> map) {
        return checkGetTestPaperService.GetTestPaper(Integer.valueOf(map.get("current_page")));
    }
}
