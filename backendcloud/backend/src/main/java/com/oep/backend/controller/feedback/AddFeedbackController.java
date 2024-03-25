package com.oep.backend.controller.feedback;

import com.oep.backend.service.feedback.AddFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddFeedbackController {
    @Autowired
    private AddFeedbackService addFeedbackService;
    @PostMapping("/user/add/feedback/")
    public String addFeedback(@RequestParam Map<String, String> map)    {
        return addFeedbackService.AddFeedBack(map);
    }
}
