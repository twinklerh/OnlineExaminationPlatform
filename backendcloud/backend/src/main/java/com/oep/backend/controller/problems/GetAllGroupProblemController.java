package com.oep.backend.controller.problems;

import com.oep.backend.pojo.Group;
import com.oep.backend.service.problems.GetAllGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GetAllGroupProblemController {
    @Autowired
    private GetAllGroupService getAllGroupService;

    @GetMapping("/problems/getallgroups/")
    public Map<String,List<String>> getAllGroup() {
       return getAllGroupService.getAllGroup();
    }
}
