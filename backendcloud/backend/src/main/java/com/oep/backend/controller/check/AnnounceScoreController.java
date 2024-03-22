package com.oep.backend.controller.check;

import com.oep.backend.service.check.AnnounceScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AnnounceScoreController {
    @Autowired
    private AnnounceScoreService announceScoreService;
    @PostMapping("/enterprise/announce/score")
    public String announceScore(@RequestParam Map<String, String> map)  {
        return announceScoreService.announceScore(map);
    }
}
