package com.oep.backend.controller.candidate_exam;

import com.oep.backend.service.candidate_exam.PostTestPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PostTestPaperController {
    @Autowired
    private PostTestPaperService postTestPaperService;
    @PostMapping("/handout/testpaper/")
    public String postTestPaper(@RequestParam Map<String, String> map){
        return postTestPaperService.postTestPaper(map.get("testpaperTitle"));
    }
}
