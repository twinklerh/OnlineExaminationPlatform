package com.oep.backend.controller.exams;

import com.oep.backend.service.exams.AddExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddExamController {
    @Autowired
    private AddExamService addExamService;
    @PostMapping("/exam/add/")
    public String addExam(@RequestParam Map<String,String> map){
        return addExamService.addExam(map);
    }
}
