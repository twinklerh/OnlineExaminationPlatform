package com.oep.backend.serviceImpl.grade;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oep.backend.mapper.CandidateExamMapper;
import com.oep.backend.mapper.CandidateMapper;
import com.oep.backend.mapper.ExamMapper;
import com.oep.backend.mapper.TestpaperMapper;
import com.oep.backend.pojo.*;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.grade.GetGradeByEnterpriseService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetGradeByEnterpriseServiceImpl implements GetGradeByEnterpriseService {
    @Autowired
    private TestpaperMapper testpaperMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private CandidateExamMapper candidateExamMapper;
    @Autowired
    private CandidateMapper candidateMapper;
    private JSONArray jsonArray;
    @Override
    public String getGrade(Integer page, String testPaperTitle) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        Account account = userDetails.getAccount();
        Map<String, String> respMap = new HashMap<>();

        QueryWrapper<Testpaper> queryWrapper = new QueryWrapper<>();
        IPage<Testpaper> testpaperIPage = new Page<>(page, 10);
        queryWrapper.like("title", testPaperTitle);
        queryWrapper.eq("is_score_public", true);
        testpaperIPage = testpaperMapper.selectPage(testpaperIPage,queryWrapper);
        List<Testpaper> testPapers = testpaperIPage.getRecords();

        if(!testPapers.isEmpty())   {
            this.getMessage(testPapers);
            respMap.put("dataCount", String.valueOf(testpaperIPage.getTotal()));
            respMap.put("error_message", "success");
            if(!jsonArray.isEmpty())    respMap.put("jsonArray", WriteValue.writeValueAsString(this.jsonArray));
        }   else    {
            respMap.put("error_message", "数据为空");
        }
        return WriteValue.writeValueAsString(respMap);
    }
    private void getMessage(List<Testpaper> testPapers) {
        testPapers.forEach((item) -> {
            Integer testPaperId = item.getId();
            String testPaperTitle = item.getTitle();
            Date beginTime = getTime("begin_time", testPaperTitle);
            Date endTime = getTime("end_time", testPaperTitle);
            getCandidateAndScore(testPaperId, testPaperTitle, beginTime, endTime);
        });
    }
    private Date getTime(String timeType, String testPaperTitle)   {
        QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("testpaper_title", testPaperTitle);
        if("begin_time".equals(timeType))   return examMapper.selectOne(queryWrapper).getBeginTime();
        return examMapper.selectOne(queryWrapper).getEndTime();
    }
    private synchronized void getCandidateAndScore(Integer testPaperId,String testPaperTitle, Date beginTime, Date endTime)  {
        this.jsonArray = new JSONArray();
        QueryWrapper<Exam> examQueryWrapper = new QueryWrapper<>();
        examQueryWrapper.eq("testpaper_title", testPaperTitle);
        Exam exam = examMapper.selectOne(examQueryWrapper);

        QueryWrapper<CandidateExam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exam_id", exam.getExamId());
        List<CandidateExam> candidateExams = candidateExamMapper.selectList(queryWrapper);

        candidateExams.forEach((item)->{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("testPaperId", testPaperId);
            int begin = testPaperTitle.indexOf('}');
            jsonObject.put("testPaperTitle", testPaperTitle.substring(begin+1));
            jsonObject.put("beginTime", beginTime);
            jsonObject.put("endTime", endTime);
            jsonObject.put("candidateName", candidateMapper.selectById(item.getCandidateId()).getFullname());
            jsonObject.put("score", item.getScore());
            this.jsonArray.add(jsonObject);
        });
    }
}
