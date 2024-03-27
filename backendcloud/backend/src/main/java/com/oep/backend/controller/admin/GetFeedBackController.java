package com.oep.backend.controller.admin;

import com.oep.backend.service.admin.GetFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetFeedBackController {
    @Autowired
    private GetFeedbackService getFeedbackService;
    @PostMapping("/admin/get/feedback/")
    public String getFeedbackInfo(@RequestBody Map<String, String> map) {
        return getFeedbackService.getFeedbackInfo(Integer.parseInt(map.get("currentPage")));
    }
}
