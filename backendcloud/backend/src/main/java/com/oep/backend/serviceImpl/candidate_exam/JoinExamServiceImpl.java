package com.oep.backend.serviceImpl.candidate_exam;

import com.oep.backend.mapper.CandidateExamMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Candidate;
import com.oep.backend.service.candidate_exam.JoinExamService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JoinExamServiceImpl extends ClassCandidateExam implements JoinExamService {
    @Autowired
    private CandidateExamMapper candidateExamMapper;
    public String joinExam(Map<String,String> map){
        Map<String, String> respMap = new HashMap<>();
        Account account = super.getAccount();
        if(!"candidate".equals(account.getStatus())){
            respMap.put("error_message", "身份验证失败");
            return WriteValue.writeValueAsString(respMap);
        }
        Candidate candidate = super.getCandidate(account);
        String inviteCode = map.get("inviteCode");
        //  接下来要将inviteCode与数据库中的匹配，匹配成功，添加应试场次到这个人的数据库里面
        return null;
    }
}
