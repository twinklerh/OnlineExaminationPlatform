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
    @PostMapping("/candidate/fill/invitecode/")
    public String fillInviteCode(@RequestParam Map<String,String> map){
        return joinExamService.fillInviteCode(map);
    }
    @PostMapping("/candidate/join/exam")
    public String tryToJoinExam(@RequestParam Map<String, String> map){
        System.out.println(666);
        return joinExamService.tryToJoinExam(map);
    }
}
