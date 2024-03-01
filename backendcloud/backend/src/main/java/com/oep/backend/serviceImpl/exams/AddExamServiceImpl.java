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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        String testPaperTitle = map.get("testPaperTitle");
        Date beginDateTime = DateTimeFormat.dateTimeFormat(map.get("beginDateTime"));
        Date endDateTime = DateTimeFormat.dateTimeFormat(map.get("endDateTime"));
        String note = map.get("note");

        Exam exam = new Exam(null, beginDateTime, endDateTime, note, testPaperTitle, enterprise_name);
        examMapper.insert(exam);
        returnMap.put("error_message", "success");
        return WriteValue.writeValueAsString(returnMap);
    }
}
