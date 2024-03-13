package com.oep.backend.serviceImpl.candidate_exam;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.oep.backend.mapper.CandidateExamMapper;
import com.oep.backend.mapper.ExamMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.CandidateExam;
import com.oep.backend.pojo.Exam;
import com.oep.backend.service.candidate_exam.JoinExamService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JoinExamServiceImpl extends ClassCandidateExam implements JoinExamService {
    @Autowired
    private CandidateExamMapper candidateExamMapper;
    @Autowired
    private ExamMapper examMapper;
    public String fillInviteCode(Map<String,String> map){
        Map<String, String> respMap = new HashMap<>();
        Account account = super.getAccount();
        if(!"candidate".equals(account.getStatus())){
            respMap.put("error_message", "身份验证失败");
            return WriteValue.writeValueAsString(respMap);
        }
        Integer candidateId = super.getCandidate(account).getCandidateId();
        String inviteCode = map.get("inviteCode");

        Integer examId = isExistExam(inviteCode);
        if(examId == 0) {
            respMap.put("error_message", "加入失败，输入的邀请码有误");
            return WriteValue.writeValueAsString(respMap);
        }
        if(isAlreadyAdd(candidateId, examId)){
            respMap.put("error_message", "您已经添加过该场应试啦！");
            return WriteValue.writeValueAsString(respMap);
        }
        CandidateExam candidateExam = new CandidateExam(null, candidateId, examId, -1, "",false);
        candidateExamMapper.insert((candidateExam));
        respMap.put("error_message", "success");
        return WriteValue.writeValueAsString(respMap);
    }

    @Override
    public String tryToJoinExam(Map<String, String> map) {
        Map<String, String> respMap = new HashMap<>();
        Account account = super.getAccount();
        if(!"candidate".equals(account.getStatus())) {
            respMap.put("error_message", "身份验证失败");
            return WriteValue.writeValueAsString(respMap);
        }
        Integer examId = Integer.valueOf(map.get("examId"));
        Integer candidateId = super.getCandidate(account).getCandidateId();
        QueryWrapper<CandidateExam> candidateExamQueryWrapper = new QueryWrapper<>();
        candidateExamQueryWrapper.eq("candidate_id", candidateId);
        candidateExamQueryWrapper.eq("exam_id", examId);
        System.out.println(candidateExamMapper.selectOne(candidateExamQueryWrapper).getIsJoined());
        if(candidateExamMapper.selectOne(candidateExamQueryWrapper).getIsJoined()){
            respMap.put("error_message", "您已参加过本场考试");
            return WriteValue.writeValueAsString(respMap);
        }
        respMap.put("error_message", "success");
        return WriteValue.writeValueAsString(respMap);
    }

    private Integer isExistExam(String invite_code){
        QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("invite_code", invite_code);
        Exam exam = examMapper.selectOne(queryWrapper);
        if(exam == null || !exam.isAnnounced())    return 0;
        return exam.getExamId();
    }
    private boolean isAlreadyAdd(Integer candidateId, Integer examId){
        QueryWrapper<CandidateExam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("candidate_id", candidateId);
        queryWrapper.eq("exam_id", examId);
        List<CandidateExam> list = candidateExamMapper.selectList(queryWrapper);
        return !list.isEmpty();
    }
}
