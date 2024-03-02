package com.oep.backend.controller.group;

import com.oep.backend.service.group.AddNewGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddNewGroupController {
    @Autowired
    private AddNewGroupService addNewGroupService;
    @GetMapping("/groups/addnewgroup/")
    public String addNewGroup(@RequestParam Map<String, String> map){
        return addNewGroupService.addNewGroup(map);
    }
}
