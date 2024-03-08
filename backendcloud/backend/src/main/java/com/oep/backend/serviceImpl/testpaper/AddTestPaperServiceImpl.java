package com.oep.backend.serviceImpl.testpaper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.TestpaperMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Testpaper;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddTestPaperServiceImpl extends ClassTestPaper implements com.oep.backend.service.papertest.AddTestPaperService {
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
        Boolean isNeedAppendix = Boolean.valueOf(map.get("isNeedAppendix"));

        title = '{' + enterprise_name + '}' +title;

        Map<String, String> respMap = new HashMap<>();

        QueryWrapper<Testpaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        Testpaper t = testpaperMapper.selectOne(queryWrapper);
        if(t!=null) {
            respMap.put("error_message", "试卷名存在重复");
            return WriteValue.writeValueAsString(respMap);
        }
        Testpaper testpaper =  new Testpaper(null, title, note, problemCount, isNeedAppendix, problemString, enterprise_name);
        testpaperMapper.insert(testpaper);
        respMap.put("error_message", "success");
        return WriteValue.writeValueAsString(respMap);
    }
}
