package com.oep.backend.controller.submitProblem;

import com.oep.backend.service.submitProblem.SubmitSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SubmitSubjectController {
    @Autowired
    private SubmitSubjectService submitSubjectService;

    @PostMapping("/problem/submit/subjectivity/")
    public Map<String,String> submitSubjectProblem(@RequestParam Map<String,String> map){
        submitSubjectService.addSubjectProblem(map);
        return null;
    }
}
