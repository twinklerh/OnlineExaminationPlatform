package com.oep.backend.serviceImpl.exams;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.oep.backend.mapper.EnterpriseMapper;
import com.oep.backend.mapper.ExamMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Enterprise;
import com.oep.backend.pojo.Exam;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.exams.UpdateExamService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateExamServiceImpl implements UpdateExamService {
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Override
    public String releaseExam(Integer examId) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        Account account = userDetails.getAccount();
        Map<String,String> respMap = new HashMap<>();
        if(!"enterprise".equals(account.getStatus())) {
            respMap.put("error_message", "身份验证失败");
            return WriteValue.writeValueAsString(respMap);
        }
        QueryWrapper<Enterprise> enterpriseQueryWrapper = new QueryWrapper<>();
        enterpriseQueryWrapper.eq("account_id", account.getAccountId());
        String enterprise_name =  enterpriseMapper.selectOne(enterpriseQueryWrapper).getName();

        Exam exam = examMapper.selectById(examId);
        if(!exam.getEnterpriseName().equals(enterprise_name)){
            respMap.put("error_message", "身份验证失败");
            return WriteValue.writeValueAsString(respMap);
        }
        UpdateWrapper<Exam> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("exam_id", examId);
        updateWrapper.set("announced", true);
        examMapper.update(updateWrapper);
        respMap.put("error_message", "success");
        return WriteValue.writeValueAsString(respMap);
    }
}
