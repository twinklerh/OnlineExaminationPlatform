package com.oep.backend.controller.candidate_exam;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.oep.backend.service.candidate_exam.CheckTestPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

@RestController
public class CheckTestPaperController {
    @Autowired
    private CheckTestPaperService checkTestPaperService;
    @PostMapping("/get/check/message/")
    public String checkTestPaperService(@RequestParam Map<String,String> map) {
        JSONArray jsonArray = JSON.parseArray(map.get("testpaperTitle"));
        return checkTestPaperService.checkTestPaper(jsonArray);
    }
}
