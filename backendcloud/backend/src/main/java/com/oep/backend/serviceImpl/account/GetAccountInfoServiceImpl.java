package com.oep.backend.serviceImpl.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oep.backend.mapper.CandidateMapper;
import com.oep.backend.mapper.EnterpriseMapper;
import com.oep.backend.pojo.Account;
import com.oep.backend.pojo.Candidate;
import com.oep.backend.pojo.Enterprise;
import com.oep.backend.security.utils.UserDetailsImpl;
import com.oep.backend.service.account.GetAccountInfoService;
import com.oep.backend.utils.WriteValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class GetAccountInfoServiceImpl implements GetAccountInfoService {
    @Autowired
    private CandidateMapper candidateMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Override
    public String getAccountInfo() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        Account account = loginUser.getAccount();
        Map<String,String> map = new HashMap<>();
        map.put("status", account.getStatus());
        if("enterprise".equals(account.getStatus())){
            QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account_id", account.getAccountId());
            Enterprise enterprise = enterpriseMapper.selectOne(queryWrapper);
            if(enterprise!=null) map.put("username", enterprise.getName());
        }   else if ("candidate".equals(account.getStatus())) {
            QueryWrapper<Candidate> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account_id", account.getAccountId());
            Candidate candidate = candidateMapper.selectOne(queryWrapper);
            if(candidate!=null) map.put("username", candidate.getFullname());
        }
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(dateTime + ": userId "+account.getAccountId()+" login or get info by token.");
        return WriteValue.writeValueAsString(map);
    }
}
