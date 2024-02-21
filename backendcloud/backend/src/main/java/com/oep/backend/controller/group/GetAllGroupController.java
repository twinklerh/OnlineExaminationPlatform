package com.oep.backend.controller.group;

import com.oep.backend.service.group.GetAllGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetAllGroupController {
    @Autowired
    private GetAllGroupService getAllGroupService;

    @GetMapping("/groups/getallgroups/")
    public String getAllGroup() {
       return getAllGroupService.getAllGroup();
    }
}
