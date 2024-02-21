package com.oep.backend.controller.problems.submitProblem;

import com.oep.backend.service.problems.submitProblem.SubmitObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SubmitObjectController {
    @Autowired
    private SubmitObjectService submitObjectService;

    @PostMapping("/problem/submit/objectivity/")
    public String submitObjectProblem(@RequestParam Map<String, String> map){
        return submitObjectService.addObjectProblem(map);
    }
}
