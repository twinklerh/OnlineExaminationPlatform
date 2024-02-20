package com.oep.backend.controller.problems;

import com.oep.backend.service.problems.DeleteProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeleteProblemController {
    @Autowired
    private DeleteProblemService deleteProblemService;
    @PostMapping("/problems/deleteproblem/")
    String deleteProblem(@RequestParam Map<String, String> map){
        return deleteProblemService.deleteProblem(map);
    }
}
