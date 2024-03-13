package com.oep.backend.serviceImpl.candidate_exam;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.oep.backend.mapper.CandidateExamMapper;
import com.oep.backend.mapper.CandidateMapper;
import com.oep.backend.mapper.ProblemMapper;
import com.oep.backend.mapper.TestpaperMapper;
import com.oep.backend.pojo.*;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.candidate_exam.PostTestPaperService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostPaperServiceImpl implements PostTestPaperService {
    @Autowired
    private TestpaperMapper testpaperMapper;
    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private CandidateExamMapper candidateExamMapper;
    @Autowired
    private CandidateMapper candidateMapper;
    @Override
    public String postTestPaper(String testpaperTitle, Integer examId) {
        Candidate candidate = authenticateion();
        if(candidate == null) return WriteValue.writeValueAsString(new JSONArray());
        if(isAlreadyJoined(candidate.getCandidateId(), examId)) return WriteValue.writeValueAsString(new JSONArray());
        QueryWrapper<Testpaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", testpaperTitle);
        String problems =  testpaperMapper.selectOne(queryWrapper).getProblems();
        setFlagJoined(candidate.getCandidateId(), examId);
        return handleProblems(problems);
    }
    private Candidate authenticateion() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        Account account = userDetails.getAccount();
        if(account == null)    return null;
        QueryWrapper<Candidate> candidateQueryWrapper = new QueryWrapper<Candidate>();
        candidateQueryWrapper.eq("account_id", account.getAccountId());
        return candidateMapper.selectOne(candidateQueryWrapper);
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
    private boolean isAlreadyJoined(Integer candidateId, Integer examId){
        QueryWrapper<CandidateExam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("candidate_id", candidateId);
        queryWrapper.eq("exam_id", examId);
        CandidateExam candidateExam = candidateExamMapper.selectOne(queryWrapper);
        return candidateExam.getIsJoined();
    }
    private void setFlagJoined(Integer candidateId, Integer examId) {
        UpdateWrapper<CandidateExam> candidateExamUpdateWrapper = new UpdateWrapper<>();
        candidateExamUpdateWrapper.eq("candidate_id", candidateId);
        candidateExamUpdateWrapper.eq("exam_id", examId);
        candidateExamUpdateWrapper.set("is_joined", true);
        candidateExamMapper.update(candidateExamUpdateWrapper);
    }
}
