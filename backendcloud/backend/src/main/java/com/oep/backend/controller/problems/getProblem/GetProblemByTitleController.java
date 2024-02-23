package com.oep.backend.controller.problems.getProblem;

import com.oep.backend.service.problems.getProblem.GetProblemByTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetProblemByTitleController {
    @Autowired
    private GetProblemByTitleService getProblemByTitleService;
    @GetMapping("/problem/getproblembytitle/")
    public String GetProblemByNameService(@RequestParam Map<String,String> map){
        return getProblemByTitleService.getProblemByTitle(map);
    }
}
