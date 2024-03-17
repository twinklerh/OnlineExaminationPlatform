package com.oep.backend.serviceImpl.candidate_exam;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.CandidateProblemMapper;
import com.oep.backend.mapper.ExamMapper;
import com.oep.backend.pojo.*;
import com.oep.backend.service.candidate_exam.CheckTestPaperService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckTestPaperServiceImpl extends ClassCandidateExam implements CheckTestPaperService {
    private Enterprise enterprise = null;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private CandidateProblemMapper candidateProblemMapper;
    @Override
    public String checkTestPaper(JSONArray titleArray) {
        Account account = super.getAccount();
        Map<String,String> respMap = new HashMap<>();
        if(!"enterprise".equals(account.getStatus()))   return WriteValue.writeValueAsString(respMap.put("error_message", "身份验证失败"));
        enterprise = super.getEnterprise(account);
        JSONArray jsonArray = new JSONArray();
        titleArray.forEach((item)->{
            JSONObject one = getTestpaperMsg((String) item);
            if(one == null) return;
            if(!isChecked((Integer) one.get("exam_id"))) {
                one.remove("exam_id");
                one.put("is_checked", false);
            }   else {
                one.remove("exam_id");
                one.put("is_checked", true);
            }
            jsonArray.add(one);
        });
        if(jsonArray.isEmpty())     return null;
        return WriteValue.writeValueAsString(jsonArray);
    }
    private JSONObject getTestpaperMsg(String testpaperTitle)   {
        QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("testpaper_title", "{" + enterprise.getName() + "}" + testpaperTitle);
        Exam exam = examMapper.selectOne(queryWrapper);
        if(exam == null)    return null;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("begin_time", exam.getBeginTime());
        jsonObject.put("end_time", exam.getEndTime());
        jsonObject.put("testpaper_title", exam.getTestpaperTitle());
        jsonObject.put("exam_id", exam.getExamId());
        return jsonObject;
    }
    private boolean isChecked(Integer exam_id) {
        QueryWrapper<CandidateProblem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exam_id", exam_id);
        List<CandidateProblem> list = candidateProblemMapper.selectList(queryWrapper);
        for(CandidateProblem item: list)   {
            if(!item.getIsChecked())    return false;
        }
        return true;
    }
}