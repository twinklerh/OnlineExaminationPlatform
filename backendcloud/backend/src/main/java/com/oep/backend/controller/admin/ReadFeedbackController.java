package com.oep.backend.controller.admin;

import com.oep.backend.service.admin.ReadFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ReadFeedbackController {
    @Autowired
    private ReadFeedbackService readFeedbackService;
    @PostMapping("/admin/read/feedback/")
    public String readFeedback(@RequestBody Map<String, String> map)    {
        System.out.println(map);
        return readFeedbackService.readFeedback(Integer.parseInt(map.get("feedbackId")));
    }
}
