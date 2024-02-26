package com.oep.backend.serviceImpl.papertest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.oep.backend.mapper.TestpaperMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Testpaper;
import com.oep.backend.service.papertest.AddTestPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AddTestPaperService extends ClassTestPaper implements AddTestPaper {
    @Autowired
    private TestpaperMapper testpaperMapper;
    @Override
    public String addTestPaper(Map<String,String> map) {
        Account account = super.authenticate();
        String enterprise_name = super.getEnterpriseName(account).getName();

        String title = map.get("title");
        String note = map.get("note");
        Integer problemCount = Integer.valueOf(map.get("problemCount"));
        String problemString = map.get("problemString");

        Testpaper testpaper =  new Testpaper(null, title, note, problemCount, problemString, enterprise_name);
        testpaperMapper.insert(testpaper);
        return "success";
    }
}
