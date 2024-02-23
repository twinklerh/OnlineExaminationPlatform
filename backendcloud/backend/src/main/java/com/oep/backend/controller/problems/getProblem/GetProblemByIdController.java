package com.oep.backend.controller.problems.getProblem;

import com.oep.backend.service.problems.getProblem.GetAProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetProblemByIdController {
    @Autowired
    private GetAProblemService getAProblemService;

    @GetMapping("/getaproblem/")
    public String getAProblem(@RequestParam Map<String,String> map){
        return getAProblemService.GetAProblemById(map);
    }
}
