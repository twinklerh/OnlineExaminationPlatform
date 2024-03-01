package com.oep.backend.controller.testpaper;

import com.oep.backend.service.papertest.GetTestPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetTestPaperController {
    @Autowired
    private GetTestPaper getTestPaper;
    @GetMapping("/testpaper/gettestpaper/")
    public String getTestPaper(@RequestParam Map<String,String> map){
        return getTestPaper.getTestPaper(Integer.valueOf(map.get("page")));
    }
}
