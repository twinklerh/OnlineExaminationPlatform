package com.oep.backend.serviceImpl.check;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.*;
import com.oep.backend.pojo.*;
import com.oep.backend.service.check.GetCheckService;
import com.oep.backend.serviceImpl.candidate_exam.ClassCandidateExam;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GetGetCheckServiceImpl extends ClassCandidateExam implements GetCheckService {
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private CandidateProblemMapper candidateProblemMapper;
    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private CandidateMapper candidateMapper;
    @Override
    public String getCheckMsg(String title) {
        Map<String, String> respMap = new HashMap<>();
        Account account = super.getAccount();
        if(!"enterprise".equals(account.getStatus()))  return WriteValue.writeValueAsString(Collections.singletonMap("error_message", "身份验证失败"));
        Integer examId = getExamId(title);
        if(examId==-1)   return WriteValue.writeValueAsString(Collections.singletonMap("error_message", "试卷不存在"));
        JSONArray jsonArray = getAnswerMsg(examId);
        return WriteValue.writeValueAsString(jsonArray);
    }
    private Integer getExamId(String title) {
        QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("testpaper_title", title);
        Exam exam = examMapper.selectOne(queryWrapper);
        if(exam!=null) return exam.getExamId();
        else return -1;
    }
    private JSONArray getAnswerMsg(Integer examId) {
        QueryWrapper<CandidateProblem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exam_id", examId);
        List<CandidateProblem> list = candidateProblemMapper.selectList(queryWrapper);
        JSONArray jsonArray = new JSONArray();
        for(CandidateProblem item: list)    {
            Integer checkId = item.getId();
            Integer candidateId = item.getCandidateId();
            String  candidateName = getCandidateName(candidateId);
            String answer = item.getCandidateAnswer();
            Integer problemId = item.getProblemId();
            String description = getDescription(problemId);
            Float get_score = item.getGetScore();
            Float score = getProblemScore(problemId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("problemId", problemId);
            jsonObject.put("description", description);
            jsonObject.put("checkId", checkId);
            jsonObject.put("answer", answer);
            jsonObject.put("candidateName", candidateName);
            jsonObject.put("getScore", get_score);
            jsonObject.put("score", score);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
    private Float getProblemScore(Integer problem_id) {
        return problemMapper.selectById(problem_id).getScore();
    }
    private String getDescription(Integer problemId) {
        return problemMapper.selectById(problemId).getDescription();
    }
    private String getCandidateName(Integer candidateId)    {
        return candidateMapper.selectById(candidateId).getFullname();
    }
}
