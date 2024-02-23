package com.oep.backend.controller.problems.getProblem;

import com.oep.backend.service.problems.getProblem.GetAllProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetAllProblemsController {
    @Autowired
    private GetAllProblemService getAllProblemService;
    @GetMapping("/problems/getallproblems/")
    public String getAllProblem(){
        return getAllProblemService.getAllProblems();
    }
}
