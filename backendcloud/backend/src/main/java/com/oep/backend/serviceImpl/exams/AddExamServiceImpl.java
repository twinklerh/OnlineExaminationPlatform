package com.oep.backend.serviceImpl.exams;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.EnterpriseMapper;
import com.oep.backend.mapper.ExamMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Enterprise;
import com.oep.backend.pojo.Exam;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.exams.AddExamService;
import com.oep.backend.utils.DateTimeFormat;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AddExamServiceImpl implements AddExamService {
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Override
    public String addExam(Map<String, String> map) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        Account account = userDetails.getAccount();
        QueryWrapper<Enterprise> enterpriseQueryWrapper = new QueryWrapper<>();
        enterpriseQueryWrapper.eq("account_id",account.getAccountId());
        String enterprise_name = enterpriseMapper.selectOne(enterpriseQueryWrapper).getName();
        Map<String,String> returnMap = new HashMap<>();
        String time1 = map.get("beginDateTime");
        String time2 = map.get("endDateTime");
        if("".equals(time1) || "".equals(time2))    {
            returnMap.put("error_message", "时间不能为空");
            return WriteValue.writeValueAsString(returnMap);
        }
        String testPaperTitle = map.get("testPaperTitle");
        if("".equals(testPaperTitle)){
            returnMap.put("error_message", "请选择试卷");
            return WriteValue.writeValueAsString(returnMap);
        }
        Date beginDateTime = DateTimeFormat.dateTimeFormat(time1);
        Date endDateTime = DateTimeFormat.dateTimeFormat(time2);
        String note = map.get("note");
        String inviteCode = getInviteCode();
        Exam exam = new Exam(null, beginDateTime, endDateTime, note, false, inviteCode, testPaperTitle, enterprise_name);
        System.out.println(exam);
        examMapper.insert(exam);
        returnMap.put("error_message", "success");
        return WriteValue.writeValueAsString(returnMap);
    }

    private static String getInviteCode() {
        StringBuilder s = new StringBuilder();
        Random random = new Random();
        int lastNumber = random.nextInt(126);
        int randomNum;
        for(int i = 0; i < 15; i++){
            randomNum = random.nextInt(126);
            if((lastNumber+1)%3 == 0 && ((randomNum >= 'A' && randomNum <= 'Z') || (randomNum >= 'a' && randomNum <= 'z')) ){
                s.append((char) randomNum);
            } else {
                s.append(String.valueOf(randomNum));
            }
            lastNumber = randomNum;
            if(s.length() >= 26)    break;
        }
        return s.toString();
    }

}


