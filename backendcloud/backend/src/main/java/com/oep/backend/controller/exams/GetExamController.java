package com.oep.backend.controller.exams;

import com.oep.backend.service.exams.GetExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetExamController {
    @Autowired
    private GetExamService getExamService;
    @PostMapping("/exam/getallexam/")
    public String getAllExam(@RequestParam Map<String,String> map){
        return getExamService.getAllExam(Integer.valueOf(map.get("current_page")));
    }
}
