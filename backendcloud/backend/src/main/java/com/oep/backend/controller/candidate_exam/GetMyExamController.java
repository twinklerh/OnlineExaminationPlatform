package com.oep.backend.controller.candidate_exam;

import com.oep.backend.service.exams.GetMyExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetMyExamController {
    @Autowired
    private GetMyExamService getMyExamService;
    @GetMapping("/candidate/get/myexam/")
    public String getMyExam(@RequestParam Map<String, String> map){
        return getMyExamService.getMyExam(Integer.valueOf(map.get("currentPage")));
    }
}
