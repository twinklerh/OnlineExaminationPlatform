package com.oep.backend.serviceImpl.check;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.oep.backend.mapper.*;
import com.oep.backend.pojo.*;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.check.AnnounceScoreService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnnounceScoreServiceImpl implements AnnounceScoreService {
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private CandidateProblemMapper candidateProblemMapper;
    @Autowired
    private CandidateExamMapper candidateExamMapper;
    @Override
    public String announceScore(Map<String, String> map) {
        String testPaperTitle = map.get("testPaperTitle");
        Account account = this.authenticate();
        Enterprise enterprise = this.getEnterprise(account);
        Integer examId = this.getExamId(testPaperTitle);
        List<CandidateProblem> candidateProblemsList = this.getCandidateIdList(examId);
        candidateProblemsList.forEach((item)->{
            Double score = this.calculateScore(examId, item.getCandidateId());
            updateCandidateScore(item.getCandidateId(), examId, score);
            UpdateWrapper<CandidateExam> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("announced", true);
            candidateExamMapper.update(updateWrapper);
        });
        Map<String, String> respMap = new HashMap<>();
        respMap.put("error_message", "success");
        return WriteValue.writeValueAsString(respMap);
    }
    private Account authenticate()   {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        return userDetails.getAccount();
    }
    private Enterprise getEnterprise(Account account)   {
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account_id", account.getAccountId());
        return enterpriseMapper.selectOne(queryWrapper);
    }
    private Integer getExamId(String testPaperTitle)   {
        QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("testpaper_title", testPaperTitle);
        return examMapper.selectOne(queryWrapper).getExamId();
    }
    private List<CandidateProblem> getCandidateIdList(Integer examId)    {
        QueryWrapper<CandidateProblem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exam_id", examId);
        return candidateProblemMapper.selectList(queryWrapper);
    }
    private double calculateScore(Integer examId, Integer candidateId) {
        QueryWrapper<CandidateProblem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exam_id", examId);
        queryWrapper.eq("candidate_id", candidateId);
        double sum = 0.0;
        List<CandidateProblem> candidateProblems = candidateProblemMapper.selectList(queryWrapper);
        for(CandidateProblem item: candidateProblems)   {
            if(item.getGetScore() != -1)    sum = sum + item.getGetScore();
        }
        return sum;
    }
    private void updateCandidateScore(Integer candidateId, Integer examId, Double score) {
        UpdateWrapper<CandidateExam> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("candidate_id", candidateId);
        updateWrapper.eq("exam_id", examId);
        updateWrapper.set("score", score);
        candidateExamMapper.update(updateWrapper);
    }
}
