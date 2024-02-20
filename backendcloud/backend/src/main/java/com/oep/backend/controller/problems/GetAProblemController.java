package com.oep.backend.controller.problems;

import com.oep.backend.service.problems.GetAProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetAProblemController {
    @Autowired
    private GetAProblemService getAProblemService;

    @GetMapping("/getaproblem/")
    public String getAProblem(@RequestParam Map<String,String> map){
        return getAProblemService.GetAProblemById(map);
    }
}
