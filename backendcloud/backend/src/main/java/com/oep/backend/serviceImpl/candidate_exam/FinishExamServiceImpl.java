package com.oep.backend.serviceImpl.candidate_exam;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.oep.backend.mapper.CandidateExamMapper;
import com.oep.backend.mapper.CandidateProblemMapper;
import com.oep.backend.mapper.ProblemMapper;
import com.oep.backend.pojo.*;
import com.oep.backend.service.candidate_exam.FinishExamService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FinishExamServiceImpl extends ClassCandidateExam implements FinishExamService {
    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private CandidateProblemMapper candidateProblemMapper;
    @Autowired
    private CandidateExamMapper candidateExamMapper;
    private Candidate candidate;
    private float sumScore = 0;
    private Integer examId = 0;
    @Override
    public synchronized String submitExam(Map<String, String> map) {
        final Account account = super.getAccount();
        Map<String, String> respMap = new HashMap<>();
        if("candidate".equals(account.getStatus()))    candidate = super.getCandidate(account);
        else candidate = null;
        if(candidate == null) {
            respMap.put("error_message", "身份验证失败");
            return WriteValue.writeValueAsString(respMap);
        }
        examId = Integer.valueOf(map.get("examId"));
        String s1 = map.get("myAnswer_select");
        String s2 = map.get("myAnswer_judge");
        String s3 = map.get("myAnswer_fill");
        String s4 = map.get("myAnswer_text");
        if (s1!=null && !s1.isEmpty())      caculate1(JSONObject.parseObject(s1));
        if (s2!=null && !s2.isEmpty())      caculate2(JSONObject.parseObject(s2));
        if (s3!=null && !s3.isEmpty())      caculate3(s3);
        if (s4!=null && !s4.isEmpty())      caculate4(s4);
        UpdateWrapper<CandidateExam> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("candidate_id", this.candidate.getCandidateId());
        updateWrapper.eq("exam_id", this.examId);
        updateWrapper.set("score", sumScore);
        this.sumScore = 0;
        respMap.put("error_message", "success");
        return WriteValue.writeValueAsString(respMap);
    }
    private void caculate1(JSONObject myAnswer) { //  全自动选择题计分
        myAnswer.forEach((key, value)->{
            char ch = (char) (Integer.parseInt(value.toString()) + 65);
            Float score = -1f;
            String rightAnswer = problemMapper.selectById(key).getRightAnswer();
            if(ch == rightAnswer.charAt(0)) {
                QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", Integer.valueOf(key));
                score = problemMapper.selectOne(queryWrapper).getScore();
                this.sumScore += score;
            }
            CandidateProblem candidateProblem = new CandidateProblem(null, Integer.valueOf(key), candidate.getCandidateId(), examId, "" + ch, new Date(), score);
            candidateProblemMapper.insert(candidateProblem);
        });
    }

    private void caculate2(JSONObject myAnswer) { //  全自动判断题计分
        myAnswer.forEach((key, value) -> {
            String rightAnswer = problemMapper.selectById(key).getRightAnswer();
            Float score = -1f;
            if(rightAnswer.equals(value.toString()))   {
                QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", Integer.valueOf(key));
                score = problemMapper.selectOne(queryWrapper).getScore();
                this.sumScore += score;
            }
            CandidateProblem candidateProblem = new CandidateProblem(null, Integer.valueOf(key), candidate.getCandidateId(), examId, value.toString(), new Date(), score);
            candidateProblemMapper.insert(candidateProblem);
        });
    }

    private void caculate3(String s) {  //  全自动填空题判分
        JSONObject jsonObject = null;
        try { jsonObject = JSON.parseObject(s); }
        catch (JSONException e) { return; }
        jsonObject.forEach((key, value)->{
            String rightAnswer = problemMapper.selectById(key).getRightAnswer();
            Float score = -1f;
            if(rightAnswer.equals(value))   {
                QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", Integer.valueOf(key));
                score = problemMapper.selectOne(queryWrapper).getScore();
                this.sumScore += score;
            }
            CandidateProblem candidateProblem = new CandidateProblem(null, Integer.valueOf(key), candidate.getCandidateId(), examId, value.toString(), new Date(), score);
            candidateProblemMapper.insert(candidateProblem);
        });
    }

    private void caculate4(String s) {  //  全人工综合题计分
        s = '{' + s.substring(1,s.length()-1) + ", }";
        JSONObject jsonObject = null;
        try {   jsonObject = JSON.parseObject(s); }
        catch (JSONException e) { return;}
        for(String key: jsonObject.keySet()) {
            Integer problem_id = Integer.valueOf(key);
            String answer = jsonObject.getString(key);
            CandidateProblem candidateProblem = new CandidateProblem(null, problem_id, candidate.getCandidateId(), examId, answer, new Date(), -1f);
            candidateProblemMapper.insert(candidateProblem);
        }
    }
}
