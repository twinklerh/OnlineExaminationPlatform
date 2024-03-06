package com.oep.backend.serviceImpl.exams;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oep.backend.mapper.EnterpriseMapper;
import com.oep.backend.mapper.ExamMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Enterprise;
import com.oep.backend.pojo.Exam;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.exams.GetExamService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetExamServiceImpl implements GetExamService {
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Override
    public String getAllExam(Integer current_page) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        Account account = userDetails.getAccount();
        Map<String,String> respMap = new HashMap<>();
        if("candidate".equals(account.getStatus())) {
            respMap.put("error_message", "身份验证失败");
            return WriteValue.writeValueAsString(respMap);
        }
        QueryWrapper<Enterprise> enterpriseQueryWrapper = new QueryWrapper<>();
        enterpriseQueryWrapper.eq("account_id", account.getAccountId());
        String enterprise_name = enterpriseMapper.selectOne(enterpriseQueryWrapper).getName();

        QueryWrapper<Exam> examQueryWrapper = new QueryWrapper<>();
        examQueryWrapper.orderByDesc("begin_time");
        examQueryWrapper.eq("enterprise_name", enterprise_name);

        IPage<Exam> examPage = new Page<>(current_page,6);
        examPage = examMapper.selectPage(examPage, examQueryWrapper);
        List<Exam> examList = examPage.getRecords();

        respMap.put("examList", WriteValue.writeValueAsString(examList));
        respMap.put("dataCount", String.valueOf(examPage.getTotal()));
        respMap.put("error_message", "success");
        return WriteValue.writeValueAsString(respMap);
    }
}
