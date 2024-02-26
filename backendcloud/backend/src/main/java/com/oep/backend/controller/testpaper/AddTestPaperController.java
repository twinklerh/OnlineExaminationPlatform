package com.oep.backend.controller.testpaper;

import com.fasterxml.jackson.databind.JsonNode;
import com.oep.backend.service.papertest.AddTestPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@RestController
public class AddTestPaperController {
    @Autowired
    private AddTestPaper addTestPaper;
    @PostMapping("/testpaper/addtestpaper/")
    public String addTestPaper(@RequestParam Map<String,String> map){
        return addTestPaper.addTestPaper(map);
    }
}
