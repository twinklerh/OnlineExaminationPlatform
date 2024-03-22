package com.oep.backend.serviceImpl.candidate_exam;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oep.backend.mapper.ExamMapper;
import com.oep.backend.mapper.TestpaperMapper;
import com.oep.backend.pojo.*;
import com.oep.backend.service.candidate_exam.CheckGetTestPaperService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckTestPaperServiceImpl extends ClassCandidateExam implements CheckGetTestPaperService {
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private TestpaperMapper testpaperMapper;

    @Override
    public String GetTestPaper(Integer current_page) {
        Account account = super.getAccount();
        Enterprise enterprise = super.getEnterprise(account);

        QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
        IPage<Exam> examIPage = new Page<>(current_page, 8);
        queryWrapper.eq("enterprise_name", enterprise.getName());
        examIPage = examMapper.selectPage(examIPage, queryWrapper);
        List<Exam> exams = examIPage.getRecords();
        final JSONArray jsonArray = new JSONArray();
        exams.forEach((item)-> {
            String testpaperTitle = item.getTestpaperTitle();
            Date beginTime = item.getBeginTime();
            Date endTime = item.getEndTime();
            boolean is_score_public = isScorePublic(testpaperTitle);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("testpaper_title", testpaperTitle);
            jsonObject.put("begin_time", beginTime);
            jsonObject.put("end_time", endTime);
            jsonObject.put("is_score_public", is_score_public);

            jsonArray.add(jsonObject);
        });
        Map<String, String> respMap = new HashMap<>();
        respMap.put("total", String.valueOf(examIPage.getTotal()));
        respMap.put("error_message", "success");
        respMap.put("jsonArray", WriteValue.writeValueAsString(jsonArray));
        return WriteValue.writeValueAsString(respMap);
    }
    private boolean isScorePublic(String title) {
        QueryWrapper<Testpaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        Testpaper testpaper = testpaperMapper.selectOne(queryWrapper);
        return testpaper.getIsScorePublic();
    }
}