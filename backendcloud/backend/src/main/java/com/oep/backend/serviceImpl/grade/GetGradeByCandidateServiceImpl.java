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
import com.oep.backend.service.grade.GetGradeByCandidateService;
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
public class GetGradeByCandidateServiceImpl implements GetGradeByCandidateService {
    @Autowired
    private CandidateMapper candidateMapper;
    @Autowired
    private TestpaperMapper testpaperMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private CandidateExamMapper candidateExamMapper;
    private JSONArray jsonArray;
    @Override
    public String candidateGetGrade(String testPaperTitle, Integer currentPage) {
        Account account = getAccount();
        Candidate candidate = getCandidate(account);
        //  查找已经公布成绩的试卷
        IPage<Testpaper> testpaperIPage = new Page<>(currentPage, 7);
        QueryWrapper<Testpaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title", testPaperTitle);
        queryWrapper.eq("is_score_public", true);
        testpaperIPage = testpaperMapper.selectPage(testpaperIPage, queryWrapper);
        List<Testpaper> testPaper = testpaperIPage.getRecords();

        jsonArray = new JSONArray();
        testPaper.forEach((item)->{
            Exam exam = getExam(item.getTitle());
            CandidateExam candidateExam = getCandidateExam(candidate.getCandidateId(), exam.getExamId());
            String candidateName = candidateMapper.selectById(candidateExam.getCandidateId()).getFullname();
            filterMessage(candidateExam, item.getId(), item.getTitle(), exam.getBeginTime(), exam.getEndTime(), candidateName, candidateExam.getScore());
        });
        Map<String, String> respMap = new HashMap<>();
        respMap.put("error_message", "success");
        respMap.put("dataCount", String.valueOf(testpaperIPage.getTotal()));
        respMap.put("jsonArray", WriteValue.writeValueAsString(jsonArray));
        return WriteValue.writeValueAsString(respMap);
    }
    private Account getAccount()    {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        return userDetails.getAccount();
    }
    private Candidate getCandidate(Account account)    {
        QueryWrapper<Candidate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", account.getAccountId());
        return candidateMapper.selectOne(queryWrapper);
    }
    private Exam getExam(String testPaperTitle) {
        QueryWrapper<Exam> examQueryWrapper = new QueryWrapper<Exam>();
        examQueryWrapper.eq("testpaper_title", testPaperTitle);
        return examMapper.selectOne(examQueryWrapper);
    }
    private CandidateExam getCandidateExam(Integer candidateId, Integer examId) {
        QueryWrapper<CandidateExam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("candidate_id", candidateId);
        queryWrapper.eq("exam_id", examId);
        return candidateExamMapper.selectOne(queryWrapper);
    }
    private void filterMessage(CandidateExam candidateExam, Integer testPaperId, String testPaperTitle, Date beginTime, Date endTime, String candidateName, Float score) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("testPaperId", testPaperId);
        int begin = testPaperTitle.indexOf('}');
        jsonObject.put("testPaperTitle", testPaperTitle.substring(begin+1));
        jsonObject.put("beginTime", beginTime);
        jsonObject.put("endTime", endTime);
        jsonObject.put("candidateName", candidateName);
        jsonObject.put("score", score);
        this.jsonArray.add(jsonObject);
    }
}
