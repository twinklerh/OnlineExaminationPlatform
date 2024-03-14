package com.oep.backend.controller.problems.getProblem;

import com.oep.backend.service.problems.getProblem.GetPageProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetPageProblemsController {
    @Autowired
    private GetPageProblemService getPageProblemService;
    @GetMapping("/problems/getallproblems/")
    public String getAllProblem(@RequestParam Map<String, String> map){
        return getPageProblemService.getPageProblems(Integer.parseInt(map.get("current_page")));
    }
}
