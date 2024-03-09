package com.oep.backend.controller.candidate_exam;

import com.oep.backend.service.candidate_exam.FinishExamService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FinishExamController {
    @Autowired
    private FinishExamService finishExamService;
    @PostMapping("/candidate/submit/exam/")
    public String submitExam(@RequestParam Map<String, String> map) {
        return finishExamService.submitExam(map);
    }
}
