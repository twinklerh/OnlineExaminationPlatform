package com.oep.backend.controller.testpaper;

import com.oep.backend.service.papertest.AddTestPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AddTestPaperController {
    @Autowired
    private AddTestPaperService addTestPaperService;
    @PostMapping("/testpaper/addtestpaper/")
    public String addTestPaper(@RequestParam Map<String,String> map){
        return addTestPaperService.addTestPaper(map);
    }
}
