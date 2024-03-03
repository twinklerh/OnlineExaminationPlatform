package com.oep.backend.controller.exams;

import com.oep.backend.service.exams.UpdateExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateExamController {
    @Autowired
    private UpdateExamService updateExamService;
    @PostMapping("/exam/release/")
    public String releaseExam(@RequestParam Map<String,String> map){
        return updateExamService.releaseExam(Integer.valueOf(map.get("examId")));
    }
}
