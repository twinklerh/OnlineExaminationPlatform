package com.oep.backend.controller.candidate_exam;

import com.oep.backend.service.candidate_exam.JoinExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class JoinExamController {
    @Autowired
    private JoinExamService joinExamService;
    @PostMapping("/candidate/join/exam/")
    public String joinExam(@RequestParam Map<String,String> map){
        return joinExamService.joinExam(map);
    }
}
