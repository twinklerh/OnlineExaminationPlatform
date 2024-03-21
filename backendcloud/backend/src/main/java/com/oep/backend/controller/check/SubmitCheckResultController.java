package com.oep.backend.controller.check;

import com.oep.backend.service.check.SubmitCheckResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SubmitCheckResultController {
    @Autowired
    private SubmitCheckResultService submitCheckResultService;
    @PostMapping("/enterprise/submit/check/result/")
    public String submitCheck(@RequestParam Map<String, String> map) {
        return submitCheckResultService.submitCheckResult(map);
    }
}
