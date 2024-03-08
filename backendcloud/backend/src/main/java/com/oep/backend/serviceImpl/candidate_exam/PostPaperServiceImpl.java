package com.oep.backend.serviceImpl.candidate_exam;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.ProblemMapper;
import com.oep.backend.mapper.TestpaperMapper;
import com.oep.backend.pojo.Problem;
import com.oep.backend.pojo.Testpaper;
import com.oep.backend.service.candidate_exam.PostTestPaperService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostPaperServiceImpl implements PostTestPaperService {
    @Autowired
    private TestpaperMapper testpaperMapper;
    @Autowired
    private ProblemMapper problemMapper;
    @Override
    public String postTestPaper(String testpaperTitle) {
        QueryWrapper<Testpaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", testpaperTitle);
        String problems =  testpaperMapper.selectOne(queryWrapper).getProblems();
        return handleProblems(problems);
    }
    private String handleProblems(String s) {
        s = '[' + s + ']';
        List<JSONObject> jsonList = JSON.parseArray(s, JSONObject.class);
        jsonList.forEach(item -> {
            item.remove("rightAnswer");
            item.remove("checkBy");
            if("选择".equals(item.get("type"))) {
                item.put( "appendix", getSelection( (Integer) item.get("problemId")) );
            }
        });
        return WriteValue.writeValueAsString(jsonList);
    }
    private String getSelection(Integer problemId) {
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", problemId);
        return problemMapper.selectOne(queryWrapper).getAppendixName();
    }
}
