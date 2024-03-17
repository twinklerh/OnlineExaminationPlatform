package com.oep.backend.controller.check;

import com.oep.backend.service.check.GetCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CheckController {
    @Autowired
    private GetCheckService getCheckService;
    @PostMapping("/get/check/msg/")
    public String getCheckMsg(@RequestParam Map<String, String> map) {
        return getCheckService.getCheckMsg(map.get("testpaper_title"));
    }
}
